package com.example.demo.api;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {

    // AUTH
    @POST("auth/login")
    Call<String> login(@Body LoginDTO loginDTO);

    @POST("auth/register")
    Call<String> register(@Body RegisterDTO registerDTO);

    // ADMIN MENU
    @GET("admin/menu")
    Call<List<String>> getMenu();

    // MASCOTAS
    @POST("mascotas/guardar")
    Call<String> guardarMascota(@Body MascotaDTO mascotaDTO);

    @GET("mascotas/historial")
    Call<List<Mascota>> getHistorialMascotas();

    // CLIENTES
    @POST("clientes/guardar")
    Call<String> guardarCliente(@Body ClienteDTO clienteDTO);

    @GET("clientes/historial")
    Call<List<Cliente>> getHistorialClientes();

    // HISTORIAL MÉDICO
    @POST("historialMedico/guardar")
    Call<String> guardarHistorialMedico(@Body HistorialMedicoDTO historialMedicoDTO);

    @GET("historialMedico/historial")
    Call<List<HistorialMedico>> getHistorialMedico();

    // CITAS
    @POST("citas/guardar")
    Call<String> guardarCita(@Body CitaDTO citaDTO);

    @GET("citas/historial")
    Call<List<Cita>> getHistorialCitas();

    // NUEVO ENDPOINT PARA REPORTES: Buscar cita por ID
    @GET("citas/buscar/{idCita}")
    Call<Cita> getCitaById(@Path("idCita") String idCita);

    // INVENTARIO
    @POST("inventario/agregar")
    Call<String> agregarProducto(@Body ProductoDTO productoDTO);

    @DELETE("inventario/eliminar/{id}")
    Call<String> eliminarProducto(@Path("id") Long id);

    @GET("inventario/historial")
    Call<List<Producto>> getHistorialProductos();

    // FACTURACIÓN
    @POST("facturacion/generar")
    Call<String> generarFactura(@Body FacturaDTO facturaDTO);

    @GET("facturacion/historial")
    Call<List<Factura>> getHistorialFacturas();
}
