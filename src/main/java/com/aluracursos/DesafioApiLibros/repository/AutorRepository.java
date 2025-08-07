package com.aluracursos.DesafioApiLibros.repository;

import com.aluracursos.DesafioApiLibros.model.Autor;
import com.aluracursos.DesafioApiLibros.model.Idioma;
import com.aluracursos.DesafioApiLibros.model.Libro;

import java.util.List;
import java.util.Optional;

public interface AutorRepository {

    Optional<Autor> buscarAutorPorNombre(String collect);

    Optional<Libro> buscarLibroPorNombre(String titulo);

    void save(Autor autor);

    List<Libro> librosRegistrados();

    List<Autor> findAll();

    List<Autor> listarAutoresVivos(int fecha);

    List<Libro> librosPorIdioma(Idioma idiomaEnum);

    List<Autor> listarAutorPorFechaNacimiento(Integer nacimiento);

    List<Autor> listarAutorPorFechaFallecimiento(Integer fallecimiento);
}