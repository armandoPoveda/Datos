package Principal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserHtml {
	

	public static void main(String[] args) {
		
		
		// creamos un objeto del tipo Document con la libreria d Jsoup
		Document Html = null;
		try {
			// hacemos un connect de Jsoup
			 Html = Jsoup.connect("http://www.pccomponentes.com/").get();
		} catch (IOException e) {
			System.out.println("la página no fue encontrada");
		}
		
		//sacamos el titulo del html
		String titulo = Html.title();
		System.out.println(titulo);
		 
		//con select selecionamos la parte de la página html que queremos
		 Elements articulos = Html.select("div[class=col-xs-6 col-sm-4 col-md-3 col-lg-3 article-block]");
		 
		 //recorremos con un for each los Elementos de div
		 for(Element art: articulos) {
			 //cogemos el atributo de los prodcutos
			 Elements productos = art.getElementsByAttribute("data-loop");
			 //cogemos el precio actual del atributo class y su valor
			 Elements precio = art.getElementsByAttributeValue("class","tarjeta-articulo__precio-actual");
			 //cogemos el descuento que tienen los prodcutos del atributo class y su valor, ademas comprobamos
			 //si tienen texto
			 boolean descuento = art.getElementsByAttributeValue("class","tarjeta-articulo__descuento").hasText();
			 //guardamos en un string los atributos que tengan texto en descuento
			 String descuento1 = art.getElementsByAttributeValue("class","tarjeta-articulo__descuento").text();
			 //cogemos el texto del precio que tenia el producto antes del descuento
			 Elements precioAntiguo = art.getElementsByAttributeValue("class","tarjeta-articulo__pvp");
			 if (descuento==true) {
				 System.out.println("\nProducto: "+productos.text()+"/Antes: "+precioAntiguo.text()+", Descuento: "+descuento1+", Ahora: "+precio.text());
			 }else
			 System.out.println("\nProducto: "+productos.text()+"/Precio: "+precio.text());
		 }
	}

}
