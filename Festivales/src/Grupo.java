import java.util.ArrayList;
import java.util.List;

public class Grupo {
	
	private int id;
	private String nombre;
	private String estilo;
	
	private List<Miembro> miembros = new ArrayList<Miembro>();
	
	public Grupo() {
		
	}
	
	public Grupo(int id, String nombre, String estilo) {
		
		this.id=id;
		this.nombre = nombre;
		this.estilo = estilo;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public List<Miembro> getMienbros() {
		return miembros;
	}

	public void setMienbros(List<Miembro> mienbros) {
		this.miembros = mienbros;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
