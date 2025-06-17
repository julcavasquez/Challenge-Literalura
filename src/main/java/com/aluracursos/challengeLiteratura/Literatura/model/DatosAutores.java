package com.aluracursos.challengeLiteratura.Literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutores(
        @JsonAlias("name") String nombreAutor,
        @JsonAlias("birth_year") Integer fechaNacimiento,
        @JsonAlias("death_year") Integer fechaFallecido
) {
}
