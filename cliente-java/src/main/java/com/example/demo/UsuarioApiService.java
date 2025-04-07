package com.example.demo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsuarioApiService {
    @GET("/api/usuarios")
    Call<List<Usuario>> getAllUsuarios();

    @GET("/api/usuarios/{id}")
    Call<Usuario> getUsuarioById(@Path("id") String id);

    @POST("/api/usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);

    @PUT("/api/usuarios/{id}")
    Call<Usuario> updateUsuario(@Path("id") String id, @Body Usuario usuario);

    @DELETE("/api/usuarios/{id}")
    Call<Void> deleteUsuario(@Path("id") String id);

    @GET("/api/usuarios/buscar")
    Call<List<Usuario>> buscarUsuarios(
        @Query("nombre") String nombre,
        @Query("email") String email,
        @Query("edad") int edad
    );

    @GET("/api/usuarios/auth")
    Call<Usuario> getUserByToken(@Header("Authorization") String authToken);
}