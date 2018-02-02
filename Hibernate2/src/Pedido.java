import java.util.ArrayList;
import java.util.List;

public class Pedido {
	
	private int id;
	private String fecha;
	private List<Item> item = new ArrayList<Item>();
	
	public Pedido() {
		
	}
	
	public Pedido(int id, String fecha) {
		
		this.id = id;
		this.fecha = fecha;
		
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
	public List<Item> getItem() {
		return item;
	}
	public void setItem(List<Item> item) {
		this.item = item;
	}
	
}
