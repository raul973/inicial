package es.uca.sub.Vistas;


import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

public class PrecioWrapper extends CustomField<String> {
	Select<String> Modifier=new Select<>();
	NumberField euroFieldS = new NumberField();
	NumberField euroFieldE = new NumberField();
	static final String Between="between";
	
    public PrecioWrapper() {
    	Modifier.setItems("<","<=",Between,">",">=");
    	Modifier.setEmptySelectionAllowed(true);
    	Modifier.setEmptySelectionCaption("Seleccione rango");
    	euroFieldS.setSuffixComponent(new Span("€"));
    	euroFieldS.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    	euroFieldE.setSuffixComponent(new Span("€"));
    	euroFieldE.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    	euroFieldE.getElement().setVisible(false);
        setLabel("Precio");
        HorizontalLayout hr = new HorizontalLayout(euroFieldS,Modifier,euroFieldE);
        Modifier.addValueChangeListener(e->{if(Modifier.getValue()!=null && Modifier.getValue().equals(Between)){
        	euroFieldE.getElement().setVisible(true);
        }else {
        	
        		euroFieldE.getElement().setVisible(false);
        	
        }
        });
     	hr.setAlignItems(FlexComponent.Alignment.BASELINE);
        add(hr);
    }

    @Override
    protected String generateModelValue() {
    	String result="";
    	Integer number1=0;
    	Integer number2=0;
    	if(Modifier.getValue()!=null) {
	    	if(Modifier.getValue().equals(Between)) {
	    		if(euroFieldS.getValue()!=null) {
	    		number1=euroFieldS.getValue().intValue();
		    		if(euroFieldE.getValue()!=null) {
		    		number2=euroFieldE.getValue().intValue();
		    		result=number1.toString()+","+Modifier.getValue()+","+number2.toString();
		    		}
	    		}
	    	}else {
	    		if(euroFieldS.getValue()!=null) {
	    		number1=euroFieldS.getValue().intValue();
	    		result=number1.toString()+","+Modifier.getValue();
	    		}
	    	}
    	return result;
    	}else {
        return result;
    	}
    }
    
    /*
     * (non-Javadoc)
     * @see com.vaadin.flow.component.customfield.CustomField#setPresentationValue(java.lang.Object)
     */
    @Override
    protected void setPresentationValue(String abc) {
        
    }
}