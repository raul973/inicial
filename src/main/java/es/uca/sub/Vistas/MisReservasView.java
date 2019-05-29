package es.uca.sub.Vistas;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

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
@Route("misreservas")
@Secured({"Cliente","Gestor","Gerente"})
public class MisReservasView extends Composite<VerticalLayout> implements HasComponents, RouterLayout,BeforeEnterObserver {

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}
	}
	Notification error=new Notification("Por favor,introduzca un asunto");
	Vehiculo coche;
	Usuario usuario;
	transient UsuarioService usersService;
	transient ReservasService reservasService;
	transient VehiculosService carsService;
	transient CargoService cargoService;
	
	public MisReservasView() {
		
	}
	@Autowired
    public MisReservasView(UsuarioService userService,ReservasService reservasService,VehiculosService carsService,CargoService cargos) {
    	usersService=userService;
    	this.reservasService=reservasService;
    	this.carsService=carsService;
    	this.cargoService=cargos;
    	if(SecurityUtils.isUserLoggedIn()) {
    		String user=SecurityUtils.getUsername();
    		Usuario usuario=usersService.findByUsername(user);
    		Integer id_usuario=usuario.getId();
    		List<Reserva> ReservaList=reservasService.findById_usuario(id_usuario);
    		Grid<Reserva> grid = new Grid<>();
    		grid.setItems(ReservaList);
    		grid.addColumn(Reserva::getId).setHeader("Id Reserva");
    		grid.addComponentColumn(item -> getMarcaCoche(item))
            .setHeader("Marca");
    		grid.addComponentColumn(item -> getModeloCoche(item))
            .setHeader("Modelo");
    		grid.addComponentColumn(item -> getPrecioCoche(item))
            .setHeader("Precio");
    		grid.addColumn(Reserva::getSeguro).setHeader("Seguro");
    		grid.addComponentColumn(item -> getFechaIni(item))
            .setHeader("Fecha de entrega");
    		grid.addComponentColumn(item -> getFechaFin(item))
            .setHeader("Fecha de devolución");
    		grid.addComponentColumn(item -> reportarProblema(item))
            .setHeader("Reportar Problema");
    		grid.addComponentColumn(item -> cancelarReserva(grid, item))
            .setHeader("Cancelación");
    		grid.addComponentColumn(item -> descargarFactura(item))
            .setHeader("Factura");
    		
    		
    		
    		
    		
    		if(SecurityUtils.hasRole("Cliente")) {
    			LayoutView layout=new LayoutView();
            	Header header = new Header();
            	Div body= new Div();
            	Footer footer = new Footer();
            	header= layout.HeaderClienteView(user);
            	footer = layout.FooterView();
            	body=layout.BodyView();
            	body.add(grid);
            	add(header);
            	add(body);
            	add(footer);
    			
    		}
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
    		
    		
    		
    	}else {
    		LayoutView layout=new LayoutView();
        	Header header = new Header();
        	Div body= new Div();
        	Footer footer = new Footer();
        	header= layout.HeaderView();
        	footer = layout.FooterView();
        	body=layout.BodyView();
        	add(header);
        	add(body);
        	add(footer);
    		
    	}
    
    	
    }
	
	private Label getFechaIni(Reserva item) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
	   
	    return  new Label(dateFormat.format(item.getFecha_inicio()));
	}
	
	private Label getFechaFin( Reserva item) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		   
		    return new Label(dateFormat.format(item.getFecha_fin()));
	}
	
	private Label getMarcaCoche(Reserva item) {
	    Optional<Vehiculo> car=this.carsService.getOne(item.getId_vehiculo());
	   if(car.isPresent()) {
		   return new Label(car.get().getMarca());
	   }else {
		   return new Label("");
	   }
	    
	}
	private Label getModeloCoche(Reserva item) {
	    Optional<Vehiculo> car=this.carsService.getOne(item.getId_vehiculo());
	   if(car.isPresent()) {
	    return new Label(car.get().getModelo());
	   }else {
		   return new Label("");
	   }
	}
	private Label getPrecioCoche(Reserva item) {
	    Optional<Vehiculo> car=this.carsService.getOne(item.getId_vehiculo());
	   if(car.isPresent()) {
	    return new Label(car.get().getPrecio().toString()+"€/día");
	   }else {
		   return new Label("");
	   }
	}

	private Button cancelarReserva(Grid<Reserva> grid, Reserva item)  {
		Optional<Vehiculo> car=this.carsService.getOne(item.getId_vehiculo());
		Vehiculo coche=car.get();
		Optional<Usuario> us=this.usersService.getOne(item.getId_usuario());
		Usuario usuario=us.get();
		coche.setEstado("Disponible");
		Date fecha1=item.getFecha_inicio();
		Date fecha2= item.getFecha_fin();
		String periodosinicial=coche.getPeriodo();
		String [] periodosiniciallista=periodosinicial.split(";");
		ArrayList<String> perifinal= new ArrayList<String>();
		String periodoanadir;
		System.out.println(periodosiniciallista[0]);
		System.out.println(periodosiniciallista[1]);
		for(int i=0;i<periodosiniciallista.length;i++) {
			String fechas[]=periodosiniciallista[i].split(" ");
			Date f1=Date.valueOf(fechas[0]);
			Date f2=Date.valueOf(fechas[1]);
			int diff=compfechas(f1, fecha2);
		    int diff2 = compfechas(f2, fecha1);
		    System.out.println(diff);
		    System.out.println(diff2);
		    if(diff2<=1) {
		    	if(i==periodosiniciallista.length-1) {
		    		periodoanadir=f1.toString()+" "+fecha2.toString();
		    		perifinal.add(periodoanadir);
		    	}else {
		    		
		    		String fechas2[]=periodosiniciallista[i+1].split(" ");
		    		Date f22=Date.valueOf(fechas2[1]);
		    		periodoanadir=f1.toString()+" "+f22.toString();
		    		perifinal.add(periodoanadir);
		    		
		    	}
		    }
		    if(diff<=1) {
		    	if(i==0) {
		    		periodoanadir=fecha1.toString()+" "+f2.toString();
		    		perifinal.add(periodoanadir);
		    	}else {
		    		
		    		String fechas2[]=periodosiniciallista[i-1].split(" ");
		    		Date f22=Date.valueOf(fechas2[0]);
		    		periodoanadir=f22.toString()+" "+f2.toString();
		    		perifinal.add(periodoanadir);
		    		
		    	}
		    }
		    if(diff>=2 && diff2>=2) {
		    	 perifinal.add(periodosiniciallista[i]);
		    }
		   
			
		}
		
		String periodofinal="";
		ArrayList<String> auxArray = (ArrayList<String>) perifinal.clone();
	
		for (String obj : perifinal) {
		while (auxArray.indexOf(obj) != auxArray.lastIndexOf(obj)) {
		auxArray.remove(auxArray.lastIndexOf(obj));
		}
		}
		perifinal = auxArray;
		for(int i=0;i<perifinal.size();i++) {
			
			periodofinal=periodofinal+perifinal.get(i)+";";
		}
		
		
		
		//periodofinal=coche.getPeriodo()+item.getFecha_inicio()+" "+item.getFecha_fin()+";";
		System.out.println(periodofinal);
		coche.setPeriodo(periodofinal);
		Button bc=new Button("Cancelar Reserva",e->{
			  this.reservasService.deleteById(item.getId());
			  this.carsService.save(coche);
			  
			   Calendar cal1 = new GregorianCalendar();
			   Calendar cal2 = new GregorianCalendar();
			   Date fecha_ini=item.getFecha_inicio();
			   Date fecha_f=item.getFecha_fin();
			   SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

			 
			     cal1.setTime(fecha_ini);
			   
			     cal2.setTime(fecha_f);
			 
			     
			 java.util.Date date = new java.util.Date();
			 Calendar cal12 = new GregorianCalendar();
			   Calendar cal22 = new GregorianCalendar();
			
			   Date fecha_i=item.getFecha_inicio();
			   SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");

			 
			     cal12.setTime(date);
			   
			     cal22.setTime(fecha_i);
			     
			 int diferencia = (int)( (cal2.getTime().getTime() - cal1.getTime().getTime()) / (1000 * 60 * 60 * 24));
			 int diashastareserva = (int)( (cal22.getTime().getTime() - cal12.getTime().getTime()) / (1000 * 60 * 60 * 24));
			 
			 Cargo cargo=new Cargo("PAGAR",coche.getId(),item.getId_usuario(),item.getId(),(50*diferencia)-(2*diashastareserva));
	 		 cargoService.save(cargo);
	 		 //CREAR  NUEVA FACTURA SOLO CON 50*DAYS
	 	  Factura factura=new Factura((50*diferencia)-(2*diashastareserva),usuario);
			  ListDataProvider<Reserva> dataProvider = (ListDataProvider<Reserva>) grid
		                .getDataProvider();
		        dataProvider.getItems().remove(item);
		        dataProvider.refreshAll();
	   });
	    return bc;
	}
	private Button reportarProblema(Reserva item)  {
		
	   Button bc=new Button("Reportar problema",e->{
		   String reporte=item.getReporte();
		   
		  
			   VerticalLayout verticalLayout = new VerticalLayout();
			   HorizontalLayout horizontalLayout=new HorizontalLayout();
				Dialog dialog = new Dialog(new Label("Reporte su problema"));
				dialog.setCloseOnEsc(false);
				dialog.setCloseOnOutsideClick(false);
				TextField Asunto=new TextField("Asunto:");
				TextField Contenido=new TextField("Descripción:");
				if(reporte!=null) {
					String texto=item.getReporte();
					String [] textoFormateado=texto.split(":;:;:;");
					if(texto.length()==2) {
					String Asuntito=textoFormateado[0];
					String Contenidito=textoFormateado[1];
					Asunto.setValue(Asuntito);
					Contenido.setValue(Contenidito);
					}else {
						String Asuntito=textoFormateado[0];
						Asunto.setValue(Asuntito);
					}
					
					
				}
				
				Button Enviar=new Button("Enviar",click->{
					
					if(error.isOpened()) {
						error.close();
					}
					if(!Asunto.getValue().equals("")){
						String textoFormateado;
						textoFormateado=Asunto.getValue();
						textoFormateado+=":;:;:;";
						textoFormateado+=Contenido.getValue();
						item.setReporte(textoFormateado);
						this.reservasService.save(item);
						dialog.close();
						
					}else{
						
						error.getElement().getStyle().set("color", "red");
						error.open();
						
						
					}
					
				});
				Button Cancelar=new Button("Cancelar",clicke->
					dialog.close()
				) ;
				horizontalLayout.add(Enviar,Cancelar);
				verticalLayout.add(Asunto,Contenido,horizontalLayout);
				dialog.add(verticalLayout);
				dialog.open();
		   
			 
	   });
	    return bc;
	}
	 
	
	
	
	
	
	private Button descargarFactura(Reserva item)  {
		Optional<Vehiculo> car=this.carsService.getOne(item.getId_vehiculo());
		
		coche=null;
		if(car.isPresent()) {
		coche=car.get();
		}
		Usuario user=this.usersService.findByUsername(SecurityUtils.getUsername());
	   
	    return new Button("Descargar Factura",e->{
			   try {
				   new Factura(item,coche,user);
				   
		        } catch (Exception eb) {
		            eb.printStackTrace();
		        }
				 
		   });
	}
	 
public int compfechas(Date f1,Date f2) {
		
		boolean flag=true;
		int cont=0;
		Calendar c = Calendar.getInstance();
        c.setTime(f2);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(f1);
        
        if(f1.after(f2)) {
        	
        	while(flag) {
        		if(f2.toString().equals(f1.toString())) {
    				flag=false;
    			}else {
    			c2.add(Calendar.DATE, -1);
    			f1=new Date(c2.getTimeInMillis());
    			cont++;
    			}
    		}
        }else {
        
        	
		while(flag){
			
			if(f2.toString().equals(f1.toString())) {	
				flag=false;
			}else {
			c.add(Calendar.DATE, -1);
			f2=new Date(c.getTimeInMillis());
			cont++;
			}
		}
        }
		return cont;
	}
	
}
