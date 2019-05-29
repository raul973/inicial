package es.uca.sub.Repositorios.Auditoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria,Integer>{

	List<Auditoria>findAll();
}
