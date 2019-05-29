package es.uca.sub.Repositorios.Usuario;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class UserForm extends FormLayout {
	
	private TextField nombre = new TextField("Nombre");

	private TextField apellidos = new TextField("Apellidos");
	private TextField contrasena = new TextField("Contraseña");
	private TextField username = new TextField("Usuario");
	private TextField email = new TextField("email");
	Select<String> rol = new Select<>("Gerente", "Cliente","Gestor");
	
	private Button registerButton = new Button("¡Regístrate!");

	private Binder<Usuario> binder = new Binder<>(Usuario.class);
	
	
	
	transient UsuarioService service;
	public UserForm( UsuarioService ser,boolean b) {
		
		this.service=ser;
		rol.setPlaceholder("rol");
		registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		add(nombre,apellidos,username,contrasena,email,registerButton);
		rol.setValue("Cliente");
		
		binder.bindInstanceFields(this);
		
		registerButton.addClickListener(event -> register());
		
		
	}
	
	
	public UserForm( UsuarioService ser) {
	
		this.service=ser;
		rol.setPlaceholder("rol");
		registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		add(nombre,apellidos,username,contrasena,email,rol,registerButton);
		
		binder.bindInstanceFields(this);
		
		registerButton.addClickListener(event -> register());
		
		
	}
	
	public void register() {
		Usuario customer = new Usuario();
		try {
			binder.writeBean(customer);
		} catch (ValidationException e) {

			e.printStackTrace();
		}
		service.save(customer);
		
	
	
		
	}
	

}