package es.uca.sub.Vistas;



import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Seguridad.SecurityUtils;
/**
 * The main view contains a button and a click listener.
 */

@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("")
public class MainView extends Composite<VerticalLayout> implements HasComponents, RouterLayout {

	transient UsuarioService usersService;
	static UI ui;
	@Autowired
    public MainView(UsuarioService userService) {

    	usersService=userService;
    	if(SecurityUtils.isUserLoggedIn()) {
    		String user=SecurityUtils.getUsername();
 
    		if(SecurityUtils.hasRole("Cliente")) {
    			LayoutView layout=new LayoutView();
            	Header header = new Header();
            	Div body= new Div();
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
            	Div body= new Div();
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
           	Div body= new Div();
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
}