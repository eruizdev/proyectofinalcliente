package com.example.demo.ui;

import com.example.demo.api.ApiService;
import com.example.demo.api.RetrofitClient;
import com.example.demo.dto.FacturaDTO;
import com.example.demo.model.Factura;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class FacturacionFrame extends JFrame {
    private JTextField txtIdFactura, txtNombreCliente, txtNombreMascota, txtTotal;
    private JComboBox<String> cbServicio, cbMetodoPago;
    private JButton btnGenerar, btnVerHistorial;
    private ApiService apiService;

    public FacturacionFrame() {
        setTitle("Facturación");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 250, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Facturación");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // ID Factura
        JLabel lblIdFactura = new JLabel("ID Factura:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblIdFactura, gbc);
        txtIdFactura = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtIdFactura, gbc);

        // Nombre Cliente
        JLabel lblNombreCliente = new JLabel("Nombre Cliente:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblNombreCliente, gbc);
        txtNombreCliente = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtNombreCliente, gbc);

        // Nombre Mascota
        JLabel lblNombreMascota = new JLabel("Nombre Mascota:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblNombreMascota, gbc);
        txtNombreMascota = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(txtNombreMascota, gbc);

        // Servicio Realizado
        JLabel lblServicio = new JLabel("Servicio:");
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblServicio, gbc);
        String[] servicios = {"consulta", "medicación/alimento/accesorio"};
        cbServicio = new JComboBox<>(servicios);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(cbServicio, gbc);

        // Total
        JLabel lblTotal = new JLabel("Total:");
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(lblTotal, gbc);
        txtTotal = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(txtTotal, gbc);

        // Método de Pago
        JLabel lblMetodoPago = new JLabel("Método de Pago:");
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(lblMetodoPago, gbc);
        String[] metodos = {"efectivo", "tarjeta", "transferencia"};
        cbMetodoPago = new JComboBox<>(metodos);
        gbc.gridx = 1; gbc.gridy = 6;
        panel.add(cbMetodoPago, gbc);

        // Botones
        btnGenerar = new JButton("Generar Factura");
        btnVerHistorial = new JButton("Ver Historial");
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        panel.add(btnGenerar, gbc);
        gbc.gridy = 8;
        panel.add(btnVerHistorial, gbc);

        btnGenerar.addActionListener(e -> generarFactura());
        btnVerHistorial.addActionListener(e -> verHistorialFacturas());

        add(panel);
    }

    private void generarFactura() {
        String idFactura = txtIdFactura.getText();
        String nomCliente = txtNombreCliente.getText();
        String nomMascota = txtNombreMascota.getText();
        String servicio = (String) cbServicio.getSelectedItem();
        String totalStr = txtTotal.getText();
        String metodoPago = (String) cbMetodoPago.getSelectedItem();

        if (idFactura.isEmpty() || nomCliente.isEmpty() || nomMascota.isEmpty() || totalStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double total;
        try {
            total = Double.parseDouble(totalStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Total inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Usamos la fecha actual
        java.time.LocalDate fecha = java.time.LocalDate.now();

        FacturaDTO dto = new FacturaDTO(idFactura, nomCliente, nomMascota, servicio, fecha, total, metodoPago);

        try {
            Call<String> call = apiService.generarFactura(dto);
            Response<String> response = call.execute();
            if (response.isSuccessful()) {
                JOptionPane.showMessageDialog(this, response.body(), "Información", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar
                txtIdFactura.setText("");
                txtNombreCliente.setText("");
                txtNombreMascota.setText("");
                txtTotal.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al generar factura: " + response.message(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verHistorialFacturas() {
        try {
            Call<List<Factura>> call = apiService.getHistorialFacturas();
            Response<List<Factura>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                List<Factura> lista = response.body();
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay facturas registradas.", "Historial", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (Factura f : lista) {
                    sb.append("ID Factura: ").append(f.getIdFactura())
                      .append(", Cliente: ").append(f.getNombreCliente())
                      .append(", Mascota: ").append(f.getNombreMascota())
                      .append(", Servicio: ").append(f.getServicioRealizado())
                      .append(", Total: ").append(f.getTotal())
                      .append(", Fecha: ").append(f.getFecha())
                      .append("\n");
                }
                JTextArea textArea = new JTextArea(sb.toString(), 10, 30);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(this, scrollPane, "Historial de Facturas", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al obtener historial de facturas: " + response.message(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
