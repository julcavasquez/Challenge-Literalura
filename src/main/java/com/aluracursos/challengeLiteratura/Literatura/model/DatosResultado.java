package com.aluracursos.challengeLiteratura.Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosResultado(
        @JsonAlias("count") Integer contador,
        @JsonAlias("results") List<DatosLibros> resultado
) {
}
