package com.aluracursos.challengeLiteratura.Literatura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombreAutor;
    private Integer fechaNacimiento;
    private Integer fechaFallecido;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libro = new ArrayList<>();;
    public Autor() {
    }

    public Autor(DatosAutores datosAutor) {
        this.nombreAutor = datosAutor.nombreAutor();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecido = datosAutor.fechaFallecido();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecido() {
        return fechaFallecido;
    }

    public void setFechaFallecido(Integer fechaFallecido) {
        this.fechaFallecido = fechaFallecido;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        String listaLibros= (libro != null && !libro.isEmpty())
                ? libro.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", ", "[", "]"))
                : "[sin libros]";

        return "----------Autor---------" + '\n' +
                "nombreAutor=" + nombreAutor + '\n' +
                "fechaNacimiento=" + fechaNacimiento + '\n' +
                "fechaFallecido=" + fechaFallecido + '\n' +
                "Libros=" + listaLibros + '\n' +
                "---------------------" +'\n';
    }
}
