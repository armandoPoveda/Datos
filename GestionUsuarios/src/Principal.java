
import java.sql.Date;

import org.hibernate.Session;


public class Principal {

	public static void main(String[] args) {
		
			Session session= Hibernate.getSessionFactory().openSession();
			session.beginTransaction();
			
			//generamos una empresa con sus datos y hacemos una relacion ono to one con direccion con el cif de la empresa
			Empresa e = new Empresa();
			e.setCif("B43343443");
			e.setNombre("Desarroll S.L.");
			e.setEmpleados(4);
			e.setCiudad("Valencia");
			e.getDireccionEmpresa().setCalle("castillo");
			e.getDireccionEmpresa().setCp(46970);
			e.getDireccionEmpresa().setPoblacion("aldaia");
			e.addPedido(new Pedido("10/12/2005"));
			e.addPedido(new Pedido("3/5/2018"));
						
			//generamos otra empresa con sus datos y hacemos una relacion one to one con direccion con el cif de la empresa
			Empresa e2 = new Empresa();
			e2.setCif("1234658");
			e2.setNombre("EDICOM");
			e2.setEmpleados(150);
			e2.setCiudad("Madrid");
			e2.getDireccionEmpresa().setCalle("general palafox");
			e2.getDireccionEmpresa().setCp(46960);
			e2.getDireccionEmpresa().setPoblacion("alacuas");
			e2.addPedido(new Pedido("2/2/2013"));
			e2.addPedido(new Pedido("5/12/2009"));
			
			//generamos un pedido con su fecha
			Pedido p = new Pedido();
			//p.setFecha("20/10");
			
			//generamos un item con sus datos
			Item i = new Item();
			i.setCantidad(2);
			i.setNombre("ordenador");
			//añadimos el item al pedido
			p.getItems().add(i);

			//generamos otro item con sus datos
			Item i2 = new Item();
			i2.setCantidad(3);
			i2.setNombre("raton");
			//añadimos el segundo item al primer pedido
			p.getItems().add(i2);
			
			//generamos un tercer item y añadimos sus datos
			Item i3 = new Item();
			i3.setCantidad(2);
			i3.setNombre("teclado");
			//añadimos el item al primer pedido
			p.getItems().add(i3);

			//generamos un segundo pedido con su fecha
			Pedido p2 = new Pedido();
			//p2.setFecha("05/01");

			//generamos un cuarto item con sus datos
			Item i4 = new Item();
			i4.setCantidad(2);
			i4.setNombre("mesa");
			//añadimos el cuarto item al segundo pedido
			p2.getItems().add(i4);

			//guardamos la Session de cada objeto generado
			session.save(e);
			session.save(e2);
			//session.save(p);
			//session.save(p2);
			
			session.getTransaction().commit();
			session.close();
			
			Hibernate.getSessionFactory().close();
			
	}

}
