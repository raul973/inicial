package es.uca.sub.Vistas;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

import es.uca.sub.Repositorios.Cargos.Cargo;
import es.uca.sub.Repositorios.Cargos.CargoService;
import es.uca.sub.Repositorios.Reserva.Reserva;
import es.uca.sub.Repositorios.Reserva.ReservasService;
import es.uca.sub.Repositorios.Usuario.Usuario;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Repositorios.Vehiculo.Vehiculo;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;
import es.uca.sub.Seguridad.SecurityUtils;

@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("misreservasGestor")
@Secured({"Gestor","Gerente"})
public class MisReservasGestor extends Composite<VerticalLayout> implements HasComponents, RouterLayout,BeforeEnterObserver {

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}
	}
	Notification error=new Notification("Por favor,introduzca un asunto");
	Vehiculo coche;
	transient UsuarioService usersService;
	transient ReservasService reservasService;
	transient VehiculosService carsService;
	transient CargoService cargoService;
	
	@Autowired
    public MisReservasGestor(UsuarioService userService,ReservasService reservasService,VehiculosService carsService,CargoService cargo) {
    	usersService=userService;
    	this.reservasService=reservasService;
    	this.carsService=carsService;
    	this.cargoService=cargo;
    	if(SecurityUtils.isUserLoggedIn()) {
    		String user=SecurityUtils.getUsername();
    		
    		
    		List<Reserva> ReservaList=reservasService.findAll();
    		Grid<Reserva> grid = new Grid<>();
    		grid.setItems(ReservaList);
    			
            	grid.addColumn(Reserva::getId).setHeader("ID");
            	grid.addColumn(Reserva::getId_usuario).setHeader("ID_usuario");
            	grid.addColumn(Reserva::getId_vehiculo).setHeader("ID_vehiculo");
            	grid.addColumn(Reserva::getReporte).setHeader("Reporte");
            	grid.addComponentColumn(item -> getFechaIni(item))
                .setHeader("Fecha de entrega");
        		grid.addComponentColumn(item -> getFechaFin(item))
                .setHeader("Fecha de devolución");
        		grid.addComponentColumn(item -> ButtonL(item))
                .setHeader("Editar");
        		grid.addComponentColumn(item -> ButtonFianza(item))
                .setHeader("Cobrar Fianza");
        		
        		if(SecurityUtils.hasRole("Gestor")) {
        			LayoutView layout=new LayoutView();
                	Header headerg = new Header();
                	Div body= new Div();
                	Footer footer = new Footer();
                	headerg= layout.HeaderGestorView(user);
                	footer = layout.FooterView();
                	body=layout.BodyView();
                	body.add(grid);
                	add(headerg);
                	add(body);
                	add(footer);
        			
        		}
        		if(SecurityUtils.hasRole("Gerente")) {
       			LayoutView layout=new LayoutView();
               	Header headerg = new Header();
               	Div body= new Div();
               	Footer footer = new Footer();
               	headerg= layout.HeaderGerenteView(user);
               	footer = layout.FooterView();
               	body=layout.BodyView();
               	body.add(grid);
               	add(headerg);
               	add(body);
               	add(footer);
        		}
    	}
    	
    }
	
	private Label getFechaIni(Reserva item) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		return new Label(dateFormat.format(item.getFecha_inicio()));
	    
	}
	
	private Label getFechaFin(Reserva item) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		return new Label(dateFormat.format(item.getFecha_fin()));
		 
	}
	
	
	private RouterLink ButtonL(Reserva item)  {

		 return new RouterLink("Editar",FormReserva.class,item.getId());

		  
	    
	}
	
     void ComprobarFianzayCobrar(String fianza,Reserva item, Vehiculo coche, Usuario user) {
    	
		 
    	 if(fianza.equals("Normal")) {

			Notification.show("Dispone de nuestro seguro Premium, por lo tanto no le cobraremos fianza");
    		
    		 }
    	 
    		 if(fianza.equals("Premium")) {

				 Notification.show("Dispone de nuestro seguro Premium, por lo tanto no le cobraremos fianza");
    		 }
    		 if(fianza.equals("Sin seguro")) {
    			 Cargo cargo=new Cargo("PAGAR",coche.getId(),user.getId(),item.getId(),100);
        		 cargoService.save(cargo);
        		 if(item.getReporte()!=null) {

        			 Notification.show("Se le ha cobrado una fianza por  no tener contratado seguro");
        			 item.setImporte(item.getImporte()+100);
        			 new Factura(100,user);
        			 

        			 }else {
        				 
        				 Cargo cargo2=new Cargo("DEVOLVER",coche.getId(),user.getId(),item.getId(),100);
                		 cargoService.save(cargo2);
                		 
        				 Notification.show("No se preocupe, como no se han detectado daños en el vehiculo se le ha reembolsado la fianza");
                			 
        			 }
    		
    }}
	private Button ButtonFianza(Reserva item)  {
		
		Optional<Vehiculo> car=this.carsService.getOne(item.getId_vehiculo());
		if(car.isPresent()) {
		coche=car.get();
		}
		Usuario user=this.usersService.findByUsername(SecurityUtils.getUsername());
		
		Button button = new Button("Cobrar fianza");

		 button.addClickListener(clickEvent ->
		 
		 ComprobarFianzayCobrar(item.getSeguro(),item,coche,user));

		
		 
	    return button;
	}

	
	
}
