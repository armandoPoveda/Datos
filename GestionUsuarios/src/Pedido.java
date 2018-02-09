import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

	private int id;
	private String fecha;	
	private Empresa empresa;
	private List<Item> items = new ArrayList<Item>();
	
	public Pedido() {}
	
	
	public Pedido(String date) {
		
		//this.id = id;
		this.fecha = date;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
