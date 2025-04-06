package com.alquilatusvehiculos.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String matricula;
    private Double precioPorDia;
    private int anio;
    private boolean disponible;

    // Getter para precioPorDia
    public Double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Otros métodos y setters generados por Lombok (si estás usando Lombok)
}
