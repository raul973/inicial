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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

import es.uca.sub.Repositorios.Cargos.Cargo;
import es.uca.sub.Repositorios.Cargos.CargoService;
import es.uca.sub.Seguridad.SecurityUtils;

@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("pagosView")
@Secured({"Gestor"})
public class pagosView extends Composite<VerticalLayout> implements HasComponents, RouterLayout,BeforeEnterObserver {

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}else {
			
		}
	}

	CargoService cargoService;
	@Autowired
    public pagosView(CargoService cargo) {
    	
    	this.cargoService=cargo;
    	if(SecurityUtils.isUserLoggedIn()) {
    		String user=SecurityUtils.getUsername();
    	
    		List<Cargo> cargoList=cargoService.findAll();
    		Grid<Cargo> grid = new Grid<>();
    		grid.setItems(cargoList);
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
               	headerg= layout.HeaderGestorView(user);
               	footer = layout.FooterView();
               	body=layout.BodyView();
               	body.add(grid);
               	add(headerg);
               	add(body);
               	add(footer);
       			
       		}
            	grid.addColumn(Cargo::getId).setHeader("ID");
            	grid.addColumn(Cargo::getTipo).setHeader("Tipo");
            	grid.addColumn(Cargo::getVehiculo).setHeader("ID_vehiculo");
            	grid.addColumn(Cargo::getReserva).setHeader("Reserva");
            	grid.addColumn(Cargo::getImporte).setHeader("Importe");
            	grid.addColumn(Cargo::getUsuario).setHeader("Usuario");
    	}
    	
    }
	



	 

	
}