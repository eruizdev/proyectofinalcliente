package com.example.demo;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioClient {
    private static final String BASE_URL = "http://localhost:8080";
    private static UsuarioApiService apiService;
    private static Scanner scanner;

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        apiService = retrofit.create(UsuarioApiService.class);
        scanner = new Scanner(System.in);

        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: listarTodosUsuarios(); break;
                case 2: buscarUsuarioPorId(); break;
                case 3: crearUsuario(); break;
                case 4: actualizarUsuario(); break;
                case 5: eliminarUsuario(); break;
                case 6: buscarUsuariosPorFiltros(); break;
                case 7: buscarPorToken(); break;
                case 0: 
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENU DE USUARIOS ---");
        System.out.println("1. Listar Todos los Usuarios");
        System.out.println("2. Buscar Usuario por ID");
        System.out.println("3. Crear Usuario");
        System.out.println("4. Actualizar Usuario");
        System.out.println("5. Eliminar Usuario");
        System.out.println("6. Buscar Usuarios por Filtros");
        System.out.println("7. Buscar por Token de Autorización");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void listarTodosUsuarios() {
        try {
            Response<List<Usuario>> response = apiService.getAllUsuarios().execute();
            if (response.isSuccessful()) {
                List<Usuario> usuarios = response.body();
                usuarios.forEach(usuario -> System.out.println(usuario));
            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buscarUsuarioPorId() {
        System.out.print("Ingrese el ID del usuario: ");
        String id = scanner.nextLine();
        try {
            Response<Usuario> response = apiService.getUsuarioById(id).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body());
            } else {
                System.out.println("Usuario no encontrado: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void crearUsuario() {
        Usuario usuario = new Usuario();
        System.out.print("Nombre: ");
        usuario.setNombre(scanner.nextLine());
        System.out.print("Email: ");
        usuario.setEmail(scanner.nextLine());
        System.out.print("Edad: ");
        usuario.setEdad(scanner.nextInt());

        try {
            Response<Usuario> response = apiService.createUsuario(usuario).execute();
            if (response.isSuccessful()) {
                System.out.println("Usuario creado: " + response.body());
            } else {
                System.out.println("Error al crear usuario: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void actualizarUsuario() {
        System.out.print("Ingrese el ID del usuario a actualizar: ");
        String id = scanner.nextLine();
        
        Usuario usuario = new Usuario();
        usuario.setId(id);
        System.out.print("Nombre: ");
        usuario.setNombre(scanner.nextLine());
        System.out.print("Email: ");
        usuario.setEmail(scanner.nextLine());
        System.out.print("Edad: ");
        usuario.setEdad(scanner.nextInt());

        try {
            Response<Usuario> response = apiService.updateUsuario(id, usuario).execute();
            if (response.isSuccessful()) {
                System.out.println("Usuario actualizado: " + response.body());
            } else {
                System.out.println("Error al actualizar usuario: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void eliminarUsuario() {
        System.out.print("Ingrese el ID del usuario a eliminar: ");
        String id = scanner.nextLine();
        try {
            Response<Void> response = apiService.deleteUsuario(id).execute();
            if (response.isSuccessful()) {
                System.out.println("Usuario eliminado exitosamente");
            } else {
                System.out.println("Error al eliminar usuario: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buscarUsuariosPorFiltros() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email (opcional, presione Enter si no aplica): ");
        String email = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();

        try {
            Response<List<Usuario>> response = apiService.buscarUsuarios(nombre, email, edad).execute();
            if (response.isSuccessful()) {
            	response.body().forEach(usuario -> System.out.println(usuario));
            } else {
                System.out.println("Error al buscar usuarios: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buscarPorToken() {
        System.out.print("Ingrese el token de autorización: ");
        String token = scanner.nextLine();
        try {
            Response<Usuario> response = apiService.getUserByToken(token).execute();
            if (response.isSuccessful()) {
                System.out.println("Usuario autenticado: " + response.body());
            } else {
                System.out.println("Autorización fallida: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}