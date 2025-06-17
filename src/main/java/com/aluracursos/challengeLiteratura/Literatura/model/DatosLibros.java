package com.aluracursos.challengeLiteratura.Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Integer cantidadDescargas,
        @JsonAlias("authors") List<DatosAutores> autores
) {
}
