package es.uca.sub.Vistas;




import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;

public class DisponibilidadWrapper extends CustomField<String> {
	Select<String> diasS=new Select<>();
	Select<String> diasE=new Select<>();
	Tab MiddleG=new Tab("-");
	
    public DisponibilidadWrapper() {
    	diasS.setItems("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo");
    	diasS.setEmptySelectionCaption("Seleccion el dia");
    	diasE.setItems("Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo");
    	diasE.setEmptySelectionCaption("Seleccion el dia");
    	MiddleG.setEnabled(false);
        setLabel("Disponibilidad");
        HorizontalLayout hr = new HorizontalLayout(diasS,MiddleG,diasE);
     	hr.setAlignItems(FlexComponent.Alignment.BASELINE);
        add(hr);
    }

    @Override
    protected String generateModelValue() {
    	return diasS.getValue()+"-"+diasE.getValue();
        
    }

    @Override
    protected void setPresentationValue(String newPresentationValue) {
        
    }
}