package com.aluracursos.challengeLiteratura.Literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer cantidadDescargas;
    @ManyToOne(fetch = FetchType.LAZY)
    private Autor autor;

    public Libro(){}

    public Libro(DatosLibros datosLibros, Autor autor) {
        this.titulo = datosLibros.titulo();
        this.idioma = Idioma.buscar(datosLibros.idioma().get(0).split(",")[0].trim()) .orElseThrow(() -> new IllegalArgumentException("Idioma no válido"));
        this.cantidadDescargas = datosLibros.cantidadDescargas();
        this.autor = autor;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Integer cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {

        return "---------- LIBRO ----------" + '\n' +
                "Titulo: " + titulo + '\n' +
                "Autor: " + autor.getNombreAutor() + '\n' +
                "Idioma: " + idioma + '\n' +
                "Numero de descargas: " + cantidadDescargas + '\n' +
                "--------------------";
    }
}
