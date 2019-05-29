package es.uca.sub.Vistas;



import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

import es.uca.sub.Repositorios.Cargos.CargoService;
import es.uca.sub.Repositorios.Reserva.Reserva;
import es.uca.sub.Repositorios.Reserva.ReservasService;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Repositorios.Vehiculo.Vehiculo;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;
import es.uca.sub.Seguridad.SecurityUtils;
@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("buscador")
public class BuscadorCoches extends Composite<VerticalLayout> implements HasComponents, RouterLayout {
	
	private static final String PRECIO2 = "Precio";
	private static final String MODELO2 = "Modelo";
	private static final String ESTADO2 = "Estado";
	transient VehiculosService carService;
	Div body;
	transient UsuarioService usersService;
	transient ReservasService reservaService;
	transient CargoService cargoService;
	
	private Binder<Reserva> binder = new Binder<>(Reserva.class);
	
	@Autowired
	public BuscadorCoches(VehiculosService service,UsuarioService userService,ReservasService reservaService,CargoService cargos)
	{
		usersService=userService;
		this.carService=service;
		this.reservaService=reservaService;
		this.cargoService=cargos;
		if(SecurityUtils.isUserLoggedIn()) {
    		String user=SecurityUtils.getUsername();
 
    		if(SecurityUtils.hasRole("Cliente")) {
    			LayoutView layout=new LayoutView();
            	Header header = new Header();
            	
            	Footer footer = new Footer();
            	header= layout.HeaderClienteView(user);
            	footer = layout.FooterView();
            	body=layout.BodyView();
            	add(header);
            	add(body);
            	add(footer);
    			
    		}
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
    		
    		
    		
    	}else {
    		LayoutView layout=new LayoutView();
        	Header header = new Header();
        	
        	Footer footer = new Footer();
        	header= layout.HeaderView();
        	footer = layout.FooterView();
        	body=layout.BodyView();
        	add(header);
        	add(body);
        	add(footer);
    		
    	}
    
    	
    	
    	//Caja de búsqueda
    	TextField Marca=new TextField("Marca:");
    	Marca.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    	Marca.setPlaceholder("Introduzca la marca");
    	
    	TextField Modelo=new TextField("Modelo:");
    	Modelo.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    	Modelo.setPlaceholder("Introduzca el modelo");
    	
    	
    	Select<String> Estado = new Select<>();
    	Estado.setLabel(ESTADO2);
    	Estado.setItems("Libre", "Ocupado");

    	Estado.setEmptySelectionAllowed(true);
    	Estado.setEmptySelectionCaption("Seleccione el estado");

    	
    	
    	
    	
    	
    	PrecioWrapper Precio=new PrecioWrapper();
    	
    	//Select de opciones
    	Select<String> select = new Select<>(MODELO2, "Matricula",ESTADO2,"Características");
    	select.setPlaceholder("Categoría");
    	select.setClassName("select");
    
    	Div divArticles=new Div();
    	divArticles.setClassName("divArticles");
    	 //Botón de búsqueda
    	Button buscar=new Button("¡Busca!",click-> {
    		divArticles.removeAll();
    		int [] Campos=new int[5];
    		String metodo="findBy";
    		String especial="";
    		int count=0;
    		//Divs de todos los artículos
	    	String Smarca= Marca.getValue();
	    	String Smodelo=Modelo.getValue();
	    	String Sestado=Estado.getValue();
	    	String Sprecio=Precio.generateModelValue();
	    	
	    	Map<String, String> Values = new HashMap<>();
	    	if(!Smarca.equals("")) {
	    		metodo+="Marca";
	    		count++;
	    		especial+="Marca,";
	    		Values.put("Marca", Smarca);
	    	}
	    	if(!Smodelo.equals("")) {
	    		metodo+=MODELO2;
	    		count++;
	    		Campos[1]=1;
	    		especial+="Modelo,";
	    		Values.put(MODELO2, Smodelo);
	    	}
	    	if(Sestado!=null) {
	    		metodo+=ESTADO2;
	    		count++;
	    		especial+="Estado,";
	    		Values.put(ESTADO2, Sestado);
	    	}
	    	if(!Sprecio.equals("")) {
	    		metodo+=PRECIO2;
	    		count++;
	    		especial+=PRECIO2;
	    		Values.put(PRECIO2, Sprecio);
	    	}
	    	Method method=null;
	    	List<Vehiculo> listCar = null;
	    	try {
	    		if(count==1) {
	    			method = carService.getClass().getMethod(metodo, String.class);
	    		}
	    		if(count==2) {
	    			method = carService.getClass().getMethod(metodo, String.class, String.class);
	    		}
	    		if(count==3) {
	    			method = carService.getClass().getMethod(metodo, String.class, String.class,String.class);
	    		}
	    		if(count==4) {
	    			method= carService.getClass().getMethod(metodo, String.class,String.class,String.class,String.class);
	    		}
	    	  
	    	  
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	  
	    	try {
	    		if(count==4) {
	    			String [] VarValues=especial.split(",");
	    			listCar=(List<Vehiculo>)method.invoke(carService,Values.get(VarValues[0]),Values.get(VarValues[1]),Values.get(VarValues[2]),Values.get(VarValues[3]));
	    		}else if(count==3){
	    			String [] VarValues=especial.split(",");
	    			listCar=(List<Vehiculo>)method.invoke(carService,Values.get(VarValues[0]),Values.get(VarValues[1]),Values.get(VarValues[2]));
	    		}else if(count==2) {
	    			String [] VarValues=especial.split(",");
	    			listCar=(List<Vehiculo>)method.invoke(carService,Values.get(VarValues[0]),Values.get(VarValues[1]));
	    		}else if(count==1) {
	    			String [] VarValues=especial.split(",");
	    			listCar=(List<Vehiculo>)method.invoke(carService,Values.get(VarValues[0]));
	    		}else if(count==0) {
	    			listCar=carService.findAll();
	    		}
	    		  
	    		} catch (Exception e) { e.printStackTrace(); }
	    		  
	    		
	    	
	  
	    	
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
    				Label precio=new Label ("El precio  es: "+coche.getPrecio().toString());
    				precio.setClassName("et");
    				Html a=new Html("<img class='im'src="+coche.getImagen()+">");
    				articulo.add(a);
    				articulo.add(matricula);
    				articulo.add(marca);
    				articulo.add(caracteristicas);
    				articulo.add(estado);
    				articulo.add(precio);
    				int parametros=coche.getId();
    			
    				RouterLink  botonEnlace= new RouterLink("¡Ver más!",UnCocheView.class,parametros);
    				botonEnlace.setClassName("boton2");
    				articulo.add(botonEnlace);
    				UI.getCurrent().setLocale(new Locale("es","ES"));
    				Button boton=new Button("Reservar",clicke->{
    					if(SecurityUtils.isUserLoggedIn()) {
    						new ReservaForm(coche,usersService,carService,reservaService,cargoService);
    					}else {
    						UI.getCurrent().navigate(SignInPage.class);
    					}
    					
    					
    				});
    				boton.setClassName("boton2");
    				if(!coche.isAvailable()) {
    					boton.setEnabled(false);
    				}
    				
    				articulo.add(boton);
    				  
    				
    				divArticles.add(articulo);
    			
    				}
	    	}
	    
		  });
    	
    	 


    	 HorizontalLayout hr = new HorizontalLayout(Marca, Modelo,Estado,Precio,buscar);
    	 hr.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
    	 hr.setAlignItems(FlexComponent.Alignment.BASELINE);
     	
    	 
    	 body.add(hr);
    	

    	 
    	 body.add(divArticles);
    	
    	
		
	}

	 

}
