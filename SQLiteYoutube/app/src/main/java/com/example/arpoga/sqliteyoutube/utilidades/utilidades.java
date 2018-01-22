package com.example.arpoga.sqliteyoutube.utilidades;

/**
 * Created by arpoga on 29/11/2017.
 */

//clase para los registros de la base de datos
public class utilidades {

    //constantes de las dos tablas
    public static final String TABLA_ALUMNO = "alumno";
    public static final String TABLA_PROFESOR = "profesor";
    public static final String TABLA_ASIGNATURA = "asignatura";

    //constantes de los campos que componen las tablas
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_EDAD = "edad";
    public static final String CAMPO_CURSO = "curso";
    public static final String CAMPO_CICLO = "ciclo";
    public static final String CAMPO_DESPACHO = "despacho";
    public static final String CAMPO_NOTA = "nota";
    public static final String CAMPO_HORAS = "horas";


    //constantes para crear las tablas con toda la información
   public static final String CREAR_TABLA_ALUMNO = "CREATE TABLE "+ ""+TABLA_ALUMNO+"(_id integer primary key autoincrement, nombre text, edad text, curso text, ciclo text, nota text )";
   public static final String CREAR_TABLA_PROFESOR = "CREATE TABLE "+ ""+TABLA_PROFESOR+"(_id integer primary key autoincrement, nombre text, edad text, curso text, ciclo text, despacho text )";
    public static final String CREAR_TABLA_ASIGNATURA = "CREATE TABLE "+ ""+TABLA_ASIGNATURA+"(_id integer primary key autoincrement, nombre text, text horas )";

}
