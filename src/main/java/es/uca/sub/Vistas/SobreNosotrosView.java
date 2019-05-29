package es.uca.sub.Vistas;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Header;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;


@Route("nosotros")
public class SobreNosotrosView extends Composite<VerticalLayout> implements HasComponents, RouterLayout{

	public SobreNosotrosView()
	{
    	LayoutView layout=new LayoutView();
    	Header header = new Header();
    	Div body= new Div();
    	Footer footer = new Footer();
    	header= layout.HeaderView();
    	footer = layout.FooterView();
    	body=layout.BodyView();
    	add(header);
    	add(body);
    	VerticalLayout texto =new VerticalLayout();
        Details component = new Details();
        component.setSummaryText("Sobre Nosotros");
        component.addContent(new H3("Autohired"), new Text("Autorhired es un referente en el alquiler vacacional de vehículos en Europa, siendo líder del sector en países como España o Portugal.  La empresa, con más de 30 años de trayectoria, dispone en la actualidad de más de 105 oficinas de alquiler de vehículos. Una amplia red de atención al usuario presente en 17 países como son: España, Portugal, Italia, Francia, Grecia, Malta, Andorra, Marruecos, Países Bajos, Croacia, Rumanía, Chipre, Reino Unido, Turquía, Serbia, Islandia y Montenegro. La lista de destinos Goldcar crece cada año para acercar a los viajeros una amplia variedad de posibilidades con las que descubrir el mundo al volante de los coches de alquiler más nuevos del mercado.  El grupo pone a disposición de sus clientes más de 60.000 vehículos que se renuevan cada año en más de un 80%, configurando una de las flotas de alquiler más jóvenes de Europa. Además, Goldcar cuenta con una plantilla altamente cualificada compuesta por más de 1.100 profesionales, dedicados a ofrecer a todos sus clientes un servicio de calidad, transparente y de vanguardia."));
        component.addThemeVariants(DetailsVariant.SMALL);
        texto.add(component);
        body.add(texto);       
    	add(footer);

	}
}
