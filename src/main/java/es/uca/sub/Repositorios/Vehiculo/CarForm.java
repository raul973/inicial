package es.uca.sub.Repositorios.Vehiculo;


import java.time.ZoneId;
import java.util.Date;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import es.uca.sub.Vistas.RegistrarVehiculoView;


public class CarForm extends FormLayout {
	
	//@Autowired
	VehiculosService carService;
	
	private TextField marca = new TextField("Marca");
	private TextField matricula = new TextField("Matricula");
	private TextField modelo = new TextField("Modelo");
	private TextField disponibilidad = new TextField("Disponibilidad");
	private Select<String> estado = new Select<>("Disponible", "Reservado","En reparación");
	private Select<String> GPS;
	private Select<String> plazas;
	private  Select<String> climatizador;
	private Select<String> transmision;
	private TextField precio = new TextField("Precio");
	private NumberField precio2=new NumberField("Precio");
	private TextField imagen = new TextField("Imagen");
	private Details component;
	private VerticalLayout ver;
	private TextField carroceria ;
	private Select<String> motor ;
	private String caracteristicas;
	private DatePicker fecha_inicio;
	private DatePicker fecha_fin;
	private Button Register = new Button("Registrar");
	
	private Binder<Vehiculo> binder = new Binder<Vehiculo>(Vehiculo.class);
	
	private RegistrarVehiculoView mainView;
	
	//@Autowired
	public CarForm(RegistrarVehiculoView mainView,VehiculosService aux) {
		this.mainView=mainView;
		estado.setPlaceholder("Seleccione el estado");
		carService=aux;
		Register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		ver =new VerticalLayout();
		component = new Details();
        component.setSummaryText("Características");
        climatizador = new Select<>("Sí","No");
        climatizador.setPlaceholder("Climatizador");
         GPS = new Select<>("Sí","No");
        GPS.setPlaceholder("GPS");
        plazas = new Select<>("2","4","5","6","7","8");
        plazas.setPlaceholder("Número de plazas");
        transmision = new Select<>("Manual","Automatico");
        transmision.setPlaceholder("Tipo de transmisión");
        carroceria = new TextField("Carroceria");
        carroceria.setPlaceholder("Carrocería");
        motor = new Select<>("Diésel","Híbrido","Gasolina");
        motor.setPlaceholder("Motor");
        component.addContent(climatizador,GPS,plazas,transmision,carroceria,motor);
        component.addThemeVariants(DetailsVariant.SMALL);
        ver.add(component);
        fecha_inicio=new DatePicker("Fecha inicio");
        fecha_fin=new DatePicker("Fecha fin");
		add(marca,matricula,modelo,estado,fecha_inicio,fecha_fin,ver,precio2,Register,imagen);
		precio.setValue("");
		
		precio2.addValueChangeListener(e->{
			if(precio2.getValue()!=null) {
				Integer auxil=precio2.getValue().intValue();
			precio.setValue(auxil.toString());
			
			}});
		
		binder.forField(precio)
		.withConverter(new StringToIntegerConverter("Error"))
		.asRequired()
		.bind(Vehiculo::getPrecio,Vehiculo::setPrecio);
		binder.bindInstanceFields(this);
		
		
		Register.addClickListener(event -> register());
		
		
	}
	
	public void register() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date_f=new Date();
		Date date_i=new Date();
		if(fecha_fin.getValue()==null || fecha_inicio.getValue()==null) {
			Dialog dialog = new Dialog();
			dialog.add(new Label("Alguna de las fechas estan vacias, introducelas." ));

			dialog.setWidth("400px");
			dialog.setHeight("150px");
			dialog.open();
			
		}else {
		date_f = Date.from(fecha_fin.getValue().atStartOfDay(defaultZoneId).toInstant());
		date_i = Date.from(fecha_inicio.getValue().atStartOfDay(defaultZoneId).toInstant());
		}
		Date fecha = new Date();
		
		if(marca.getValue().equals("")) {
			Dialog dialog = new Dialog();
			dialog.add(new Label("Has introducido mal la marca, introduzca otra" ));

			dialog.setWidth("400px");
			dialog.setHeight("150px");
			dialog.open();
			
		}else if(modelo.getValue().equals("")) {
			Dialog dialog = new Dialog();
			dialog.add(new Label("Has introducido mal el modelo, introduzca otro" ));

			dialog.setWidth("400px");
			dialog.setHeight("150px");
			dialog.open();
			
		}else if(matricula.getValue().equals("")) {
			Dialog dialog = new Dialog();
			dialog.add(new Label("Has introducido mal la matricula, introduzca otra" ));

			dialog.setWidth("400px");
			dialog.setHeight("150px");
			dialog.open();
			
		}else if(imagen.getValue().equals("")) {
			Dialog dialog = new Dialog();
			dialog.add(new Label("Has introducido mal la imagen, introduzca otra" ));

			dialog.setWidth("400px");
			dialog.setHeight("150px");
			dialog.open();
			
		}else if(estado.getValue()==null) {
			Dialog dialog = new Dialog();
			dialog.add(new Label("Has introducido mal el estado, introduzca otro" ));

			dialog.setWidth("400px");
			dialog.setHeight("150px");
			dialog.open();
			
		}else if( precio.getValue().equals("")) {
			Dialog dialog = new Dialog();
			dialog.add(new Label("Introduzca el precio, por favor" ));

			dialog.setWidth("400px");
			dialog.setHeight("150px");
			dialog.open();
		}
		else if(Integer.valueOf(precio.getValue())<0) {
			Dialog dialog = new Dialog();
			dialog.add(new Label("Has introducido mal el precio, introduzca otro" ));

			dialog.setWidth("400px");
			dialog.setHeight("150px");
			dialog.open();
		}else {
		if(date_f.compareTo(date_i)<=0||date_i.compareTo(fecha)<0) {
			Dialog dialog = new Dialog();
			dialog.add(new Label("Has introducido mal la fecha, haz click fuera para salir" ));

			dialog.setWidth("400px");
			dialog.setHeight("150px");
			dialog.open();
		}else {
     
	     
		Vehiculo car = new Vehiculo();
		try {
			binder.writeBean(car);
			String periodo=fecha_inicio.getValue().toString()+" "+fecha_fin.getValue().toString()+";";
			caracteristicas=new String(climatizador.getValue()+" "+GPS.getValue()+" "+plazas.getValue()+" "+transmision.getValue()+" "+carroceria.getValue()+" "+motor.getValue());
		
			car.setPeriodo(periodo);
			car.setCaracteristicas(caracteristicas);
		} catch (ValidationException e) {
		
			e.printStackTrace();
		}
		
	
	String id=marca.getValue();
	
	
	
		carService.save(car);
	


	}
		
		}
	}
	


	
	}
