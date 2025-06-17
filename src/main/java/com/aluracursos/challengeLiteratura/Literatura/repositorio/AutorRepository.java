package com.aluracursos.challengeLiteratura.Literatura.repositorio;

import com.aluracursos.challengeLiteratura.Literatura.model.Autor;
import com.aluracursos.challengeLiteratura.Literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libro WHERE a.nombreAutor = :nombre")
    Optional<Autor> findByNombreConLibros(String nombre);

    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.libro")
    List<Autor> findAllAutoresConLibros();
}
