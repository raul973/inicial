package es.uca.sub.Repositorios.Reserva;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservasService {
	ReservasRepository reservaRepository;
	
	@Autowired
	public ReservasService(ReservasRepository reser) {
		reservaRepository=reser;
	}
	
	public Reserva save(Reserva res) {
		return reservaRepository.save(res);
	}
	public Optional<Reserva> getOne(Integer id){
		return reservaRepository.findById(id);
	}
	public List<Reserva> findAll() {
		return reservaRepository.findAll();
	}
	
	public void delete(Reserva res) {
		 reservaRepository.delete(res);
	}
	public void deleteAll() {
		reservaRepository.deleteAll();
	}
	public void deleteById(Integer id) {
		reservaRepository.deleteById(id);
	}
	public boolean existReserva(Integer id) {
		return reservaRepository.existsById(id);
	}
	
	public List<Reserva>findById_usuario(Integer id_usuario){
		return reservaRepository.findById_usuario(id_usuario);
	}
	
	
	
	
	

}
