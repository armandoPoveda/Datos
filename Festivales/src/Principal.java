import org.hibernate.Session;

public class Principal {

	public static void main(String[] args) {
		
		Session session= Hibernate.getSessionFactory().openSession();
		session.beginTransaction();
		
		//primera actuacion
		Actuacion actuacion = new Actuacion();
		actuacion.setEscenario(1);
		actuacion.setHora("21:30");
		
		//segunda actuacion
		Actuacion actuacion2 = new Actuacion();
		actuacion2.setEscenario(2);
		actuacion2.setHora("23:00");
		
		//primer festival
		Festival festival = new Festival();
		festival.setFecha("21/03/2018");
		festival.setLugar("Valencia");
		festival.setNombre("Manos Unidas");
		
		//segundo festival
		Festival festival2 = new Festival();
		festival2.setFecha("10/05/2018");
		festival2.setLugar("Madrid");
		festival2.setNombre("Festival Madriles");
		
		//primer grupo
		Grupo grupo = new Grupo();
		grupo.setNombre("Los delincuentes");
		grupo.setEstilo("Flamenco");
		
		//primer miembro del grupo uno
		Miembro miembro = new Miembro();
		miembro.setNombre("Pepe");
		miembro.setInstrumento("Bateria");
		grupo.getMienbros().add(miembro);
		
		//segundo miembro del grupo dos
		Miembro miembro2 = new Miembro();
		miembro2.setNombre("Juan");
		miembro2.setInstrumento("Guitarra");
		grupo.getMienbros().add(miembro2);
		
		//segundo grupo
		Grupo grupo2 = new Grupo();
		grupo2.setNombre("Extremoduro");
		grupo2.setEstilo("Rock");
		
		//primer miembro del grupo 2
		Miembro miembro3 = new Miembro();
		miembro3.setNombre("Rober");
		miembro3.setInstrumento("Guitarra");
		grupo2.getMienbros().add(miembro3);
		
		//miembro 2 del grupo 2
		Miembro miembro4 = new Miembro();
		miembro4.setNombre("Antonio");
		miembro4.setInstrumento("Bajo");
		grupo2.getMienbros().add(miembro4);
		
		//guardamos actuaciones
		session.save(actuacion);
		session.save(actuacion2);
		
		//guardamos festivales
		session.save(festival);
		session.save(festival2);
		
		//guardamos grupos
		session.save(grupo);
		session.save(grupo2);
		
		
		session.getTransaction().commit();
		session.close();
		
		Hibernate.getSessionFactory().close();
	}

}
