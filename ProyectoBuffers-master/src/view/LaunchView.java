package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LaunchView extends JFrame {

	private JButton comparar,buscar, copiar,recuperarLibro, a�adirLibro, recuperartodos,botonordenarpalabra;
	private JTextArea textArea;
	private JTextField fichero1,fichero2,palabra, Id, Titulo, Autor, A�o, Editor, Paginas;
	private JLabel label_f1,label_f2,label_pal, label_Id, label_titulo, label_autor, label_a�o, label_editor, label_paginas;
	private JCheckBox primera,CheckBoxOrdenado;
	private JPanel panel;
	

	public LaunchView() {
		
		setBounds(200,200,1142,485);
		setTitle("Proyecto Buffers");	
		panel = new JPanel();
		comparar = new JButton("Comparar contenido");
		comparar.setPreferredSize(new Dimension(150, 26));
		buscar = new JButton("Buscar palabra");
		buscar.setPreferredSize(new Dimension(150, 26));
		copiar = new JButton("Copiar archivo");
		copiar.setPreferredSize(new Dimension(150, 26));
		a�adirLibro = new JButton("Guardar libro"); 
		a�adirLibro.setPreferredSize(new Dimension(150, 26));
		recuperarLibro = new JButton("Recuperar libro");
		recuperarLibro.setPreferredSize(new Dimension(150, 26));
		recuperartodos = new JButton("Recuperar libros");
		recuperartodos.setPreferredSize(new Dimension(150, 26));
		botonordenarpalabra = new JButton("Ordenar palabra");
		botonordenarpalabra.setPreferredSize(new Dimension(150, 26));

		fichero1 = new JTextField("",10);
		fichero2 = new JTextField("",10);
		palabra = new JTextField("",10);
		Id = new JTextField("",10);
		Titulo = new JTextField("",10);
		Autor = new JTextField("",10);
		A�o = new JTextField("",10);
		Editor = new JTextField("",10);
		Paginas = new JTextField("",10);


		
		label_f1 = new JLabel("Fichero 1:");
		label_f2 = new JLabel("Fichero 2:");
		label_pal = new JLabel("Palabra:");
		label_Id = new JLabel("Id:");
		label_titulo = new JLabel("Titulo:");
		label_autor = new JLabel("Autor:");
		label_a�o = new JLabel("A�o:");
		label_editor = new JLabel("Editor:");
		label_paginas = new JLabel("Paginas:");


		CheckBoxOrdenado = new JCheckBox("Ordenado");
		primera = new JCheckBox("Primera aparici�n");

		textArea = new JTextArea(20, 80);
		textArea.setBounds(50,50,50,50);
		textArea.setEditable(false);		
		
		
		
		panel.add(copiar);
		panel.add(comparar);
		panel.add(buscar);
		panel.add(label_f1);
		panel.add(fichero1);
		panel.add(label_f2);
		panel.add(fichero2);
		panel.add(label_pal);
		panel.add(palabra);
		panel.add(primera);
		panel.add(label_Id);
		panel.add(Id);
		panel.add(label_titulo);
		panel.add(Titulo);
		panel.add(label_autor);
		panel.add(Autor);
		panel.add(label_a�o);
		panel.add(A�o);
		panel.add(label_editor);
		panel.add(Editor);
		panel.add(label_paginas);
		panel.add(Paginas);
		panel.add(a�adirLibro);
		panel.add(recuperarLibro);
		panel.add(recuperartodos);
		panel.add(CheckBoxOrdenado);
		panel.add(botonordenarpalabra);
		panel.add(textArea);

        // A�adimos el JPanel al JFrame
        this.getContentPane().add(panel);		 
		
	}	
	public JCheckBox getPrimera() {
		return primera;
	}

	public void setPrimera(JCheckBox primera) {
		this.primera = primera;
	}

	
	public JCheckBox getCheckBoxOrdenado() {
		return CheckBoxOrdenado;
	}

	public void setCheckBoxOrdenado(JCheckBox checkBoxOrdenado) {
		CheckBoxOrdenado = checkBoxOrdenado;
	}

	public JButton getBotonordenarpalabra() {
		return botonordenarpalabra;
	}

	public void setBotonordenarpalabra(JButton botonordenarpalabra) {
		this.botonordenarpalabra = botonordenarpalabra;
	}

	public JButton getRecuperartodos() {
		return recuperartodos;
	}

	public void setRecuperartodos(JButton recuperartodos) {
		this.recuperartodos = recuperartodos;
	}

	public JButton getComparar() {
		return comparar;
	}

	public void setComparar(JButton comparar) {
		this.comparar = comparar;
	}

	public JButton getBuscar() {
		return buscar;
	}

	public void setBuscar(JButton buscar) {
		this.buscar = buscar;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
			
	public JTextField getFichero1() {
		return fichero1;
	}

	public void setFichero1(JTextField fichero1) {
		this.fichero1 = fichero1;
	}

	public JTextField getFichero2() {
		return fichero2;
	}

	public void setFichero2(JTextField fichero2) {
		this.fichero2 = fichero2;
	}

	public void showError(String m){
		JOptionPane.showMessageDialog(this.panel,
			    m,
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
	}

	public JTextField getPalabra() {
		return palabra;
	}

	public void setPalabra(JTextField palabra) {
		this.palabra = palabra;
	}

	public JButton getCopiar() {
		return copiar;
	}

	public void setCopiar(JButton copiar) {
		this.copiar = copiar;
	}

	public JButton getRecuperarLibro() {
		return recuperarLibro;
	}

	public void setRecuperarLibro(JButton recuperarLibro) {
		this.recuperarLibro = recuperarLibro;
	}

	public JButton getA�adirLibro() {
		return a�adirLibro;
	}

	public void setA�adirLibro(JButton a�adirLibro) {
		this.a�adirLibro = a�adirLibro;
	}

	public JTextField getId() {
		return Id;
	}

	public void setId(JTextField id) {
		Id = id;
	}

	public JTextField getTitulo() {
		return Titulo;
	}

	public void setTitulo(JTextField titulo) {
		Titulo = titulo;
	}

	public JTextField getAutor() {
		return Autor;
	}

	public void setAutor(JTextField autor) {
		Autor = autor;
	}

	public JTextField getA�o() {
		return A�o;
	}

	public void setA�o(JTextField a�o) {
		A�o = a�o;
	}

	public JTextField getEditor() {
		return Editor;
	}

	public void setEditor(JTextField editor) {
		Editor = editor;
	}

	public JTextField getPaginas() {
		return Paginas;
	}

	public void setPaginas(JTextField paginas) {
		Paginas = paginas;
	}

	

	
}
