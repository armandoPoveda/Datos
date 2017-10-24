package Clase_Libro;

import java.io.Serializable;

public class Libro implements Serializable {// con la clase serializable lo que hacemos es convertir en bytes el objeto o clase que vamos a utilizar
	private String id;
	private String titulo;
	private String autor;
	private int año;
	private String editor;
	private int numero_paginas;
	
	public Libro() {
	}
	
	
	public Libro(String id, String t, String a, int any, String ed, int np ) {
		this.id=id;
		titulo=t;
		autor=a;
		año=any;
		editor=ed;
		numero_paginas=np;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public int getNumero_paginas() {
		return numero_paginas;
	}

	public void setNumero_paginas(int numero_paginas) {
		this.numero_paginas = numero_paginas;
	}
	
	public void print() {
		System.out.print("Id: "+id+"Titulo: "+titulo+"autor: "+autor+"año: "+año+"Editor: "+editor+"numero de paginas: "+numero_paginas );
	}
	
	public String toString() {
		String textolibro="El id:/n "+id+" Titulo:/n "+titulo+" Autor:/n"+autor+" año:/n"+editor+ "Editor:/n"+numero_paginas+ "numero_paginas:/n"; 
		 
		return textolibro;
		
	}
}
