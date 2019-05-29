package es.uca.sub.Vistas;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

import es.uca.sub.Repositorios.Reserva.Reserva;
import es.uca.sub.Repositorios.Reserva.ReservasService;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Repositorios.Vehiculo.Vehiculo;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;
import es.uca.sub.Seguridad.SecurityUtils;

@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("Gestion")
@Secured({"Gestor","Gerente"})
public class GestionView extends Composite<VerticalLayout> implements HasComponents, RouterLayout, BeforeEnterObserver {
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}
	}
	transient VehiculosService carService;
	Div body;
	transient UsuarioService usersService;
	transient ReservasService reservaService;
	private Binder<Reserva> binder = new Binder<>(Reserva.class);
	
	@Autowired
	public GestionView(VehiculosService service,UsuarioService userService,ReservasService reservaService)
	{
		usersService=userService;
		this.carService=service;
		this.reservaService=reservaService;
		if(SecurityUtils.hasRole("Gestor")) {
			LayoutView layout=new LayoutView();
        	Header headerg = new Header();
        
        	Footer footer = new Footer();
        	headerg= layout.HeaderGestorView(SecurityUtils.getUsername());
        	footer = layout.FooterView();
        	body=layout.BodyView();
        	
        	add(headerg);
        	add(body);
        	add(footer);
			
		}
		if(SecurityUtils.hasRole("Gerente")) {
			LayoutView layout=new LayoutView();
       	Header headerg = new Header();
       	
       	Footer footer = new Footer();
       	headerg= layout.HeaderGerenteView(SecurityUtils.getUsername());
       	footer = layout.FooterView();
       	body=layout.BodyView();
    
       	add(headerg);
       	add(body);
       	add(footer);
			
		}
    	
    	//Caja de búsqueda
    	TextField bus=new TextField("Introduzca el concepto que quiera buscar");
    	bus.setClassName("textb");
    	bus.setPlaceholder("Introduzca el concepto");
   
    	//Select de opciones
    	Select<String> select = new Select<>("Modelo", "Matricula","Estado","Características");
    	select.setPlaceholder("Categoría");
    	select.setClassName("select");
    
    	Div divArticles=new Div();
    	divArticles.setClassName("divArticles");
    
	    	
	    	
	    	List<Vehiculo> listCar = null;
	    	
	    	listCar=service.findAll();
	    	
	    	if(listCar!=null) {
	    		for (Vehiculo coche : listCar) {
    				Div articulo=new Div();
    				articulo.setClassName("articulo");
    				Label matricula=new Label ("La matricula es: "+coche.getMatricula());
    				matricula.setClassName("et");
    				Label marca=new Label ("La marca es: "+coche.getMarca());
    				marca.setClassName("et");
    				Label caracteristicas=new Label ("La disponibilidad es: "+coche.getCaracteristicas());
    				caracteristicas.setClassName("et");
    				Label estado=new Label ("El estado  es: "+coche.getEstado());
    				estado.setClassName("et");
    				Label precio=new Label ("El precio  es: "+coche.getPrecio());
    				precio.setClassName("et");
    				Html a=new Html("<img class='im'src="+coche.getImagen()+">");
    				articulo.add(a);
    				articulo.add(matricula);
    				articulo.add(marca);
    				articulo.add(estado);
    				articulo.add(precio);
    				int parametros=coche.getId();
    				RouterLink  botonEnlace= new RouterLink("Gestion",GestionUnView.class,parametros);
    				botonEnlace.setClassName("boton2");
    				articulo.add(botonEnlace);
    				divArticles.add(articulo);
    			
    				}
	    	}
    	 body.add(divArticles);
    	
    	
	}

	 

}
