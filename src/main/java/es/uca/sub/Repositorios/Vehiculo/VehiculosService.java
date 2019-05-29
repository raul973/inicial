package es.uca.sub.Repositorios.Vehiculo;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VehiculosService {

	private VehiculosRepository carsRepository;
	
	@Autowired
	public VehiculosService(VehiculosRepository vec) {
		this.carsRepository=vec;
	}
	//Devuelve todos los vehículos
		public List<Vehiculo> findAll() {
			return carsRepository.findAll();
		}

	//Almacena un vehículo
	public Vehiculo save (Vehiculo entrada) {
		return carsRepository.save(entrada);
	}
	//Obtiene un vehículo
	public Optional<Vehiculo> getOne(Integer id) {
		return carsRepository.findById(id);
	}
	
	public List<Vehiculo> findByMarcaModeloEstado(String marca,String modelo,String estado){
		return carsRepository.findByMarcaModeloEstado(marca,modelo,estado);
	}
	
	public List<Vehiculo> findByModeloEstado(String modelo,String estado){
		return carsRepository.findByModeloEstado(modelo,estado);
	}
	
	public List<Vehiculo> findByMarcaEstado(String marca,String estado){
		return carsRepository.findByMarcaEstado(marca,estado);
	}
	
	public List<Vehiculo> findByMarcaModelo(String marca,String modelo){
		return carsRepository.findByMarcaModelo(marca,modelo);
	}
	
	public List<Vehiculo> findByMarca(String marca){
		return carsRepository.findByMarca(marca);
	}
	
	public List<Vehiculo> findByModelo(String modelo){
		return carsRepository.findByModelo(modelo);
	}	

	public List<Vehiculo> findByCaracteristicas(String carac){
		return carsRepository.findByModelo(carac);
	}
	public List<Vehiculo> findByEstado(String est){
		return carsRepository.findByEstado(est);
	}
	public List<Vehiculo> findByMatricula(String mat){
		return carsRepository.findByMatricula(mat);
	}
	
	public List<Vehiculo> findByPrecio(String texto){
		List<Vehiculo> aux=null;
		String[] variables=texto.split(",");
		if(variables[1].equals("between")) {
			aux=carsRepository.findByPrecioBetween(Integer.valueOf(variables[0]), Integer.valueOf(variables[2]));
		}else if(variables[1].equals("<")) {
			aux=carsRepository.findByPrecioMenor(Integer.valueOf(variables[0]));
		}else if(variables[1].equals("<=")) {
			aux=carsRepository.findByPrecioMenorIgual(Integer.valueOf(variables[0]));
		}else if(variables[1].equals(">")) {
			aux=carsRepository.findByPrecioMayor(Integer.valueOf(variables[0]));
		}else if(variables[1].equals(">=")) {
			aux=carsRepository.findByPrecioMayorIgual(Integer.valueOf(variables[0]));
		}
		
		return aux;
	}

	
	public List<Vehiculo>findByPrecioBetween(Integer number1,Integer number2){
		List<Vehiculo> aux=null;
		List<Vehiculo> aux2=null;
		aux=carsRepository.findByPrecioMayorIgual(number1);
		aux2=carsRepository.findByPrecioMenorIgual(number2);
		Set<Vehiculo> result = aux.stream()
				  .distinct()
				  .filter(aux2::contains)
				  .collect(Collectors.toSet());
		
		return new ArrayList<>(result);
		
	
		
		
	}
	
	
	public List<Vehiculo>findByMarcaModeloEstadoPrecio(String marca,String modelo,String estado,String texto){
		List<Vehiculo> aux=null;
		List<Vehiculo> aux2=null;
		aux=this.findByMarcaModeloEstado(marca,modelo,estado);
		aux2=this.findByPrecio(texto);
		Set<Vehiculo> result = aux.stream()
				  .distinct()
				  .filter(aux2::contains)
				  .collect(Collectors.toSet());
		
	 return  new  ArrayList<>(result);
		
	
	}
	
	
	
	public List<Vehiculo>findByModeloEstadoPrecio(String modelo,String estado,String texto){
		List<Vehiculo> aux=null;
		List<Vehiculo> aux2=null;
		aux=this.findByModeloEstado(modelo,estado);
		aux2=this.findByPrecio(texto);
		Set<Vehiculo> result = aux.stream()
				  .distinct()
				  .filter(aux2::contains)
				  .collect(Collectors.toSet());
		
		return new ArrayList<>(result);
		
	}
	
	
	
	public List<Vehiculo>findByMarcaEstadoPrecio(String marca,String estado,String texto){
		List<Vehiculo> aux=null;
		List<Vehiculo> aux2=null;
		aux=this.findByMarcaEstado(marca,estado);
		aux2=this.findByPrecio(texto);
		Set<Vehiculo> result = aux.stream()
				  .distinct()
				  .filter(aux2::contains)
				  .collect(Collectors.toSet());
		
		 return  new  ArrayList<>(result);
	}
	
	public List<Vehiculo>findByMarcaModeloPrecio(String marca,String modelo,String texto){
		List<Vehiculo> aux=null;
		List<Vehiculo> aux2=null;
		aux=this.findByMarcaModelo(marca,modelo);
		aux2=this.findByPrecio(texto);
		Set<Vehiculo> result = aux.stream()
				  .distinct()
				  .filter(aux2::contains)
				  .collect(Collectors.toSet());
	
		
		return new ArrayList<>(result);

	}
	
	
	
	
	
	
	public List<Vehiculo>findByEstadoPrecio(String estado,String texto){
		List<Vehiculo> aux=null;
		List<Vehiculo> aux2=null;
		aux=this.findByEstado(estado);
		aux2=this.findByPrecio(texto);
		Set<Vehiculo> result = aux.stream()
				  .distinct()
				  .filter(aux2::contains)
				  .collect(Collectors.toSet());
		
		return new ArrayList<>(result);
		
	}
	
	public List<Vehiculo>findByModeloPrecio(String modelo,String texto){
		List<Vehiculo> aux=null;
		List<Vehiculo> aux2=null;
		aux=this.findByModelo(modelo);
		aux2=this.findByPrecio(texto);
		Set<Vehiculo> result = aux.stream()
				  .distinct()
				  .filter(aux2::contains)
				  .collect(Collectors.toSet());
		
		return new ArrayList<>(result);
		

	}
	
	
	
	
	public List<Vehiculo>findByMarcaPrecio(String marca,String texto){
		
		List<Vehiculo> aux=null;
		List<Vehiculo> aux2=null;
		aux=this.findByMarca(marca);
		aux2=this.findByPrecio(texto);
		
		Set<Vehiculo> result = aux.stream()
				  .distinct()
				  .filter(aux2::contains)
				  .collect(Collectors.toSet());
		
		return new ArrayList<>(result);
		
	}
	
	
	
	
	
	
	
	
	//Borra un vehículo dados todos sus datos
	public void delete(Vehiculo car) {
	  carsRepository.delete(car);
	}
	//Borra todos los vehículos
	public void deleteAll() {
		  carsRepository.deleteAll();
		}
	//Borra un vehículo por su clave primaria
	public void deleteById(Integer id) {
		  carsRepository.deleteById(id);
		}
	//Comprueba si existe un vehículo por su clave primaria
	public boolean existCar(Integer id) {
		  return carsRepository.existsById(id);
		}
	public Vehiculo updateEstado(Vehiculo vehiculo) {
		return carsRepository.save(vehiculo);
	}
	
	
	
	
}
