package org.izv.amml.multi.aerolinea_v;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Cliente implements Parcelable, Serializable {

    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String email;

    public Cliente(String nombre, String apellidos, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }


    protected Cliente(Parcel in) {
        nombre = in.readString();
        apellidos = in.readString();
        direccion = in.readString();
        telefono = in.readString();
        email = in.readString();
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String cadena = nombre + ";" + apellidos + ";" + direccion + ";" + telefono + ";" + email;
        return cadena;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(apellidos);
        parcel.writeString(direccion);
        parcel.writeString(telefono);
        parcel.writeString(email);
    }
}
