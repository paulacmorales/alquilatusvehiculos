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

    public void setId(Long id) {
        this.id = id;
    }
}
