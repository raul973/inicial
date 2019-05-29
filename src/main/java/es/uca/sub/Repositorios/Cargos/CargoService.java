package es.uca.sub.Repositorios.Cargos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CargoService {

	private CargoRepository cargoRepository;
	
	@Autowired
	public CargoService(CargoRepository vec) {
		this.cargoRepository=vec;
	}
	

	//Almacena un cargo
	public Cargo save (Cargo cargo) {
		return cargoRepository.save(cargo);
	}
	//Almacena un cargo 		
	public List<Cargo> findAll () { 			
		return cargoRepository.findAll(); 		
		}
	
	
}
