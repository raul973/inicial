package es.uca.sub.Vistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import es.uca.sub.Repositorios.Usuario.UserForm;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("signupAnonymous")
public class SignUpAnonymous extends Composite<VerticalLayout> implements HasComponents, RouterLayout{
	
	transient UsuarioService service;
	
	
	@Autowired
	public SignUpAnonymous(UsuarioService service)
	{
		this.service=service;
		UserForm carform=new UserForm(service,true);
    	LayoutView layout=new LayoutView();
    	Header header = new Header();
    	Div body= new Div();
    	Footer footer = new Footer();
    	header= layout.HeaderView();
    	footer = layout.FooterView();
    	body=layout.BodyView();
    	body.add(carform);
    	add(header);
    	add(body);
		add(footer);
	}

	 

}