package com.example.demo.ui;

import com.example.demo.api.ApiService;
import com.example.demo.api.RetrofitClient;
import com.example.demo.dto.HistorialMedicoDTO;
import com.example.demo.model.HistorialMedico;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistorialMedicoFrame extends JFrame {
    private JTextField txtIdMascota, txtFecha, txtDiagnostico, txtTratamiento;
    private JButton btnGuardar, btnVerHistorial;
    private ApiService apiService;

    public HistorialMedicoFrame() {
        setTitle("Historial Médico");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 255, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Historial Médico");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // ID Mascota
        JLabel lblIdMascota = new JLabel("ID Mascota:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblIdMascota, gbc);
        txtIdMascota = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtIdMascota, gbc);

        // Fecha
        JLabel lblFecha = new JLabel("Fecha (dd/mm/aaaa):");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblFecha, gbc);
        txtFecha = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtFecha, gbc);

        // Diagnóstico
        JLabel lblDiagnostico = new JLabel("Diagnóstico:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblDiagnostico, gbc);
        txtDiagnostico = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(txtDiagnostico, gbc);

        // Tratamiento
        JLabel lblTratamiento = new JLabel("Tratamiento:");
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblTratamiento, gbc);
        txtTratamiento = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(txtTratamiento, gbc);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnVerHistorial = new JButton("Ver Historial");
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(btnGuardar, gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(btnVerHistorial, gbc);

        btnGuardar.addActionListener(e -> guardarHistorial());
        btnVerHistorial.addActionListener(e -> verHistorialMedico());

        add(panel);
    }

    private void guardarHistorial() {
        String idMascotaStr = txtIdMascota.getText();
        String fecha = txtFecha.getText();
        String diagnostico = txtDiagnostico.getText();
        String tratamiento = txtTratamiento.getText();

        if (idMascotaStr.isEmpty() || fecha.isEmpty() || diagnostico.isEmpty() || tratamiento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        long idMascota;
        try {
            idMascota = Long.parseLong(idMascotaStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID de Mascota inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HistorialMedicoDTO dto = new HistorialMedicoDTO(idMascota, fecha, diagnostico, tratamiento);

        try {
            Call<String> call = apiService.guardarHistorialMedico(dto);
            Response<String> response = call.execute();
            if (response.isSuccessful()) {
                JOptionPane.showMessageDialog(this, response.body(), "Información", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar
                txtIdMascota.setText("");
                txtFecha.setText("");
                txtDiagnostico.setText("");
                txtTratamiento.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + response.message(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verHistorialMedico() {
        try {
            Call<List<HistorialMedico>> call = apiService.getHistorialMedico();
            Response<List<HistorialMedico>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                List<HistorialMedico> lista = response.body();
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay historial médico.", "Historial", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (HistorialMedico hm : lista) {
                    sb.append("ID Mascota: ").append(hm.getIdMascota())
                      .append(", Fecha: ").append(hm.getFecha())
                      .append(", Diagnóstico: ").append(hm.getDiagnostico())
                      .append(", Tratamiento: ").append(hm.getTratamiento())
                      .append("\n");
                }
                JTextArea textArea = new JTextArea(sb.toString(), 10, 30);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(this, scrollPane, "Historial Médico", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al obtener historial: " + response.message(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
