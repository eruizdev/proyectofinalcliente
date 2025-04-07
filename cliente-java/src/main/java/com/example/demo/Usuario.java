package com.example.demo;

//Modelo de Usuario (igual al proporcionado)
class Usuario {
 private String id;
 private String nombre;
 private String email;
 private int edad;

 // Getters y Setters (igual al código original)
 public String getId() { return id; }
 public void setId(String id) { this.id = id; }
 public String getNombre() { return nombre; }
 public void setNombre(String nombre) { this.nombre = nombre; }
 public String getEmail() { return email; }
 public void setEmail(String email) { this.email = email; }
 public int getEdad() { return edad; }
 public void setEdad(int edad) { this.edad = edad; }

 @Override
 public String toString() {
     return "Usuario{" +
            "id='" + id + '\'' +
            ", nombre='" + nombre + '\'' +
            ", email='" + email + '\'' +
            ", edad=" + edad +
            '}';
 }
}