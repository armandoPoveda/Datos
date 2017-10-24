package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import Clase_Libro.Libro;
import view.LaunchView;


public class GestionDatos {
	

	public GestionDatos() {
		
	}
	
	//TODO: Implementa una función para abrir ficheros
	//esta funcion nos creara un bufferReader que se almacenara en un string fichero
	private static  BufferedReader abrir(String fichero) throws IOException{
		//clase file, para crear un objecto file
		//la clase Filereader sirve para leer caracteres
		//la clase bufferreader sirve para leer una secuencia de caracteres que almacena en un buffer
		//envoltura con bufferReader de la clase file, para crear un archivo, de la clase flireader para leer el archivo
		BufferedReader br=new BufferedReader(new FileReader(new File(fichero)));
		
		return br;
	}
	
	//TODO: Implementa una función para cerrar ficheros
	//funcion para cerrar archivos, para que se apliquen los cambios en los archivos
	private static  BufferedReader cerrar(String fichero) throws IOException{// impletmentamos una función para cerrar ficheros
		
		BufferedReader cr=abrir(fichero);
		cr.close();//metodo para cerrar archivos
		return cr;
	}
	
	//funcion para escribir los caracteres y almacenarlos en un buffer
private static  BufferedWriter escribir(String fichero) throws IOException{// función para alamacenar en el bufferwriter una secuencia de caracteres
		//filewriter para escribir caracter en el objeto File y bufferwriter para que lo almacene en el buffer
	BufferedWriter wr=new BufferedWriter(new FileWriter(new File(fichero)));
		
		return wr;
	}

	//metodo comparar para ficheros que le pasaremos dos String
	public boolean compararContenido (String fichero1, String fichero2) throws IOException{//metódo para comparar dos Strings
		

		BufferedReader br=abrir(fichero1);//utilizamos la función del buffer para abrir el fichero1 y lo mismo con el fichero2
		BufferedReader br2=abrir(fichero2);	// lo guardamos en variables diferentes
		
		if(!br.ready()||!br2.ready()) {//con ready comprobamos si la secuenta está lista para ser leida
			
			throw new FileNotFoundException("Error no existe el archivo");//controlamos su excepción
		}
		
		String linea1 = br.readLine();//guardamos en una variable String la linea que lee del fichero1
		System.out.println("Leo linea1 fichero1: "+linea1);
		String linea2 = br2.readLine();
		System.out.println("Leo linea2 fichero2: "+linea2);//guardamos en una variable String la linea que lle del fichero2
		
		//TODO: Implementa la función
		//mientras linea1 y linea2 no sean null, sigue leyendo
		//si la linea del fichero1 no es igual a la linea del fichero2 sigue leyendo lineas
		while ((linea1!=null)&&(linea2!=null)) {
			if(!linea1.equals(linea2))//metodo equals para comparar String
				return false;
			linea1=br.readLine();//readLine para leer linea1 del fichero1
			System.out.println("Leo linea1 fichero1: "+linea1);
			linea2=br2.readLine();//readLine para leer linea2 del fichero2
			System.out.println("Leo linea2 fichero2: "+linea2);
		}
		//cerramos los buffers
		intentaCerrar(br);
		intentaCerrar(br2);
		return true;		
		}
	
	//metodo para buscar palabra
	//creamos un método para buscar una palabra dentro de un archivo
	//le pasamos un String del fichero1, un String de palabra, y un boolean para comprobar si es la
	//primera aparición de la palabra o la última
	public int buscarPalabra (String fichero1, String palabra, boolean primera_aparicion) throws IOException{


		BufferedReader br=abrir(fichero1);//utilizamos el bufferreader para leer el fichero1
		String linea1=br.readLine();//leemos linea

		if(!br.ready()) {//controlamos que el fichero esté listo
			throw new FileNotFoundException("Error no existe el archivo");
		}
		System.out.println("Leo linea: "+linea1);

		//mientras queden lineas, 
		//comparamos la linea con la palabra
		//si la palabra es igual devuelves la linea en donde está
		//si la palabra no es igual leo linea

		int contador=0; //contador para que nos guarde la linea donde apareca la palabra
		int contador2 = 0;//para que nos guarde si hay mas palabras despues de la primera

		//si la primera aparición es true
		if(primera_aparicion==true) {
			while((linea1!=null)){//recorres fichero, hayan lineas
				contador++;//guardamos en contador
				if(linea1.equals(palabra)) {//si linea es igual a palabra
					return contador;//devuelve el numero donde está guardada en contador
				}
				linea1=br.readLine();//volvemos a ller linea		
				System.out.println("Leo linea: "+linea1);
			}
		}

		//mientras queden lineas,
		//comparamos la linea con la palabra
		//si la palabra es igual a linea, guardo la linea y continuo leyendo
		//si no continuo leyendo
		//devolver lo que tenga en el valor de la linea guardada

		if(primera_aparicion==false) {//si la primera aparicón es false
			while(linea1!=null) {//mientras linea1 no sea igual a null
				contador++;//añadimos al contador
				if(linea1.equals(palabra)) {//si linea1 es igual a la plabra
					contador2=contador;// contador 2 será la última palabra guardada
					System.out.println("Encontrado en la ultima linea "+contador2);	//imprimos la ultima palabra			
				}
				linea1=br.readLine();
				System.out.println(linea1);

			}
		}
		BufferedReader cr=cerrar(fichero1);// siempre cerrando los ficheros
		return contador2;
	}	

	// metodo cerrar que le pasamos la clase Closeable para cerrar los Intput y Output
	public  static void intentaCerrar(Closeable c){
		try {
			c.close();
		}catch(IOException e){}
	}
	
	//copiar ficheros
	public int copiarFicheros(String fichero1, String fichero2) throws IOException {

		FileInputStream fi=null;//obtiene datos de bytes de un archivo de entrada
		FileOutputStream fo=null;//y este la salida de bytes para un archivo de salida
		File  file= new File(fichero1);// importamos la clase file
		int numBytes= (int)file.length();// aqui le decimos que nos diga el numero de bytes
		byte[] buffer= new byte[numBytes];// un array para recorrer el numbytes


		fi=new FileInputStream(fichero1);
		fo=new FileOutputStream(fichero2);

		System.out.println("He abierto los dos streams");

		int cont=0;//contador para aumentar los bytes
		while((numBytes=fi.read())!=-1){// lee byte a byte y cuando sea menos 1 paras
			fo.write(numBytes);//escribe los bytes en el outputstream	
			cont++;
		}
		System.out.println("He leido "+cont);

		//cerramos FileInputStream y FileOutputStream
		intentaCerrar(fi);
		intentaCerrar(fo);

		return numBytes; //devuelve el array				
	}

	// metodo recuperar libro
	public int recuperarlibro(String id) throws FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println("Recuperar libro");
		Libro libro=new Libro();//creamos la clase libro

		// ObjectInputStream nos devolverá el id del libro
		ObjectInputStream	in=new ObjectInputStream(new FileInputStream("libros\\"+id));
		Libro l=(Libro) in.readObject();//lee el flujo de datos de libro
		System.out.println("He recuperado libro "+l.getId()); //imprimo el id del libro

		//cerramos  ObjectInputStream
		intentaCerrar(in);

		return 0;
	}
	
	//metodo añadir libro
	public int añadirlibro(String id, String t, String a, int any, String ed, int np) throws FileNotFoundException, IOException {
		Libro libro=new Libro(id, t, a, any, ed, np);// creamos el objeto libro con todos los parametros que le pasamos en la clase libro
		File file=new File(id);// un File con el id, para que nos guarde el libro con la id

		//ObjectOutputStream para la salida de objetos y  FileOutputStream para crearlos en el directorio, en este caso le
		//decimos que nos lo cree en un carpeta dentro del proyecto
		ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream("libros/"+file));
		out.writeObject(libro);//escribimos el obejecto que nos sale por el flujos de datos
		System.out.print("añado el libro");
		//cerramos el ObjectOutputStream
		intentaCerrar(out);

		return 1;
	}
	
	public int recuperartodos(String id) throws FileNotFoundException, IOException{
		//File ruta, para buscar en la ruta especificada
		File ruta=new File("C:\\Users\\arpoga\\Documents\\DAM 2 AÑO\\Acceso a Datos (Juanmi)\\Tema 1 Ficheros-Cambiar\\actividad 1c\\ProyectoBuffers-master\\libros");
		File libro=new File(id);//al objeto libro le pasamos el id
		String[] nombres_libros=ruta.list();//hacemos un array de Strings para que nos devuelva la lista de libros que hay en el directorio
		for(int i=0; i<nombres_libros.length; i++) {//recorremos con for el array
			System.out.println(nombres_libros[i]);//imprimos el array recorrido
		}

		return 0;
	}

	// metodo para ordenar palabra
	public boolean ordenarpalabra(String fichero1, String fichero2, int orden) throws IOException {
		System.out.println("Metodo ordenar palabras");
		BufferedReader br=abrir(fichero1);//buffer de abrir fichero para leerlo
		BufferedWriter wr=escribir(fichero2);//buffer de abrir fichero para escribir en el
		ArrayList<String> palabras=new ArrayList<String>();//arraylist para guardar las palabras del fichero1
		String linea=br.readLine();//leemos una linea del fichero1

		while(linea!= null) {//mientras linea no sea null sigue leyendo
			palabras.add(linea);//añadimos al array con add las palabras que vaya leyendo
			linea=br.readLine();//seguimos leyendo las lineas del fichero1
		}
		Collections.sort(palabras);//utilizamos la clase collection para ordenadar las palabras alfabeticamente
		if(orden==0) {  //si orden es igual a cero escribe el fichero con las palabras por orden alfabético
			for(String palabra:palabras) {//palabra será palabras
				System.out.println(palabra);//imprimos la palabra que contendrá todas las palabras del fichero1
				wr.write(palabra+"\n");//y con write las escribimos en el fichero2
			}
		}
		else {  // escribe las palabras por orden alfabético inverso
			for(int i=0;i<palabras.size();i++) {//recorremos las palabras
				System.out.println(palabras.get(palabras.size()-i-1));//cogemos las palabras y la variable i-1 para que empieze por la ultima
				wr.write(palabras.get(palabras.size()-i-1)+"\n");//la escribimos en el fichero2
			}
		}
		//cerramos los buffers
		intentaCerrar(br);
		intentaCerrar(wr);
		return true;
	}
	}

