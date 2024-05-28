package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.model.Datos;
import com.aluracursos.desafio.model.DatosLibros;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    //esta api no tiene apikey
   
    public void muestraElMenu(){
        
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json,Datos.class);
        System.out.println(datos);

        //Top 10 libros más descargados
        System.out.println("Top 10 libros más descargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busqueda de libros por nombre
//        System.out.println("Ingrese el nombre del libro que desea buscar");
//        var tituloLibro = teclado.nextLine();
//        json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
//        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
//        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
//                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
//                .findFirst();
//        if(libroBuscado.isPresent()){
//            System.out.println("Libro Encontrado ");
//            System.out.println(libroBuscado.get());
//        }else {
//            System.out.println("Libro no encontrado");
//        }
        
        // Buscando libros por idioma. No me trae los libros 
        System.out.println("Indica el idioma en el que estas buscando los libros");
        var idiomaSeleccionado= teclado.nextLine().trim();
        
        if(idiomaSeleccionado.equalsIgnoreCase("english")||idiomaSeleccionado.equalsIgnoreCase("ingles")){
          json = consumoAPI.obtenerDatos(URL_BASE+"?languages=en");
        }else if(idiomaSeleccionado.equalsIgnoreCase("spanish")||idiomaSeleccionado.equalsIgnoreCase("español")){
          json = consumoAPI.obtenerDatos(URL_BASE+"?languages=es");
        }else if(idiomaSeleccionado.equalsIgnoreCase("french")||idiomaSeleccionado.equalsIgnoreCase("frances")){
          json = consumoAPI.obtenerDatos(URL_BASE+"?languages=fr");
        }else{
            System.out.println("No hay libros en el idioma seleccionado");
            return;
        }
        
       
        var LibrosEnIdioma = conversor.obtenerDatos(json, Datos.class);
       
        System.out.println("Los libros en " + idiomaSeleccionado + " son :");
        LibrosEnIdioma.resultados().stream()
                .forEach(System.out::println);
         
         
        
        //Trabajando con estadisticas
//        DoubleSummaryStatistics est = datos.resultados().stream()
//                .filter(d -> d.numeroDeDescargas() >0 )
//                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
//        System.out.println("Cantidad media de descargas: " + est.getAverage());
//        System.out.println("Cantidad máxima de descargas: "+ est.getMax());
//        System.out.println("Cantidad mínima de descargas: " + est.getMin());
//        System.out.println(" Cantidad de registros evaluados para calcular las estadisticas: " + est.getCount());

    }
}
