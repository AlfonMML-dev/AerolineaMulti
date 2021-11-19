package org.izv.amml.multi.aerolinea_v;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class PrecioBillete implements Parcelable, Serializable{

    private ArrayList<String> extrasSeleccionados = new ArrayList<>();
    private Cliente cliente;
    private String extrasSelecCadena = "";
    private double precioOrigenDestino;
    private double precioTotal = 0;
    private String origen;
    private String destino;
    
    private HashMap<String, Double> precioExtras = new HashMap<>();

    public PrecioBillete() {

    }

    public PrecioBillete(String origen, String destino, ArrayList<String> extrasSeleccionados, Cliente cliente){
        this.origen = origen;
        this.destino = destino;
        this.extrasSeleccionados = extrasSeleccionados;
        this.cliente = cliente;
        rellenarPrecioExtras();
        calcularPrecioTotal();
        castArrayListToString();
    }

    protected PrecioBillete(Parcel in) {
        extrasSeleccionados = in.createStringArrayList();
        cliente = in.readParcelable(Cliente.class.getClassLoader());
        extrasSelecCadena = in.readString();
        precioOrigenDestino = in.readDouble();
        precioTotal = in.readDouble();
        origen = in.readString();
        destino = in.readString();
    }

    public static final Creator<PrecioBillete> CREATOR = new Creator<PrecioBillete>() {
        @Override
        public PrecioBillete createFromParcel(Parcel in) {
            return new PrecioBillete(in);
        }

        @Override
        public PrecioBillete[] newArray(int size) {
            return new PrecioBillete[size];
        }
    };

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getExtrasSelecCadena() {
        return extrasSelecCadena;
    }

    public void setExtrasSelecCadena(String extrasSelecCadena) {
        this.extrasSelecCadena = extrasSelecCadena;
    }

    public double getPrecioOrigenDestino() {
        return precioOrigenDestino;
    }

    public void setPrecioOrigenDestino(double precioOrigenDestino) {
        this.precioOrigenDestino = precioOrigenDestino;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getPrecioExtrasTotal(){
        return formatearNumDouble(precioTotal - precioOrigenDestino);
    }

    private void calcularPrecioOrigenDestino(){
        double result = 0;
        char[] charsOrigen = origen.toCharArray();
        char[] charsDestino = destino.toCharArray();

        for (int i = 0; i < charsOrigen.length; i++) {
            double aux = charsOrigen[i] / 10;
            result += aux;
        }
        for (int i = 0; i < charsDestino.length; i++) {
            double aux = charsDestino[i] / 10;
            result += aux;
        }
        result = result * ((Math.random() * 5) + 1);
        precioOrigenDestino = formatearNumDouble(result);
    }
    
    private void calcularPrecioTotal(){
        calcularPrecioOrigenDestino();
        for (int i = 0; i < extrasSeleccionados.size(); i++) {
            precioTotal += precioExtras.get(extrasSeleccionados.get(i));
        }
        precioTotal += precioOrigenDestino;
        precioTotal = formatearNumDouble(precioTotal);
    }

    private void castArrayListToString(){
        String nombre;
        String precio;
        for (int i = 0; i < extrasSeleccionados.size()-1; i++) {
            nombre = extrasSeleccionados.get(i);
            precio = String.valueOf(precioExtras.get(extrasSeleccionados.get(i)));
            extrasSelecCadena += nombre + "," + precio + ";";
        }
        nombre = extrasSeleccionados.get(extrasSeleccionados.size()-1);
        precio = String.valueOf(precioExtras.get(extrasSeleccionados.get(extrasSeleccionados.size()-1)));
        extrasSelecCadena += nombre + "," + precio;
    }

    private double formatearNumDouble(double num){
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return Double.parseDouble(numberFormat.format(num));
    }

    private void rellenarPrecioExtras(){
        String[] aux = MainActivity.getNombresExtras().split(";");
        double precioExtra = 0;
        for (int i = 0; i < aux.length; i++) {
            if(aux[i].contains("clase")){
                precioExtra = 100.0;
            } else if(aux[i].contains("ventanilla")){
                precioExtra = 20.5;
            } else if(aux[i].contains("mascota")){
                precioExtra = 30.2;
            } else if(aux[i].contains("desayuno")){
                precioExtra = 12.5;
            } else if(aux[i].contains("almuerzo")){
                precioExtra = 15.0;
            } else if(aux[i].contains("cena")){
                precioExtra = 12.5;
            } else if(aux[i].contains("seguro")){
                precioExtra = 40.5;
            } else if(aux[i].contains("acceso")){
                precioExtra = 150.0;
            }
            precioExtras.put(aux[i], precioExtra);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(extrasSeleccionados);
        parcel.writeParcelable(cliente, i);
        parcel.writeString(extrasSelecCadena);
        parcel.writeDouble(precioOrigenDestino);
        parcel.writeDouble(precioTotal);
        parcel.writeString(origen);
        parcel.writeString(destino);
    }
}
