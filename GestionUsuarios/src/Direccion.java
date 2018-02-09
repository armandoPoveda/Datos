
public class Direccion {

	private String cif;
	private String calle;
	private String poblacion;
	private int cp;
	private Empresa Empresa;
	
	
	public String getCalle() {
		return calle;
	}


	public void setCalle(String calle) {
		this.calle = calle;
	}


	public String getPoblacion() {
		return poblacion;
	}


	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}


	public int getCp() {
		return cp;
	}


	public void setCp(int cp) {
		this.cp = cp;
	}


	public String getCif() {
		return cif;
	}


	public void setCif(String cif) {
		this.cif = cif;
	}


	public Empresa getEmpresa() {
		return Empresa;
	}


	public void setEmpresa(Empresa empresa) {
		Empresa = empresa;
	}

}
