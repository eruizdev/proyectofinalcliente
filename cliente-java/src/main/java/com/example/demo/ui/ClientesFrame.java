package com.example.demo.ui;

import com.example.demo.api.ApiService;
import com.example.demo.api.RetrofitClient;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.model.Cliente;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientesFrame extends JFrame {
    private JTextField txtNombre, txtTelefono, txtCorreo, txtNombreMascota;
    private JButton btnGuardar, btnVerHistorial;
    private ApiService apiService;

    public ClientesFrame() {
        setTitle("Gestión de Clientes");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Inicializar Retrofit
        apiService = RetrofitClient.getClient().create(ApiService.class);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 245, 238));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblNombre, gbc);
        txtNombre = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtNombre, gbc);

        // Teléfono
        JLabel lblTelefono = new JLabel("Teléfono:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblTelefono, gbc);
        txtTelefono = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtTelefono, gbc);

        // Correo
        JLabel lblCorreo = new JLabel("Correo:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblCorreo, gbc);
        txtCorreo = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(txtCorreo, gbc);

        // Nombre de la Mascota
        JLabel lblMascota = new JLabel("Nombre Mascota:");
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblMascota, gbc);
        txtNombreMascota = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(txtNombreMascota, gbc);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnVerHistorial = new JButton("Ver Historial");
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(btnGuardar, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(btnVerHistorial, gbc);

        // ActionListeners
        btnGuardar.addActionListener(e -> guardarCliente());
        btnVerHistorial.addActionListener(e -> verHistorialClientes());

        add(panel);
    }

    private void guardarCliente() {
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String nombreMascota = txtNombreMascota.getText();

        // Validaciones mínimas
        if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty() || nombreMascota.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el DTO
        ClienteDTO dto = new ClienteDTO(nombre, telefono, correo, nombreMascota);

        try {
            Call<String> call = apiService.guardarCliente(dto);
            Response<String> response = call.execute();
            if (response.isSuccessful()) {
                JOptionPane.showMessageDialog(this, response.body(), "Información", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar campos
                txtNombre.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
                txtNombreMascota.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + response.message(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verHistorialClientes() {
        try {
            Call<List<Cliente>> call = apiService.getHistorialClientes();
            Response<List<Cliente>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                List<Cliente> lista = response.body();
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay clientes registrados.", "Historial", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (Cliente c : lista) {
                    sb.append("ID: ").append(c.getId())
                      .append(", Nombre: ").append(c.getNombre())
                      .append(", Teléfono: ").append(c.getTelefono())
                      .append(", Correo: ").append(c.getCorreo())
                      .append(", Mascota: ").append(c.getNombreMascota())
                      .append("\n");
                }
                JTextArea textArea = new JTextArea(sb.toString(), 10, 30);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(this, scrollPane, "Historial de Clientes", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al obtener historial: " + response.message(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
