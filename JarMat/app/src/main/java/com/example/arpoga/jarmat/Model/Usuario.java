package com.example.arpoga.jarmat.Model;

/**
 * Created by arpoga on 05/01/2018.
 */

public class Usuario {

    private String alias;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String correo;
    private String uid;

    public Usuario(){

    }

    public Usuario(String alias, String nombre, String apellidos, String direccion, String correo, String uid) {
        this.alias = alias;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.correo = correo;
        this.uid = uid;

    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "alias='" + alias + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
