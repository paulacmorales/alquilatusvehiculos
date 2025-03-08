package com.alquilatusvehiculos.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private int anio;
    private double precioAlquiler;

    // Constructores
    public Vehiculo() {}

    public Vehiculo(String marca, String modelo, int anio, double precioAlquiler) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precioAlquiler = precioAlquiler;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public double getPrecioAlquiler() { return precioAlquiler; }
    public void setPrecioAlquiler(double precioAlquiler) { this.precioAlquiler = precioAlquiler; }
}

