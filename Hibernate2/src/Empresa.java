import java.util.ArrayList;
import java.util.List;

public class Empresa {
	
	private String cif;
	private String direccion;
	private int empleados;
	private String nombre;
	private List<Pedido> pedido = new ArrayList<Pedido>();
	
	public Empresa() {
		
	}
	
	public Empresa(String cif, String direccion, int empleados, String nombre) {
		
		this.cif = cif;
		this.direccion = direccion;
		this.empleados = empleados;
		this.nombre = nombre;
	}
	
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getEmpleados() {
		return empleados;
	}
	public void setEmpleados(int empleados) {
		this.empleados = empleados;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

}
