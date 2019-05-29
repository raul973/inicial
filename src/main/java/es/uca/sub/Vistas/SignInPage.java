package es.uca.sub.Vistas;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import es.uca.sub.Repositorios.Usuario.UsuarioService;

@Push
@Route("login")
public class SignInPage extends Composite<VerticalLayout> implements HasComponents, RouterLayout,BeforeEnterObserver{
	UI ui;
	transient UsuarioService service;
	transient AuthenticationManager authenticationManager;
	LoginOverlay loginForm;
	Button boton=new Button("¡Registrate!");
	BeforeEnterEvent eventoBefore;
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {

		boton=new Button("¡Registrate!",click->{
			event.rerouteTo(SignUpView.class);
			UI.getCurrent().navigate(SignUpView.class);
    		
		});
	}
	@Autowired
	public SignInPage(UsuarioService service, AuthenticationManager authenticationManager) {
		this.service=service;
		this.authenticationManager=authenticationManager;
		loginForm= new LoginOverlay();
		
		H1 title = new H1();
    	title.getStyle().set("color", "var(--lumo-base-color)");
    	Icon icon = VaadinIcon.VAADIN_H.create();
    	
    	ui=UI.getCurrent();
    	
    	icon.setSize("30px");
    	icon.getStyle().set("top", "-4px");
    	
    	title.add(new Text("AutoHired"));
    	VerticalLayout vr=new VerticalLayout();
    	
    	boton.setEnabled(true);
    	boton.setVisible(true);
    	boton.getStyle().set("background-color", "white");
    	
    	vr.add(new Text("¿Eres nuevo?"));
    	vr.add(boton);
    	vr.add(title);
    	
    	loginForm.setTitle(vr);
		loginForm.setDescription("Inicia sesión en Autohired");
		loginForm.setForgotPasswordButtonVisible(false);
		loginForm.addLoginListener(e->signIn(e));
		loginForm.setOpened(true);
		
		
		add(loginForm);
		
	}
	
	private void signIn(LoginEvent e) {
		if(authenticate(e.getUsername(),e.getPassword())) {
			loginForm.close();
			UI.getCurrent().navigate(MainView.class);
			
		}else {
			loginForm.setError(true);
		}
	}
	public boolean authenticate(String username, String password) {
		try {
			Authentication token= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
			SecurityContextHolder.getContext().setAuthentication(token);
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	
    	
    	
		
}

	
	
