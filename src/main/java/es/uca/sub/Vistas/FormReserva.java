package es.uca.sub.Vistas;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.client.RestTemplate;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
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
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import elemental.json.Json;
import es.uca.sub.Repositorios.Auditoria.Auditoria;
import es.uca.sub.Repositorios.Auditoria.AuditoriaService;
import es.uca.sub.Repositorios.Reserva.Reserva;
import es.uca.sub.Repositorios.Reserva.ReservasService;
import es.uca.sub.Repositorios.Usuario.Usuario;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;
import es.uca.sub.Seguridad.SecurityUtils;
@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("editarReserva")
@Secured({"Gestor","Gerente"})
public class FormReserva extends Composite<VerticalLayout> implements HasComponents, HasUrlParameter<Integer>, BeforeEnterObserver  {
	
	private ReservasService servicio;
	private VehiculosService carService;
	private AuditoriaService updateService;
	private UsuarioService userService;
	Notification error;
	private int id;
	private Div body;
	private Div izq;
	private Div der;
	private DatePicker fecha_inicio=new DatePicker();
	private DatePicker fecha_fin=new DatePicker();
	private NativeButton ex;
	private Date aux1,aux2;
	String fechaIni,fechaFin;
	Auditoria update=new Auditoria();
	ArrayList<Auditoria> Updates=new ArrayList<Auditoria>();
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}else {
			
		}
	}
	
	@Override
    public void setParameter(BeforeEvent event, Integer parameters) {
		id=parameters;
		Optional<Reserva> res =servicio.getOne(id);
		Usuario user=userService.findByUsername(SecurityUtils.getUsername());
		Date today = new Date(new java.util.Date().getTime());
		Reserva rese=res.get();
		DatePicker nueva=new DatePicker("Fecha de inicio nueva");
		DatePicker fin=new DatePicker("Fecha final nueva");
		NativeButton button = new NativeButton("Añade el seleccionado");
		
		TextField Contenido=new TextField("Reporte...");
	    aux1=rese.getFecha_fin();
		aux2=rese.getFecha_inicio();
		fechaIni=rese.getFecha_inicio().toString();
		fechaFin=rese.getFecha_fin().toString();
		String reporte= rese.getReporte();
		
		
		button.addClickListener( e-> {    
			if(fin.getValue()==null && nueva.getValue()==null && Contenido.getValue().equals("")) {
				Dialog dialog = new Dialog();
				dialog.add(new Label("Introduzca uno de los campos.Haz click fuera para salir." ));

				dialog.setWidth("400px");
				dialog.setHeight("150px");
				dialog.open();
			}else {
		
		
			ZoneId defaultZoneId = ZoneId.systemDefault();
			String extra=new String();
				
			if(fin.getValue()!=null) {
				aux1 = Date.from(fin.getValue().atStartOfDay(defaultZoneId).toInstant());
				update=new Auditoria();
				update.setId_reserva(id);
				update.setId_usuario(user.getId());
				update.setCampo_modificado("Fecha_fin");
				update.setValor_antiguo(fechaFin);
				update.setFecha(today.toString());
				
				update.setValor_nuevo(new java.sql.Date(aux1.getTime()).toString());
				Updates.add(update);
			}
			if(nueva.getValue()!=null) {
				aux2 = Date.from(nueva.getValue().atStartOfDay(defaultZoneId).toInstant());
				update=new Auditoria();
				update.setId_reserva(id);
				update.setId_usuario(user.getId());
				update.setCampo_modificado("Fecha_inicio");
				update.setValor_antiguo(fechaIni);
				update.setFecha(today.toString());
				
				update.setValor_nuevo(new java.sql.Date(aux2.getTime()).toString());
				Updates.add(update);
			}
			if(!Contenido.getValue().equals("")) {
				 extra=reporte;
				 update=new Auditoria();
					update.setId_reserva(id);
					update.setId_usuario(user.getId());
					update.setValor_antiguo(reporte);
					update.setFecha(today.toString());
					update.setValor_nuevo(reporte+". "+Contenido.getValue());
					Updates.add(update);
			}
			Date fecha = new Date();
		
			
				   Calendar c = Calendar.getInstance();
			        c.setTime(aux2);
			        c.add(Calendar.DATE, 1);
			        Date ndate_i=new Date(c.getTimeInMillis());
			        Calendar c2 = Calendar.getInstance();
			        c2.setTime(aux1);
			        c2.add(Calendar.DATE, 1);
			        Date ndate_f=new Date(c2.getTimeInMillis());
			    
				rese.setFecha_inicio(new java.sql.Date(ndate_i.getTime()));
				rese.setFecha_fin(new java.sql.Date(ndate_f.getTime()));
				if(extra!=null) {
				rese.setReporte(extra+". "+Contenido.getValue());
				}else{
					rese.setReporte(Contenido.getValue());	
				}
				int dias=(int) ((ndate_f.getTime()-ndate_i.getTime())/86400000);
				
				Integer subtotal=dias*this.carService.getOne(rese.getId_vehiculo()).get().getPrecio();
				rese.setImporte(subtotal);
				Auditoria aux=new Auditoria();
				servicio.save(rese);
				for(Auditoria update:Updates) {
					aux=updateService.save(update);
					System.out.println(aux.getId_update());
					RestTemplate restTemplate=new RestTemplate();
					restTemplate.getForObject("http://ec2-3-86-160-166.compute-1.amazonaws.com:8090/autohired/"+aux.getId_update(), String.class);
				}
			}
			
		});
	
		Contenido.setPlaceholder("Escribe el reporte");
		body.add(nueva);
		body.add(fin);
		body.add(Contenido);
		body.add(button);
		
		
	}
	
	
	@Autowired
	public FormReserva(ReservasService aux,VehiculosService a,AuditoriaService updateService,UsuarioService userService) {
		this.servicio=aux;
		this.carService=a;
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
    	
	    
	
		
	}
	
}
