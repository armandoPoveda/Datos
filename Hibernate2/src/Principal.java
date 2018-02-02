import org.hibernate.Session;

public class Principal {

	public static void main(String[] args) {
		
		Session session= Hibernate.getSessionFactory().openSession();
		session.beginTransaction();
		
		//a�adimos una empresa
		Empresa empresa = new Empresa();
		empresa.setCif("12345");
		empresa.setDireccion("aldaya");
		empresa.setEmpleados(20);
		empresa.setNombre("Edicom");
		
		//a�adimos otra empresa
		Empresa empresa2 = new Empresa();
		empresa2.setCif("98765");
		empresa2.setDireccion("alacuas");
		empresa2.setEmpleados(50);
		empresa2.setNombre("Vidal");
		
		//a�adimos un pedidos
		Pedido pedido = new Pedido();
		pedido.setFecha("10/03/2017");
		
		//a�adimos otro pedido
		Pedido pedido2 = new Pedido();
		pedido2.setFecha("20/03/2016");
		
		//a�adimos un item
		Item item = new Item();
		item.setCantidad(20);
		item.setNombre("cuchillos");
		
		//a�adimos un segundo item
		Item item2 = new Item();
		item2.setCantidad(40);
		item2.setNombre("tenedores");

		//a�adimos un tercer item
		Item item3 = new Item();
		item3.setCantidad(60);
		item3.setNombre("cucharas");
		
		//a�adimos al pedido 1 los item2 y item3
		pedido.getItem().add(item);
		pedido.getItem().add(item2);
		
		//a�adimos el pedido 2 el item3
		pedido2.getItem().add(item3);
		
		//guardamos todo en la BBDD
		session.save(empresa);
		session.save(empresa2);
		session.save(pedido);
		session.save(pedido2);
		
		session.getTransaction().commit();
		session.close();
		
		Hibernate.getSessionFactory().close();
	}

}
