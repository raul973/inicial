package es.uca.sub.Repositorios.Cargos;




import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;





@Entity(name="Cargo")

public class Cargo {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id_cargo;
	
	private String tipo;
	private int vehiculo;
	private int usuario;
	private int reserva;
	private int importe;
	public  Cargo() {}
	/**
	 * Constructor Usuario
	 * @param nombre
	 * @param apellidos
	 * @param username
	 * @param contrasena
	 * @param email
	 * @param rol
	 */
	public Cargo(String tipo,int vehiculo,int usuario,int reserva,int importe)
	{
		
		this.tipo=tipo;
		this.vehiculo=vehiculo;
		this.reserva=reserva;
		this.importe=importe;
		this.usuario=usuario;
	}
	
	/**
	 * getters and setters
	 */
	public Integer getId() {
		return id_cargo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
	   this.tipo=tipo;
	}
	public void setVehiculo(int car) {
		this.vehiculo = car;
	}
	public int getVehiculo() {
		return this.vehiculo;
	}
	public int getUsuario() {
		return this.usuario;
	}
	public void setUsuario(int us) {
		this.usuario = us;
	}
	
	public void setImporte(int importe) {
		this.importe= importe;
	}
	public int getImporte() {
		return this.importe;
	}
	public void setReserva(int reserva) {
		this.reserva= reserva;
	}
	public int getReserva() {
		return this.reserva;
	}
	

}