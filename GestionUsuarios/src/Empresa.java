import java.util.ArrayList;
import java.util.List;

public class Empresa {
	
	private String cif;
	private String nombre;
	private int empleados;
	private String ciudad;
	private Direccion direccionEmpresa;
	
	private List<Pedido> pedido = new ArrayList<Pedido>();

	public Empresa(){
		
		setDireccionEmpresa(new Direccion());
	}
	
	
	public String getCif() {
		return cif;
	}
	
	public void setCif(String cif) {
		this.cif = cif;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getEmpleados() {
		return empleados;
	}
	
	public void setEmpleados(int empleados) {
		this.empleados = empleados;
	}
	
	
	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public Direccion getDireccionEmpresa() {
		return direccionEmpresa;
	}

	public void setDireccionEmpresa(Direccion direccionEmpresa) {
		this.direccionEmpresa = direccionEmpresa;
		direccionEmpresa.setEmpresa(this);
	}
	public List<Pedido> getPedido() {
		return pedido;
	}
	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}
	public void addPedido(Pedido pedido) {
		pedido.setEmpresa(this);
		this.pedido.add(pedido);
	}
	
}
