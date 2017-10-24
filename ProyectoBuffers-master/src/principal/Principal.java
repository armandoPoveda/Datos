package principal;

import javax.swing.JFrame;

import Clase_Libro.Libro;
import model.*;
import view.*;
import controller.*;

public class Principal {

	public static void main(String[] args) {		
	
		GestionDatos model = new GestionDatos();
		Libro libro=new Libro();
		
		LaunchView view = new LaunchView();
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setVisible(true);
		
		GestionEventos controller = new GestionEventos(model,view);
		controller.contol();
		
	}

}
