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

import es.uca.sub.Repositorios.Vehiculo.CarForm;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;
import es.uca.sub.Seguridad.SecurityUtils;
@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("vehiculo")
@Secured({"Gestor","Gerente"})
public class RegistrarVehiculoView extends Composite<VerticalLayout> implements HasComponents, RouterLayout, BeforeEnterObserver {
	
	transient VehiculosService servicio;
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}
	}
	
	@Autowired
	public RegistrarVehiculoView(VehiculosService service)
	{
		this.servicio=service;
		CarForm carform=new CarForm(this,servicio);
		if(SecurityUtils.hasRole("Gestor")) {
			LayoutView layout=new LayoutView();
        	Header headerg = new Header();
        	Div body= new Div();
        	Footer footer = new Footer();
        	headerg= layout.HeaderGestorView(SecurityUtils.getUsername());
        	footer = layout.FooterView();
        	body=layout.BodyView();
        	body.add(carform);
        	add(headerg);
        	add(body);
        	add(footer);
			
		}
		if(SecurityUtils.hasRole("Gerente")) {
			LayoutView layout=new LayoutView();
       	Header headerg = new Header();
       	Div body= new Div();
       	Footer footer = new Footer();
       	headerg= layout.HeaderGerenteView(SecurityUtils.getUsername());
       	footer = layout.FooterView();
       	body=layout.BodyView();
       	body.add(carform);
       	add(headerg);
       	add(body);
       	add(footer);
			
		}
		
	}

	 

}
