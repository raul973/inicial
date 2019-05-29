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
import es.uca.sub.Seguridad.SecurityUtils;
@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("signup")
@Secured({"Gerente"})
public class SignUpView extends Composite<VerticalLayout> implements HasComponents, RouterLayout,BeforeEnterObserver {
	
	transient UsuarioService service;
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}
	}
	
	
	@Autowired
	public SignUpView(UsuarioService service)
	{
		this.service=service;
		UserForm carform=new UserForm(service);
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