package es.uca.sub.Vistas;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Crosshair;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Marker;
import com.vaadin.flow.component.charts.model.PlotOptionsLine;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

import es.uca.sub.Repositorios.Reserva.Reserva;
import es.uca.sub.Repositorios.Reserva.ReservasService;
import es.uca.sub.Repositorios.Usuario.UsuarioService;
import es.uca.sub.Repositorios.Vehiculo.VehiculosService;
import es.uca.sub.Seguridad.SecurityUtils;

@StyleSheet("styles/styles.css")
@StyleSheet("styles/theme.css")
@Route("Monitorizacion")
@Secured("Gerente")
public class ActividadView extends Composite<VerticalLayout> implements HasComponents, RouterLayout, BeforeEnterObserver {
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted= SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if(!accessGranted) {
			event.rerouteTo(MainView.class);
		}
	}
	
	transient VehiculosService carService;
	transient UsuarioService usersService;
	transient ReservasService reservaService;
	
	@Autowired
	public ActividadView(VehiculosService service,UsuarioService userService,ReservasService reservaService)
	{
		usersService=userService;
		this.carService=service;
		this.reservaService=reservaService;
    	LayoutView layout=new LayoutView();
    	Header header = new Header();
    	Div body= new Div();
    	Footer footer = new Footer();
    	header= layout.HeaderGerenteView(SecurityUtils.getUsername());
    	footer = layout.FooterView();
    	body=layout.BodyView();
    	add(header);
    	add(body);
		add(footer);
		Chart chart = new Chart(ChartType.COLUMN);
		List<Reserva> reservas=reservaService.findAll();
		
		int [] TotalFechas=new int[12];
		int [] TotalImportes=new int[12];
		for(Reserva p:reservas) {
			String [] aux=p.getFecha_reserva().toString().split("-");
			if(aux[1].equals("01")) {
				TotalImportes[0]+=p.getImporte();
				TotalFechas[0]++;
			}
			if(aux[1].equals("02")) {
				TotalFechas[1]++;
				TotalImportes[0]+=p.getImporte();
			}
			if(aux[1].equals("03")) {
				TotalFechas[2]++;	
				TotalImportes[2]+=p.getImporte();
			}
			if(aux[1].equals("04")) {
				TotalFechas[3]++;
				TotalImportes[3]+=p.getImporte();
			}
			if(aux[1].equals("05")) {
				TotalFechas[4]++;
				TotalImportes[4]+=p.getImporte();
			}
			if(aux[1].equals("06")) {
				TotalFechas[5]++;
				TotalImportes[5]+=p.getImporte();
			}
			if(aux[1].equals("07")) {
				TotalFechas[6]++;
				TotalImportes[6]+=p.getImporte();
			}
			if(aux[1].equals("08")) {
				TotalFechas[7]++;
				TotalImportes[7]+=p.getImporte();
			}
			if(aux[1].equals("09")) {
				TotalFechas[8]++;
				TotalImportes[8]+=p.getImporte();
						}
			if(aux[1].equals("10")) {
				TotalFechas[9]++;
				TotalImportes[9]+=p.getImporte();
			}
			if(aux[1].equals("11")) {
				TotalFechas[10]++;
				TotalImportes[10]+=p.getImporte();
			}
			if(aux[1].equals("12")) {
				TotalFechas[11]++;
				TotalImportes[11]+=p.getImporte();
			}
		}

		
		
		body.add(chart);
		Configuration conf = chart.getConfiguration();
		conf.setTitle("Monitorización de la empresa");
		// Disable markers from lines
		PlotOptionsLine plotOptions = new PlotOptionsLine();
		plotOptions.setMarker(new Marker(false));
		conf.setPlotOptions(plotOptions);
		ListSeries series = new ListSeries("Nº Reservas");
		ListSeries series2=new ListSeries("Facturación €");
		
		series.setData(TotalFechas[0],  TotalFechas[1],  TotalFechas[2],TotalFechas[3],TotalFechas[4],
				TotalFechas[5],  TotalFechas[6], TotalFechas[7],TotalFechas[8],TotalFechas[9],
						TotalFechas[10], TotalFechas[11]);
		series2.setData(TotalImportes[0],  TotalImportes[1],  TotalImportes[2],TotalImportes[3],TotalImportes[4],
				TotalImportes[5],  TotalImportes[6], TotalImportes[7],TotalImportes[8],TotalImportes[9],
						TotalImportes[10], TotalImportes[11]);
		
		conf.addSeries(series);
		conf.addSeries(series2);
		XAxis xaxis = new XAxis();
		xaxis.setCrosshair(new Crosshair());
		xaxis.setCategories("Enero", "Febrero",   "Marzo",
		                    "Abril",    "Mayo", "Junio",
		                    "Julio",  "Agosto",  "Septiembre",  "Octubre",  "Noviembre",  "Diciembre");
		xaxis.setTitle("Meses");
		conf.addxAxis(xaxis);
		// Set the Y axis title
		YAxis yaxis = new YAxis();
		yaxis.setTitle("Total");
		conf.addyAxis(yaxis);
		Tooltip tooltip = new Tooltip();
        tooltip.setShared(true);
        conf.setTooltip(tooltip);
		
	}

	 

}
