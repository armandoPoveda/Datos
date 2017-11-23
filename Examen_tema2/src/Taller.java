import java.util.ArrayList;

public class Taller {
	
	String nombreTaller;
	String poblacion;
	String nombreCalle;
	ArrayList<coches> coche = new ArrayList<coches>();

	public ArrayList<coches> getCoche() {
		return coche;
	}
	
	public void setCoche(ArrayList<coches> coche) {
		this.coche = coche;
	}
	
	public String getNombreTaller() {
		return nombreTaller;
	}

	public void setNombreTaller(String nombreTaller) {
		this.nombreTaller = nombreTaller;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getNombreCalle() {
		return nombreCalle;
	}

	public void setNombreCalle(String nombreCalle) {
		this.nombreCalle = nombreCalle;
	}

	public void print() {
		System.out.println("Concesionario: "+nombreTaller);
		System.out.println("Dirección: "+nombreCalle+"\nPoblación: "+poblacion);
		for(int i=0; i<coche.size(); i++) {
			System.out.println("Marca: "+coche.get(i).getMarca()+", Modelo: "+coche.get(i).getModelo());
		}
	}
 }
