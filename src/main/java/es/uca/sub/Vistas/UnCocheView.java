package es.uca.sub.Vistas;




import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import es.uca.sub.Repositorios.Vehiculo.Vehiculo;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;

@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("unCoche")
public class UnCocheView extends Composite<VerticalLayout> implements HasComponents, HasUrlParameter<Integer> {
	private transient VehiculosService carService;
	private int id;
	Div body;
	Div izq;
	Div der;
	@Override
    public void setParameter(BeforeEvent event, Integer parameters) {
			
			id=parameters;
			Optional<Vehiculo> car =carService.getOne(id);
			Vehiculo coche=null;
			if(car.isPresent()) {
				coche=car.get();
			}
			if(coche==null) {
				throw new NullPointerException();
			}
		
			
				
			Label matricula=new Label ("La matricula es: "+coche.getMatricula());
			matricula.setClassName("et1");
			
			
			Label marca=new Label ("La marca es: "+coche.getMarca());
			marca.setClassName("et1");
		
			Label estado=new Label ("El estado  es: "+coche.getEstado());
			estado.setClassName("et1");
			Label precio=new Label ("El precio  es: "+coche.getPrecio());
			precio.setClassName("et1");
			der.add(matricula);
			der.add(marca);
			der.add(estado);
			
			der.add(precio);
			Html a=new Html("<img class='im2'src="+coche.getImagen()+">");
			
			
			
			
			
			
			izq.add(a);
			
			
		}

	@Autowired
	public UnCocheView(VehiculosService service)
	{
		this.carService=service;
    	LayoutView layout=new LayoutView();
    	Header header = new Header();
        body= new Div();
    	Footer footer = new Footer();
    	header= layout.HeaderView();
    	footer = layout.FooterView();
    	body=layout.BodyView();
    	add(header);
    	add(body);
		add(footer);
	    izq= new Div();
	    der= new Div();
		izq.setClassName("izq");
		der.setClassName("der");
		body.add(izq);
		body.add(der);
		
		
		
	}

	 

}