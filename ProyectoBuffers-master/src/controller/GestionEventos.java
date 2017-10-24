package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import Clase_Libro.Libro;
import model.*;
import view.*;

public class GestionEventos {

	private Libro libro;
	private GestionDatos model;
	private LaunchView view;
	private ActionListener actionListener_comparar, actionListener_buscar, actionListener_copiar,actionListener_a�adirlibro, actionListener_recuperarlibro, actionListener_recuperartodos,actionListener_ordenarpalabra;

	public GestionEventos(GestionDatos model, LaunchView view) {
		this.model = model;
		this.view = view;
	}

	public void contol() {
		//action listener para el boton comparar contenido
		actionListener_comparar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la funci�n call_compararContenido

				call_compararContenido();//llamamos al metodo comparar contenido
			}
		};
		view.getComparar().addActionListener(actionListener_comparar);//coje de la vista del boton comparar

		//escuchador para el bot�n buscar
		actionListener_buscar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// Llamar a la funci�n call_buscarPalabra

				call_buscarPalabra();//llamamos al metodo buscar palabra
			}
		};
		view.getBuscar().addActionListener(actionListener_buscar);//coje de la vista del boton buscar

		//escuchador para el bot�n copiar
		actionListener_copiar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la funci�n call_copiar

				call_copiar();//llamamos al metodo copiar
			}
		};
		view.getCopiar().addActionListener(actionListener_copiar);//coje de la vista del boton copiar

		//escuchador para el bot�n a�adir libro
		actionListener_a�adirlibro = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la funci�n call_copiar
				try {
					call_a�adirlibro();//llamamos al metodo a�adir libro
				} catch (Throwable e) {
					view.showError("El archivo no est� listo");// si el archivo no est� listo que nos muestre un mnesaje de error
				}

			}
		};
		view.getA�adirLibro().addActionListener(actionListener_a�adirlibro);//coje de la vista del boton a�adir libro

		//escuchador para el bot�n recuperar libro
		actionListener_recuperarlibro = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// Llamar a la funci�n call_recuperar

				try {
					call_recuperarlibro();//llamamos al metodo recuperar
				} catch (Throwable e) {
					view.showError("El archivo no est� listo");// si el archivo no est� listo que nos muestre un mnesaje de error
				}

			}
		};
		view.getRecuperarLibro().addActionListener(actionListener_recuperarlibro);//coje de la vista del boton recuperar libro

		//escuchador para recuperar todos los libros
		actionListener_recuperartodos = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la funci�n call_recuperar
				System.out.println("Recuperar todos los libros");
				try {
					call_recuperartodos();//llamamos al metodo recuperar todos
				} catch (Throwable e) {
					view.showError("El Archivo no esta listo");// si el archivo no est� listo que nos muestre un mnesaje de error
				}
			}
		};
		view.getRecuperartodos().addActionListener(actionListener_recuperartodos);//coje de la vista del boton recuperar todos

		//escuchador para el bot�n ordenaar palabra
		actionListener_ordenarpalabra = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				// TODO: Llamar a la funci�n call_copiar
				try {
					call_ordenarpalabras();//llamamos al metodo ordenar palabras
				} catch (NumberFormatException | IOException e) {
				}
			}
		};
		view.getBotonordenarpalabra().addActionListener(actionListener_ordenarpalabra);//coje de la vista del boton ordenar palabras
	}

	//creamos un metodo para compararcontenido
	private int call_compararContenido() {

		// Llamar a la funci�n compararContenido de GestionDatos
		// Gestionar excepciones
		try {
			boolean iguales=model.compararContenido(view.getFichero1().getText(), view.getFichero2().getText());
			view.getTextArea().setText("Los ficheros son: "+iguales);
			if(iguales==true) {
				view.getTextArea().setText("Los ficheros son iguales");
				return 0;
			}
			else
				view.getTextArea().setText("Los ficheros son distintos");

		} catch (FileNotFoundException e) {
			view.showError("El fichero o los ficheros no est�n preparados");//nos muestra un mensaje de error
		} catch (IOException e) {
		}
		return 1;
	}
	//creamos un metodo para buscarPalabra
	private int call_buscarPalabra() {
		// Llamar a la funci�n buscarPalabra de GestionDatos
				// Gestionar excepciones
		try {

			int palabra=model.buscarPalabra(view.getFichero1().getText(), view.getPalabra().getText(), view.getPrimera().isSelected());
			view.getTextArea().setText("La �ltima linea donde se encuentra la palabra es: "+palabra);
			if(palabra<=0)
				return 0;

		} catch (FileNotFoundException e) {

			view.showError("El fichero no est� preparado");//nos muestra un mensaje de error
		} catch (IOException e) {
		}
		return 1;
	}

	//creamos un metodo para copiar
	private int call_copiar() {
		// Llamar a la funci�n copiar de GestionDatos
		// Gestionar excepciones
		try {

			int	copia = model.copiarFicheros(view.getFichero1().getText(), view.getFichero2().getText());
			if(copia <=0) 
				return 0;

		}catch(FileNotFoundException e){
			view.showError("El fichero no est� preparado");//nos muestra un mensaje de error

		} catch (IOException e) {
		}

		return 1;
	}
	//creamos un metodo para a�adir libro
	private int call_a�adirlibro() throws NumberFormatException, FileNotFoundException, IOException {
		// Llamar a la funci�n a�adir libro de GestionDatos
		if(view.getId().getText().isEmpty()){
			view.showError("Alguna caja est� vacia");//nos muestra un mensaje de error
		}
		int a�adir=model.a�adirlibro(view.getId().getText(),view.getTitulo().getText(),view.getAutor().getText(),Integer.parseInt(view.getA�o().getText()),view.getEditor().getText(),Integer.parseInt(view.getPaginas().getText()));

		return 1;
	}
	//creamos un metodo para recuperar libro
	private int call_recuperarlibro() throws FileNotFoundException, ClassNotFoundException, IOException {
		// Llamar a la funci�n recuperar libro de GestionDatos
		if(view.getId().getText().isEmpty()){
			view.showError("Alguna caja est� vacia");//nos muestra un mensaje de error
		}
		int recuperar=model.recuperarlibro(view.getId().getText());
		return 1;
	}
	//creamos un metodo para recuperar todos
	private int call_recuperartodos() throws NumberFormatException, FileNotFoundException, IOException {
		// Llamar a la funci�n recuperartodos de GestionDatos
		int ruta=model.recuperartodos(view.getId().getText());
		return 1;
	}
	//creamos un metodo para ordenar palabras
	private int call_ordenarpalabras() throws NumberFormatException, FileNotFoundException, IOException {
		// Llamar a la funci�n ordenar palabras de GestionDatos
		if(view.getCheckBoxOrdenado().isSelected())
			model.ordenarpalabra(view.getFichero1().getText(),view.getFichero2().getText(),0);
		else {
			model.ordenarpalabra(view.getFichero1().getText(),view.getFichero2().getText(),1);
		}
		return 1;
	}
}
