package com.aluracursos.challengeLiteratura.Literatura.model;

import java.util.Arrays;
import java.util.Optional;

public enum Idioma {
    es("es","español"),
    en("en","Ingles"),
    fr("fr","Frances"),
    pt("pt","Portugues");

    private String IdiomaOmdb;
    private String IdiomaEspanol;
    Idioma (String IdiomaOmdb, String IdiomaEspanol){
        this.IdiomaOmdb = IdiomaOmdb;
        this.IdiomaEspanol = IdiomaEspanol;
    }

    public String getIdiomaOmdb() {
        return IdiomaOmdb;
    }

    public void setIdiomaOmdb(String idiomaOmdb) {
        IdiomaOmdb = idiomaOmdb;
    }

    public String getIdiomaEspanol() {
        return IdiomaEspanol;
    }

    public void setIdiomaEspanol(String idiomaEspanol) {
        IdiomaEspanol = idiomaEspanol;
    }

    public static Optional<Idioma> fromString(String codigo) {
        return Arrays.stream(values())
                .filter(i -> i.getIdiomaOmdb().equalsIgnoreCase(codigo))
                .findFirst();
    }

    public static Optional<Idioma> fromEspanol(String descripcion) {
        return Arrays.stream(values())
                .filter(i -> i.getIdiomaEspanol().equalsIgnoreCase(descripcion))
                .findFirst();
    }

    public static Optional<Idioma> buscar(String entrada) {
        Optional<Idioma> porCodigo = fromString(entrada);
        return porCodigo.isPresent() ? porCodigo : fromEspanol(entrada);
    }

}
