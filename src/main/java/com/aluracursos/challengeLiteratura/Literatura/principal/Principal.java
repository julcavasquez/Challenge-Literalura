package com.aluracursos.challengeLiteratura.Literatura.principal;

import com.aluracursos.challengeLiteratura.Literatura.model.*;
import com.aluracursos.challengeLiteratura.Literatura.repositorio.AutorRepository;
import com.aluracursos.challengeLiteratura.Literatura.repositorio.LibroRepository;
import com.aluracursos.challengeLiteratura.Literatura.service.ConsumoAPI;
import com.aluracursos.challengeLiteratura.Literatura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private final AutorRepository autorRepositorio;
    private final LibroRepository libroRepositorio;
    private List<Libro> libros;
    private List<Autor> autores;
    private Autor autor;
    private Libro libro;
    @Autowired
    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepositorio = autorRepository;
        this.libroRepositorio = libroRepository;
    }

    public void muestraElMenu(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opción a través de su número:
                    1 - Buscar libro por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año 
                    5 - Listar libros por idioma 
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosEnAnio();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosResultado getDatosResultado() {
        System.out.println("Escribe el nombre del libro que desea buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        DatosResultado datos = conversor.obtenerDatos(json, DatosResultado.class);
        return datos;
    }

    private void buscarLibroWeb() {
        try {
            DatosResultado datos = getDatosResultado();
            System.out.println(datos);
            if(datos.contador() == 0){
                System.out.println("Libro no encontrado");
            }else{
                Optional<DatosLibros> datoslibro = datos.resultado().stream()
                        .max(Comparator.comparingInt(DatosLibros::cantidadDescargas));
                if(datoslibro.isPresent()) {
                    System.out.println("Libro Encontrado");
                    var LibroEncontrado = datoslibro.get();
                    Optional<Autor> autorRegistrado = autorRepositorio.findByNombreConLibros(LibroEncontrado.autores().get(0).nombreAutor());
                    if (autorRegistrado.isPresent()) {
                        autor = autorRegistrado.get();
                        libro = new Libro(LibroEncontrado,autor);
                        autor.getLibro().add(libro);
                        autorRepositorio.save(autor);
                    } else {
                        autor = new Autor(LibroEncontrado.autores().get(0));
                        libro = new Libro(LibroEncontrado,autor);
                        autor.getLibro().add(libro);
                        autorRepositorio.save(autor);
                    }
                    System.out.println(LibroEncontrado.autores());
                    System.out.println(libro.toString());
                }
            }
        }catch (DataIntegrityViolationException e){
            System.out.println("No se puede registrar el mismo libro mas de una vez");
        }
    }

    private void listarLibrosRegistrados() {
        libros = libroRepositorio.findAllConAutores();
        for (Libro libro : libros){
            System.out.println(libro);
        }

    }

    private List<Autor> obtenerAutores(){
        autores = autorRepositorio.findAllAutoresConLibros();
        return autores.stream()
                .sorted(Comparator.comparing(Autor::getNombreAutor))
                .collect(Collectors.toList());
    }

    private void listarAutoresRegistrados() {
        autores = obtenerAutores();
        autores.forEach(System.out::println);

    }

    public void autoresVivosEnAnio() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar: ");
        var anio = teclado.nextInt();
        autores = obtenerAutores();
        autores = autores.stream()
                .filter(a -> a.getFechaNacimiento() <= anio &&
                        (a.getFechaFallecido() == null || a.getFechaFallecido() >= anio))
                .collect(Collectors.toList());
        if (autores.isEmpty()) {
            System.out.println("Ningun Autor vivo hasta la fecha");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void buscarLibrosPorIdioma(){
        System.out.println("Escriba el Idioma para buscar Libros: ");
        for (Idioma idioma : Idioma.values()) {
            System.out.println(idioma.getIdiomaOmdb() + " - " + idioma.getIdiomaEspanol());
        }
        var idioma = teclado.nextLine();
        Optional<Idioma> idiomaOptional = Idioma.buscar(idioma);
        if(idiomaOptional.isPresent()) {
            Idioma idiomaOmdb = idiomaOptional.get();
            System.out.println(idiomaOmdb);
            libros = libroRepositorio.findAllConAutores();
            libros = libros.stream()
                    .filter(l -> l.getIdioma().equals(idiomaOmdb))
                    .collect(Collectors.toList());

            if (libros.isEmpty()) {
                System.out.println("Ningun Libro registrado para ese idioma");
            } else {
                libros.forEach(System.out::println);
            }
        }



    }




}
