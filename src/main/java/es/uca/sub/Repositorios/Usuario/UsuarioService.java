package es.uca.sub.Repositorios.Usuario;



import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {
	
	private PasswordEncoder passwordEncoder;
	private JavaMailSender emailSender;
	private UsuarioRepository usersRepository;
	

	@Autowired
	public UsuarioService(UsuarioRepository ur, PasswordEncoder passwordEncoder,JavaMailSender emailSender) {
		this.usersRepository=ur;
		this.passwordEncoder=passwordEncoder;
		this.emailSender=emailSender;
	}
	
	public Usuario save (Usuario entrada) {
		entrada.setContrasena(passwordEncoder.encode(entrada.getContrasena()));
		Usuario user= usersRepository.save(entrada);
		SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Active usted la cuenta porfavor");
        message.setText("Por favor haga click en este link para activar la cuenta:"+"http://ec2-3-86-160-166.compute-1.amazonaws.com:8080/AutohiredActivate/"+user.getId());

        message.setTo(user.getEmail());
       

        emailSender.send(message);
		return user;
	}
	
	public Optional<Usuario> getOne(Integer id){
		return usersRepository.findById(id);
	}
	
	
	public Usuario  update(Usuario entrada) {
		return usersRepository.save(entrada);
	}
	
	public Usuario  activate(Usuario us) {
		us.setEnabled();
		this.update(us);
		return us;
	}
	
	public Usuario findByNombre( String nombre) {
		return usersRepository.findByNombre(nombre);
	}
	public Usuario findByContrasena( String contrasena) {
		return usersRepository.findByContrasena(contrasena);
	}
	public List<Usuario> findByRol( String rol) {
		return usersRepository.findByRol(rol);
	}
	public Usuario findByUsername( String username) {
		return usersRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username)  {
		Usuario user=usersRepository.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
}