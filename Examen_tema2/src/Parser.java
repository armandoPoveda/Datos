
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

	private Document dom = null;
	private ArrayList<Taller> concesionarios = new ArrayList<Taller>();

	public Parser() {

	}

	public void parseFicheroXml(String fichero) {
		// creamos una factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// creamos un documentbuilder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parseamos el XML y obtenemos una representación DOM
			dom = db.parse(fichero);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public void parseDocument() {
		// obtenemos el elemento raíz
		Element docEle = dom.getDocumentElement();

		NodeList n1 = docEle.getElementsByTagName("taller");

		if (n1 != null && n1.getLength() > 0) {
			for (int i = 0; i < n1.getLength(); i++) {
				Element eleTaller = (Element) n1.item(i);

				Taller taller = new Taller();
				NodeList n2 = eleTaller.getElementsByTagName("nombre");

				if (n2 != null && n2.getLength() > 0) {
					Element eleNombre = (Element) n2.item(0);
					String Nombre = eleNombre.getFirstChild().getNodeValue();
					taller.setNombreTaller(Nombre);

					NodeList n3 = eleTaller.getElementsByTagName("direccion");

					if (n3 != null && n3.getLength() > 0) {
						for (int j = 0; j < n3.getLength(); j++) {

							Element eleDireccion = (Element) n3.item(j);
							NodeList n4 = eleDireccion.getElementsByTagName("nombre");
							NodeList n5 = eleDireccion.getElementsByTagName("poblacion");

							if (n4 != null && n4.getLength() > 0 || n5 != null && n5.getLength() > 0) {

								Element eleDireNombre = (Element) n4.item(0);
								Element eleDirePoblacion = (Element) n5.item(0);

								String DirePoblacion = eleDirePoblacion.getFirstChild().getNodeValue();
								String DireNombre = eleDireNombre.getFirstChild().getNodeValue();

								
								taller.setNombreCalle(DireNombre);
								taller.setPoblacion(DirePoblacion);

								NodeList n6 = eleTaller.getElementsByTagName("coches");

								if (n6 != null && n6.getLength() > 0) {
									for (int l = 0; l < n6.getLength(); l++) {
										
										Element eleCoches = (Element) n6.item(l);
										NodeList n7 = eleCoches.getElementsByTagName("coche");

										if (n7 != null && n7.getLength() > 0) {
											for (int h = 0; h < n7.getLength(); h++) {
												
												Element eleCoche = (Element) n7.item(h);
												coches coche = new coches();

												NodeList n9 = eleCoche.getElementsByTagName("modelo");
												NodeList n8 = eleCoche.getElementsByTagName("marca");
												if (n8 != null && n8.getLength() > 0 || n9 != null && n9.getLength() > 0) {
														Element eleMarca = (Element) n8.item(0);
														Element eleModelo = (Element) n9.item(0);
														String Marca = eleMarca.getFirstChild().getNodeValue();
														String Modelo = eleModelo.getFirstChild().getNodeValue();
														coche.setMarca(Marca);
														coche.setModelo(Modelo);
													}
												taller.getCoche().add(coche);
											}
										}
									}
								}
							}

						}

					}
				}
				concesionarios.add(taller);
			}

		}
	}

	public void print() {
		Iterator it = concesionarios.iterator();
		while (it.hasNext()) {
			Taller t = (Taller) it.next();
			t.print();
			System.out.println("-----------------------------\n");
		}
	}

}
