package es.uca.sub.Repositorios.Auditoria;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="Auditoria")
public class Auditoria implements Serializable{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id_update;
	
	private Integer id_vehiculo;
	private Integer id_reserva;
	private Integer id_usuario;
	
	private String campo_modificado;
	private String valor_antiguo;
	private String valor_nuevo;
	private String fecha;
	
	public Auditoria() {}
	
	
	public Auditoria(Integer id_update, Integer id_vehiculo, Integer id_reserva, Integer id_user,
			String campoModificado, String valorAntiguo, String valorNuevo,String fecha) {
		this.id_update = id_update;
		this.id_vehiculo = id_vehiculo;
		this.id_reserva = id_reserva;
		this.id_usuario = id_user;
		this.campo_modificado=campoModificado;
		this.valor_antiguo = valorAntiguo;
		this.valor_nuevo = valorNuevo;
		this.fecha=fecha;
	}



	public Integer getId_update() {
		return id_update;
	}
	public void setId_update(Integer id_update) {
		this.id_update = id_update;
	}
	
	public Integer getId_vehiculo() {
		return id_vehiculo;
	}


	public void setId_vehiculo(Integer id_vehiculo) {
		this.id_vehiculo = id_vehiculo;
	}


	public Integer getId_reserva() {
		return id_reserva;
	}
	public void setId_reserva(Integer id_reserva) {
		this.id_reserva = id_reserva;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}


	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}





	public String getCampo_modificado() {
		return campo_modificado;
	}


	public void setCampo_modificado(String campo_modificado) {
		this.campo_modificado = campo_modificado;
	}


	public String getValor_antiguo() {
		return valor_antiguo;
	}


	public void setValor_antiguo(String valor_antiguo) {
		this.valor_antiguo = valor_antiguo;
	}


	public String getValor_nuevo() {
		return valor_nuevo;
	}


	public void setValor_nuevo(String valor_nuevo) {
		this.valor_nuevo = valor_nuevo;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
	

}
