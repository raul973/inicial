package es.uca.sub.Vistas;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import es.uca.sub.Repositorios.Auditoria.Auditoria;
import es.uca.sub.Repositorios.Auditoria.AuditoriaService;
import es.uca.sub.Repositorios.Reserva.ReservasService;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;


@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("AuditoriaMobile")
public class AuditoriaMobileView extends Composite<VerticalLayout> implements RouterLayout,HasComponents,BeforeEnterObserver, HasUrlParameter<String>{
	
	private int id;
	transient UsuarioService userService;
	transient VehiculosService carService;
	transient ReservasService reservaService;
	transient AuditoriaService service;
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {

		
	}

	@Override
	public void setParameter(BeforeEvent event, String parameter) {

		id=Integer.valueOf(parameter);
		List<Auditoria> AuditoriaList=new ArrayList<> ();
		if(service.getOne(id).isPresent()) {
		AuditoriaList.add(service.getOne(id).get());
		}
		Grid<Auditoria> grid = new Grid<>();
		grid.setItems(AuditoriaList);
			LayoutView layout=new LayoutView();
        	Header headerg;
        	Div body;
        	Footer footer;
        	headerg= layout.HeaderGestorView("Gerente Mobile");
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

	@Autowired
	public AuditoriaMobileView(UsuarioService userService, VehiculosService carService,
			ReservasService reservaService, AuditoriaService service) {
		
		this.userService = userService;
		this.carService = carService;
		this.reservaService = reservaService;
		this.service = service;
		
		
	}
	
	private Label getUser( Auditoria item) {
		if(userService.getOne(item.getId_usuario()).isPresent()) {
			return new Label(userService.getOne(item.getId_usuario()).get().getUsername());
		}else{
			return new Label("");
		}
		
		    
	}
	
	
	

}
