package es.uca.sub.Repositorios.Reserva;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservasRepository extends JpaRepository<Reserva,Integer>{

	@Query("SELECT v"
			+ " FROM Reserva v WHERE v.id_usuario=?1")
	List<Reserva> findById_usuario(@Param("id_usuario")Integer id_usuario);
	
	List<Reserva>findAll();
}
