package es.uca.sub.Vistas;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import es.uca.sub.Repositorios.Usuario.UsuarioService;



@Route("AutohiredActivate")
public class ActivateAcount extends Composite<VerticalLayout> implements RouterLayout,HasComponents,BeforeEnterObserver, HasUrlParameter<Integer>{
	
	
	private int id;
	transient UsuarioService service;
	
	
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		/**
		 * Vacio porque no se usa
		 */
	}
	@Override
	public void setParameter(BeforeEvent event, Integer parameter) {
		id=parameter;
		if(service.getOne(id).isPresent()) {
		service.activate(service.getOne(id).get());
		}
		event.rerouteTo(MainView.class);
		UI.getCurrent().navigate(MainView.class);
		
	}
	
	@Autowired
	public ActivateAcount(UsuarioService service){
		this.service=service;
		
	}
	
	

	
	

}
