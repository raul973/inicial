package es.uca.sub.Repositorios.Auditoria;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {
	
	private AuditoriaRepository repository;

	@Autowired
	public AuditoriaService(AuditoriaRepository repository) {
		this.repository = repository;
	}
	
	public List<Auditoria> findAll() {
		return repository.findAll();
	}

	
	public Auditoria save (Auditoria update) {
		return repository.save(update);
	}
	
	public Optional<Auditoria> getOne(Integer id) {
		return repository.findById(id);
	}
	
	
}
