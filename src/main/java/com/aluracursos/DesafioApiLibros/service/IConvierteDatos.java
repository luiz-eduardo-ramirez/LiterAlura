package com.aluracursos.DesafioApiLibros.service;

public interface IConvierteDatos {
    <T> T obtenerDatos (String json, Class<T> clase);
}
