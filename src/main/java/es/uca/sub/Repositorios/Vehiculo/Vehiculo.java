package es.uca.sub.Repositorios.Vehiculo;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Vehiculo")
public class Vehiculo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_vehiculo;

	private String matricula;
	private String marca;
	private String modelo;
	private String caracteristicas;
	private String estado;
	private String imagen;
	private String periodo;

	private Integer precio;

	/**
	 * Constructor vacio
	 */

	public Vehiculo() {
	}

	public Vehiculo(Integer id_vehiculo, String matricula, String marca, String modelo, String caracteristicas,
			String estado, int precio, String image, String periodo) {

		this.id_vehiculo = id_vehiculo;
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.caracteristicas = caracteristicas;
		this.estado = estado;
		this.imagen = image;
		this.precio = precio;
		this.periodo = periodo;
	}

	/**
	 * Constructor de vehiculo
	 * 
	 * @param matricula
	 * @param marca
	 * @param modelo
	 * @param caracteristicas
	 * @param estado
	 * @param disponibilidad
	 * @param precio
	 */
	public Vehiculo(String matricula, String marca, String modelo, String caracteristicas, String estado,
			 int precio, String image, String periodo) {

		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.caracteristicas = caracteristicas;
		this.estado = estado;
		this.imagen = image;
		this.precio = precio;
		this.periodo = periodo;
	}

	/**
	 * Genera matricula nueva
	 * 
	 * @return matricula
	 */

	/**
	 * Getters and Setters
	 */
	public String getImagen() {
		return imagen;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public void setImagen(String image) {
		this.imagen = image;
	}

	public String getMatricula() {
		return matricula;
	}

	public Integer getId() {
		return id_vehiculo;
	}

	public void setId(Integer value) {
		this.id_vehiculo = value;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public boolean isAvailable() {
		
		return (this.estado.equals("Disponible"));
		
	}
	

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Vehiculo))
			return false;
		Vehiculo other = (Vehiculo) o;
		
		return (this.getId()==other.getId());
	}

	@Override
	public int hashCode() {
		
		return super.hashCode();
	}
	
	

}
