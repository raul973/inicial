package es.uca.sub.Repositorios.Reserva;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="Reserva")
public class Reserva implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idReserva;
	
	private Integer id_usuario;
	private Integer id_vehiculo;
	private Integer importe;
	
	private String seguro;
	private String reporte;
	
	private Date fecha_inicio;
	private Date fecha_fin;
	private Date fecha_reserva;
	/**
	 * Constructor vacio
	 */
	public Reserva() {}
	/**
	 * Contructor de reserva
	 * @param fecha_inicio
	 * @param fecha_fin
	 * @param seguro
	 */
	public Reserva(int id_usuario,int id_vehiculo,Date fecha_inicio,Date fecha_fin,String seguro,Date fecha_reserva,int importe)
	{
		this.id_usuario = id_usuario;
		this.id_vehiculo = id_vehiculo;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.seguro = seguro;
		this.fecha_reserva=fecha_reserva;
		this.importe=importe;
		
		
	}
	
	/**
	 * Getters and setters
	 */
	public Integer getId() {
		return idReserva;
	}
	public void setId(Integer idReserva) {
		this.idReserva=idReserva;
	}
	public Integer getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}
	public Integer getId_vehiculo() {
		return id_vehiculo;
	}
	public void setId_vehiculo(Integer id_vehiculo) {
		this.id_vehiculo = id_vehiculo;
	}
	
	public Date getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(Date fecha_inicio) {
		
		this.fecha_inicio = fecha_inicio;
	}
	public Date getFecha_fin() {
		return fecha_fin;
	}
	public void setFecha_fin(Date fecha_fin) {
		
		this.fecha_fin = fecha_fin;
	}
	public String getSeguro() {
		return seguro;
	}
	public void setSeguro(String seguro) {
		this.seguro = seguro;
	}
	
	public String getReporte() {
		return reporte;
	}
	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	public Integer getImporte() {
		return importe;
	}
	public void setImporte(Integer importe) {
		this.importe = importe;
	}
	public Date getFecha_reserva() {
		return fecha_reserva;
	}
	public void setFecha_reserva(Date fecha_reserva) {
		this.fecha_reserva = fecha_reserva;
	}
	

	
	
	

}