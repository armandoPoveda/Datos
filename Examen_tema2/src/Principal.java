
public class Principal {

	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.parseFicheroXml("coches.xml");
		parser.parseDocument();
		parser.print(); 
		
	}
}