package es.uca.sub.Vistas;


import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.DateToSqlDateConverter;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.server.StreamResource;

import es.uca.sub.Repositorios.Cargos.Cargo;
import es.uca.sub.Repositorios.Cargos.CargoService;
import es.uca.sub.Repositorios.Reserva.Reserva;
import es.uca.sub.Repositorios.Reserva.ReservasService;
import es.uca.sub.Repositorios.Usuario.Usuario;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Repositorios.Vehiculo.Vehiculo;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;
import es.uca.sub.Seguridad.SecurityUtils;

public class ReservaForm {
	
	UsuarioService usersService;
	VehiculosService carsService;
	CargoService cargoService;
	ReservasService service;
	private DatePicker fecha_inicio=new DatePicker();
	private DatePicker fecha_fin=new DatePicker();
	private DatePicker fecha_reserva=new DatePicker();
	private int iteratorperiod;
	private Vehiculo co;
	private TextField id_usuario=new TextField();
	private TextField id_vehiculo=new TextField();
	private TextField importe=new TextField();
	Select<String> seguro = new Select<>("Sin seguro", "Normal","Premium");
	private Binder<Reserva> binder = new Binder<>(Reserva.class);
	
	Notification error=new Notification();
	Date fecha1Binder;
	Date fecha2Binder;
	
	public ReservaForm(Vehiculo coche,UsuarioService usersService,VehiculosService carsService,ReservasService reservaService,CargoService cargos) {
		this.usersService=usersService;
		this.carsService=carsService;
		this.cargoService=cargos;
		service=reservaService;
		this.co=coche;
		
		reservaView(coche);
		
		
	}
	public void reservaView(Vehiculo coche) {
		VerticalLayout verticalLayout = new VerticalLayout();
		Dialog dialog = new Dialog(new Label("Seleccione fechas y seguro"));
		dialog.setCloseOnEsc(false);
		
			DatePicker startDatePicker = new DatePicker();
			startDatePicker.setLabel("Fecha de entrega");
			startDatePicker.setLocale(new Locale("es","ES"));
			
			DatePicker endDatePicker = new DatePicker();
			endDatePicker.setLabel("Fecha de devolución");
			endDatePicker.setLocale(new Locale("es","ES"));
			
			
			startDatePicker.addValueChangeListener(event -> {
			    LocalDate startDate = event.getValue();
			    Date fecha=Date.valueOf(startDate);
			    fecha1Binder=Date.valueOf(startDate);
			    Calendar c = Calendar.getInstance();
		        c.setTime(fecha);
		        c.add(Calendar.DATE, 1);
		       
		        
		        fecha_inicio.setValue(fecha.toLocalDate());
			});

			endDatePicker.addValueChangeListener(event -> {
				LocalDate endDate=event.getValue();
				Date fecha=Date.valueOf(endDate);
				fecha2Binder=Date.valueOf(endDate);
			    Calendar c = Calendar.getInstance();
		        c.setTime(fecha);
		        c.add(Calendar.DATE, 1);
		     
		        
		        fecha_fin.setValue(fecha.toLocalDate());
			});
			
			seguro.setPlaceholder("Elija el seguro");
			seguro.setLabel("Seguro");
			
			Button botonAc=new Button("Realizar reserva",ev->{
				Date today = new Date(new java.util.Date().getTime());
				Calendar c = Calendar.getInstance();
		        c.setTime(today);
		        c.add(Calendar.DATE, 1);
		        
				
				if(fecha1Binder==null ) {
					Notification.show("Fecha de entrega vacía. Introduzca una.");
				}else if(fecha2Binder==null) {
					Notification.show("Fecha de devolución vacía. Introduzca una.");
				}else if(seguro.getValue()==null) {
					Notification.show("Por favor, seleccione una de las opciones de seguro.");
				}else if(fecha1Binder.before(today)) {
					Notification.show("Fecha de entrega incorrecta. Introducela de nuevo.");
				}else if(fecha2Binder.before(today) ) {
					Notification.show("Fecha de devolución incorrecta o vacía. Rellenela de nuevo");
				}else if(!fecha2Binder.after(fecha1Binder)){
					Notification.show("Fecha de devolución incorrecta.\nIntroduzca una fecha posterior a la fecha de entrega.");
				}else {
					 
					String periodo=coche.getPeriodo();
					String [] spliteo=periodo.split(";");
					List<String> options = new ArrayList<>();
					boolean okfe=true;
					iteratorperiod=0;
					int it=0;
					for( String rec : spliteo) {
						options.add(rec);
						String [] fe= rec.split(" ");
						Date f1= Date.valueOf(fe[0]);
						Date f2= Date.valueOf(fe[1]);
						
						if(fecha1Binder.after(f2)||fecha1Binder.before(f1)||fecha2Binder.after(f2)||fecha2Binder.before(f1)) {
							okfe=false;
						}else {
							
							okfe=true;
							iteratorperiod=it;
							break;
						}
						it++;
						
					}
						if(!okfe) {
							Notification.show("Fecha de reserva incorrecta.\nIntroduzca una fecha en la que este disponible."+periodo);
						}else {
				VerticalLayout verticalLayout2 = new VerticalLayout();
				Dialog dialog2 = new Dialog(new Label("¿Estás seguro de reservar?\n Por favor, confirme la reserva."));
				dialog2.setCloseOnEsc(false);
				dialog2.setCloseOnOutsideClick(false);
				HorizontalLayout horL=new HorizontalLayout();
				
				fecha_reserva.setValue(today.toLocalDate());
				
				Button botonConf=new Button("Confirmar",ev2->{
					id_vehiculo.setValue(coche.getId().toString());
					
					
					if(SecurityUtils.isUserLoggedIn()) {
			    		String user=SecurityUtils.getUsername();
			    		Usuario usuario=usersService.findByUsername(user);
			    		id_usuario.setValue(usuario.getId().toString());
			    		
			    	}
					
					binder.forField(this.fecha_inicio)
					.withConverter(new LocalDateToDateConverter())
					.withConverter(new DateToSqlDateConverter())
					.asRequired().bind(Reserva::getFecha_inicio,Reserva::setFecha_inicio);
					binder.forField(this.fecha_fin)
					.withConverter(new LocalDateToDateConverter())
					.withConverter(new DateToSqlDateConverter())
					.asRequired().bind(Reserva::getFecha_fin,Reserva::setFecha_fin);
					
					binder.forField(this.fecha_reserva)
					.withConverter(new LocalDateToDateConverter())
					.withConverter(new DateToSqlDateConverter())
					.asRequired().bind(Reserva::getFecha_reserva,Reserva::setFecha_reserva);
					int dias=(int) ((fecha2Binder.getTime()-fecha1Binder.getTime())/86400000);
					//IVA
					Integer subtotal=(dias*coche.getPrecio());
					//SI LO COGE UN MONTON DE DIAS
					if(dias>10) {
						subtotal-=100;
					}
					String user=SecurityUtils.getUsername();
		    		Usuario usuario=usersService.findByUsername(user);
					List<Reserva> reservas= service.findById_usuario(usuario.getId());
					
					if(reservas.size()>=3) {
						subtotal-=50;
					}
					importe.setValue(subtotal.toString());
					
					
					binder.forField(seguro).bind(Reserva::getSeguro,Reserva::setSeguro);
					binder.forField(id_usuario)
					.withConverter(new StringToIntegerConverter("Error id user"))
					.asRequired()
					.bind(Reserva::getId_usuario,Reserva::setId_usuario);
					
					
					binder.forField(id_vehiculo)
					.withConverter(new StringToIntegerConverter("Error id vehiculo"))
					.asRequired()
					.bind(Reserva::getId_vehiculo,Reserva::setId_vehiculo);
					
					binder.forField(importe)
					.withConverter(new StringToIntegerConverter("Error importe"))
					.asRequired()
					.bind(Reserva::getImporte,Reserva::setImporte);
					
					binder.bindInstanceFields(this);
					
					 
					Dialog dialogF2 = new Dialog(new Label("Factura generada.¿Desea descargarla ahora?"));
					dialogF2.setCloseOnEsc(false);
					dialogF2.setCloseOnOutsideClick(false);
					Reserva reserva= reserva();
					Reserva reserva2=service.getOne(reserva.getId()).get();
					VerticalLayout pasarela=new VerticalLayout();
					Dialog pasarelaDialog=new Dialog(new Label("Introduzca un tarjeta de crédito/débito:"));
					TextField tarjetaTextField=new TextField();
					Button anadirTarjeta=new Button("Aceptar",click->{
						
						ArrayList<String> listOfPattern=new ArrayList<>();

						String ptVisa = "^4[0-9]{6,}$";
						listOfPattern.add(ptVisa);
						String ptMasterCard = "^5[1-5][0-9]{5,}$";
						listOfPattern.add(ptMasterCard);
						String ptAmeExp = "^3[47][0-9]{5,}$";
						listOfPattern.add(ptAmeExp);
						String ptDinClb = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";
						listOfPattern.add(ptDinClb);
						String ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
						listOfPattern.add(ptDiscover);
						String ptJcb = "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$";
						listOfPattern.add(ptJcb);
						
						
						if(!tarjetaTextField.getValue().equals("")) {
							String tarjeta=tarjetaTextField.getValue();

							for(String p:listOfPattern){
							      if(tarjeta.matches(p)){
							        dialogF2.open();
							        
							        
							         
							      }
							   }
					
						
						Notification.show("No podemos validar su tarjeta, revise lo que ha escrito.");
						
							
						
						}else {
				
							Notification.show("Por favor, rellene el campo de tarjeta.");
							
						
						}
					});
					pasarela.add(tarjetaTextField,anadirTarjeta);
					pasarelaDialog.setCloseOnOutsideClick(false);
					pasarelaDialog.setCloseOnEsc(false);
					pasarelaDialog.add(pasarela);
					pasarelaDialog.open();
					
					
					coche.setEstado("Ocupado");
					carsService.save(coche);
					 HorizontalLayout hrol=new HorizontalLayout();
						Button botonConfF=new Button("Si",click->{
							
							
							Usuario usuario2=usersService.findByUsername(SecurityUtils.getUsername());
							int plus=0;
							if(reserva2.getSeguro().equals("Premium")) {
							plus=100;
							}
							if(reserva2.getSeguro().equals("Normal")) {
								plus=50;
								}
							Reserva aux=service.getOne(reserva2.getId()).get();
							aux.setImporte(reserva2.getImporte()+(int)Math.round(reserva2.getImporte()*0.21)+plus);
							aux=service.save(aux);
							
							 new Factura(reserva2,coche,usuario2);
							
					        
							
					        
					        
					        /*
							Anchor download = new Anchor(new StreamResource("Factura.pdf", () -> new Factura(reserva2,coche,usuario2)), "");
							  download.getElement().setAttribute("download", true);
							  download.add(new Button(new Icon(VaadinIcon.DOWNLOAD_ALT)));
							*/
						
							 Cargo cargo=new Cargo("PAGAR",coche.getId(),usuario2.getId(),aux.getId(),aux.getImporte());
			        		 cargoService.save(cargo);
							
							dialogF2.close();
							pasarelaDialog.close();
							dialog2.close();
							dialog.close();
						});
						Button botonCancF=new Button("No",click->{
							
							
							dialogF2.close();
							pasarelaDialog.close();
							dialog2.close();
							dialog.close();
						});
						
						hrol.add(botonConfF,botonCancF);
						dialogF2.add(hrol);
						
					
					
				});
				Button botonCan=new Button("Cancelar",ev2->{
					dialog2.close();
					dialog.close();
				});
				horL.add(botonConf,botonCan);
				verticalLayout2.add(horL);
				dialog2.add(verticalLayout2);
				dialog2.open();
				
				}
				}
			});
			Button botonCa=new Button("Salir",ev->
				dialog.close()
			);
		    
		verticalLayout.add(startDatePicker,endDatePicker,seguro,botonAc,botonCa);
		dialog.add(verticalLayout);
		dialog.open();
	}
	
	public Reserva reserva() {
		Reserva reserva=new Reserva();
		
		try {
			binder.writeBean(reserva);
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		String periods=co.getPeriodo();
		String[] periodsf= periods.split(";");
		String []periodo = periodsf[iteratorperiod].split(" ");
		Date fecha1=Date.valueOf(periodo[0]);
		Date fecha2=Date.valueOf(periodo[1]);
		Date fechare2=reserva.getFecha_fin();
		Date fechare1= reserva.getFecha_inicio();
		
		String nuevoperiodo=fecha1.toString()+" "+fechare1.toString()+";"+fechare2.toString()+" "+fecha2.toString();
		periodsf[iteratorperiod]=nuevoperiodo;
		String periodoscoche="";
		for(int i=0;i<periodsf.length;i++) {
			periodoscoche=periodoscoche+periodsf[i]+";";
		}
		
		co.setPeriodo(periodoscoche);
		carsService.save(co);
		reserva=service.save(reserva);
		
		return reserva;
	}
	private StreamResource createResource(String filename,Factura f) {
		return new StreamResource("Pepe.pdf",
                () -> new ByteArrayInputStream(f.getFichero()));
    }
	
	

}
