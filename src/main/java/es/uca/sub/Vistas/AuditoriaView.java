package es.uca.sub.Vistas;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import es.uca.sub.Repositorios.Auditoria.Auditoria;
import es.uca.sub.Repositorios.Auditoria.AuditoriaService;
import es.uca.sub.Repositorios.Reserva.ReservasService;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;
import es.uca.sub.Seguridad.SecurityUtils;

@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("Auditoria")
@Secured({"Gerente"})
public class AuditoriaView extends Composite<VerticalLayout> implements HasComponents, RouterLayout,BeforeEnterObserver {

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}
	}
	Notification error=new Notification("Por favor,introduzca un asunto");
	
	transient UsuarioService usersService;
	transient ReservasService reservasService;
	transient VehiculosService carsService;
	transient AuditoriaService updateService;
	
	@Autowired
    public AuditoriaView(UsuarioService userService,ReservasService reservasService,VehiculosService carsService,AuditoriaService updateService) {
    	usersService=userService;
    	this.reservasService=reservasService;
    	this.carsService=carsService;
    	this.updateService=updateService;

    	if(SecurityUtils.isUserLoggedIn()) {
    		String user=SecurityUtils.getUsername();
    		
    		
    		List<Auditoria> AuditoriaList=updateService.findAll();
    		Grid<Auditoria> grid = new Grid<>();
    		grid.setItems(AuditoriaList);
    			LayoutView layout=new LayoutView();
            	Header headerg = new Header();
            	Div body= new Div();
            	Footer footer = new Footer();
            	headerg= layout.HeaderGerenteView(user);
            	footer = layout.FooterView();
            	body=layout.BodyView();
            	grid.addColumn(Auditoria::getId_update).setHeader("ID");
            	grid.addColumn(Auditoria::getId_usuario).setHeader("ID_usuario");
            	grid.addComponentColumn(item -> getUser( item))
                .setHeader("Usuario");
            	grid.addColumn(Auditoria::getId_vehiculo).setHeader("ID_vehiculo");
            	grid.addColumn(Auditoria::getId_reserva).setHeader("ID_reserva");
            	grid.addColumn(Auditoria::getCampo_modificado).setHeader("Campo Modificado");
            	grid.addColumn(Auditoria::getValor_antiguo).setHeader("Valor Antiguo");
            	grid.addColumn(Auditoria::getValor_nuevo).setHeader("Valor Actualizado");
            	grid.addColumn(Auditoria::getFecha).setHeader("Fecha de cambio");
            	
            	
            	
            	body.add(grid);
            	add(headerg);
            	add(body);
            	add(footer);
    	}
    	
    }
	private Label getUser( Auditoria item) {
		
		if(usersService.getOne(item.getId_usuario()).isPresent()) {
			return new Label(usersService.getOne(item.getId_usuario()).get().getUsername());
		}else {
			return new Label("");
		}
		
		    
	}
	
	

	
}
