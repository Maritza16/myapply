package com.aplicacion.myapply;

import android.media.Image;

public class Persona {

    int id;
    Image imagen;
    String nombre;
    String telefono;
    String latitud;
    String Longitud;

    public Persona(int id, String nombre, String telefono, String latitud, String longitud) {
        this.id = id;
      //  this.imagen = imagen;
        this.nombre = nombre;
        this.telefono = telefono;
        this.latitud = latitud;
        Longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }


}
