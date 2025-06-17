package com.aluracursos.challengeLiteratura.Literatura.repositorio;


import com.aluracursos.challengeLiteratura.Literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT l FROM Libro l JOIN FETCH l.autor")
    List<Libro> findAllConAutores();
}
