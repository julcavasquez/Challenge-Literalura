package com.aluracursos.challengeLiteratura.Literatura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json,Class<T> clase);
}
