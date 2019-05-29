package es.uca.sub.Vistas;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.client.RestTemplate;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import elemental.json.Json;
import es.uca.sub.Repositorios.Auditoria.Auditoria;
import es.uca.sub.Repositorios.Auditoria.AuditoriaService;
import es.uca.sub.Repositorios.Usuario.Usuario;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Repositorios.Vehiculo.Vehiculo;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;
import es.uca.sub.Seguridad.SecurityUtils;
@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("unaGestion")
@Secured({"Gestor","Gerente"})
public class GestionUnView extends Composite<VerticalLayout> implements HasComponents, HasUrlParameter<Integer>, BeforeEnterObserver {
	private VehiculosService carService;
	private AuditoriaService updateService;
	private UsuarioService userService;
	Vehiculo cocheUpdate;
	Auditoria update=new Auditoria();
	private int id;
	Div body;
	Div izq;
	Div der;
	String Periodo;
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}
	}
	@Override
    public void setParameter(BeforeEvent event, Integer parameters) {
			
			id=parameters;
			
			Optional<Vehiculo> car =carService.getOne(id);
			Date today = new Date(new java.util.Date().getTime());
			Vehiculo coche=car.get();
			Integer precioOld=coche.getPrecio();
			Usuario user=userService.findByUsername(SecurityUtils.getUsername());
			
			DatePicker nueva=new DatePicker("Fecha de inicio nueva");
			DatePicker fin=new DatePicker("Fecha final nueva");
			NativeButton button = new NativeButton("Añade el seleccionado");
			button.addClickListener( e-> {   
				update=new Auditoria();
				ZoneId defaultZoneId = ZoneId.systemDefault();
				Date date_f=new Date();
				Date date_i=new Date();
				if(nueva.getValue()==null || fin.getValue()==null) {
					Dialog dialog = new Dialog();
					dialog.add(new Label("Alguna de las fechas estan vacias, introducelas." ));

					dialog.setWidth("400px");
					dialog.setHeight("150px");
					dialog.open();
					
				}else {
				date_f = Date.from(fin.getValue().atStartOfDay(defaultZoneId).toInstant());
				date_i = Date.from(nueva.getValue().atStartOfDay(defaultZoneId).toInstant());
				}
				Date fecha = new Date();
				
				if(date_f.compareTo(date_i)<0||date_i.compareTo(fecha)<0) {
					Dialog dialog = new Dialog();
					dialog.add(new Label("Has introducido mal la fecha, haz click fuera para salir" ));

					dialog.setWidth("400px");
					dialog.setHeight("150px");
					dialog.open();
				}else {
		     
			
			String periodo=coche.getPeriodo()+nueva.getValue()+" "+fin.getValue()+";";
			coche.setPeriodo(periodo);
			carService.save(coche);
			
			update.setId_vehiculo(id);
			update.setId_usuario(user.getId());
			update.setCampo_modificado("Periodo");
			update.setValor_antiguo(" ");
			update.setFecha(today.toString());
			update.setValor_nuevo(periodo);
			Auditoria aux=updateService.save(update);
			RestTemplate restTemplate=new RestTemplate();
			restTemplate.getForObject("http://ec2-3-86-160-166.compute-1.amazonaws.com:8090/autohired/"+aux.getId_update(), String.class);
			
			button.getUI().ifPresent(ui -> ui.navigate("Gestion"));
				}
				
			});
			izq.add(button);
			izq.add(nueva);
			izq.add(fin);
			String periodo=coche.getPeriodo();
			String [] spliteo=periodo.split(";");
			Select<String>selector = new Select<String>();
			List<String> options = new ArrayList<>();
	
			for( String rec : spliteo) {
				options.add(rec);
		
			}
		    selector.setItems(options);
			
			NativeButton button3 = new NativeButton("Borra el seleccionado");
			button3.addClickListener( e-> {    
				update=new Auditoria();
				String aux=coche.getPeriodo();
				String [] split=aux.split(";");
				ArrayList<String> array = new ArrayList<String>();
				if(selector.getValue()==null) {
					Dialog dialog = new Dialog();
					dialog.add(new Label("Introduzca el periodo a borrar, por favor" ));

					dialog.setWidth("400px");
					dialog.setHeight("150px");
					dialog.open();
				}else {
			
				for (String sp : split) {
					if(!sp.contentEquals(selector.getValue())) {
					array.add(sp);
				}
				}
				String periodofinal=new String();
				for( String ps : array) {
					periodofinal=periodofinal+ps+";";
				}
				
				
				coche.setPeriodo(periodofinal);
				carService.save(coche);
				update.setId_vehiculo(id);
				update.setId_usuario(user.getId());
				update.setCampo_modificado("Periodo");
				update.setValor_antiguo(selector.getValue());
				update.setValor_nuevo("");
				update.setFecha(today.toString());
				Auditoria aux2=updateService.save(update);
				RestTemplate restTemplate=new RestTemplate();
				restTemplate.getForObject("http://ec2-3-86-160-166.compute-1.amazonaws.com:8090/autohired/"+aux2.getId_update(), String.class);
				button3.getUI().ifPresent(ui -> ui.navigate("Gestion")); 
				}
				});
			TextField precio=new TextField("Introduce el precio");
			
			NativeButton button4 = new NativeButton("Añade el seleccionado");
			button4.addClickListener( e-> {    
				if( precio.getValue().equals("")) {
					Dialog dialog = new Dialog();
					dialog.add(new Label("Introduzca el precio, por favor" ));

					dialog.setWidth("400px");
					dialog.setHeight("150px");
					dialog.open();
				}else {
				
			coche.setPrecio(Integer.valueOf(precio.getValue()));
			
			carService.save(coche);
			
			update.setId_vehiculo(id);
			update.setId_usuario(user.getId());
			update.setCampo_modificado("Precio");
			update.setFecha(today.toString());
			update.setValor_antiguo(precioOld.toString());
			update.setValor_nuevo(coche.getPrecio().toString());
			Auditoria aux3=updateService.save(update);
			RestTemplate restTemplate=new RestTemplate();
			restTemplate.getForObject("http://localhost:8090/autohired/"+aux3.getId_update(), String.class);
			button.getUI().ifPresent(ui -> ui.navigate("Gestion"));
			
				}
			});
			
			der.add(precio);
			der.add(button4);
		    der.add(button3);
			der.add(selector);

		}

	@Autowired
	public GestionUnView(VehiculosService service,AuditoriaService updateService,UsuarioService userService)
	{
		this.carService=service;
		this.updateService=updateService;
		this.userService=userService;
		if(SecurityUtils.isUserLoggedIn()) {
    		String user=SecurityUtils.getUsername();
 
    		if(SecurityUtils.hasRole("Gestor")) {
    			LayoutView layout=new LayoutView();
            	Header headerg = new Header();
            	
            	Footer footer = new Footer();
            	headerg= layout.HeaderGestorView(user);
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
           	headerg= layout.HeaderGerenteView(user);
           	footer = layout.FooterView();
           	body=layout.BodyView();
           	add(headerg);
           	add(body);
           	add(footer);
   			
   		}
    		
    		
		}
	    izq= new Div();
	    der= new Div();
		izq.setClassName("izq");
		der.setClassName("der");
		body.add(izq);
		body.add(der);
		
		
		
	}

	 

}