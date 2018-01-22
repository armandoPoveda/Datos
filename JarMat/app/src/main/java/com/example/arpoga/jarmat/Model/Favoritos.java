package com.example.arpoga.jarmat.Model;

/**
 * Created by arpoga on 22/01/2018.
 */

public class Favoritos {

    private  String nombre;
    private String uid;

    public Favoritos(){

    }
    public Favoritos (String nombre, String uid){
        this.nombre = nombre;
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
