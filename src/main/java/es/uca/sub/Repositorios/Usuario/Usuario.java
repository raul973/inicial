package es.uca.sub.Repositorios.Usuario;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Entity(name="Usuario")

public class Usuario implements UserDetails{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Integer id_usuario;
	
	private String nombre;
	private String apellidos;
	private String username;
	private String contrasena;
	private String rol;
	private String email;
	
	private boolean activate;
	
	public  Usuario() {}
	/**
	 * Constructor Usuario
	 * @param nombre
	 * @param apellidos
	 * @param username
	 * @param contrasena
	 * @param email
	 * @param rol
	 */
	public Usuario(String nombre,String apellidos,String username,String contrasenna,String rol,String em)
	{
		this.nombre=nombre;
		this.apellidos=apellidos;
		this.username=username;
		this.contrasena=contrasenna;
		this.rol=rol;
		this.email=em;
	}
	
	/**
	 * getters and setters
	 */
	public Integer getId() {
		return id_usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String em) {
		this.email = em;
	}
	
	@Override
	 public Collection<? extends GrantedAuthority> getAuthorities(){
		List<GrantedAuthority> list=new ArrayList<>();

		list.add(new SimpleGrantedAuthority(rol));
		return list;
	}
	

	@Override
	public String getPassword() {
		return this.contrasena;
	}
	@Override
	public String getUsername() {
		return this.username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return this.activate;
	}
	
	public void setEnabled() {
		 this.activate=true;
	}
}