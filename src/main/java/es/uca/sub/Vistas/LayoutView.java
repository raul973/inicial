package es.uca.sub.Vistas;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServletService;
import com.vaadin.flow.server.VaadinSession;





public class LayoutView extends Composite<VerticalLayout> implements HasComponents, RouterLayout{
	private static final String PADDING = "padding";
	private static final String FRONTEND_IMAGENES_CAR_PNG = "frontend/imagenes/car.png";
	private static final String SOBRE_NOSOTROS = "Sobre nosotros";
	private static final String MIS_RESERVAS = "Mis Reservas";
	private static final String MIS_REPORTES = "Mis Reportes";
	private static final String MI_PERFIL = "Mi Perfil";
	private static final String MAIN_LOGO = "MainLogo";
	private static final String MAIN_IMG = "MainImg";
	private static final String LOGO_DE_LA_PAGINA = "Logo de la página";
	private static final String LOGGED_IN = "LoggedIn";
	private static final String LISTA = "Lista";
	private static final String LOGOUT = "/logout";
	private static final String STRING0 = "0 0 0 0";
	private static final String BUSQUEDA = "Busqueda";
	private static final String CERRAR_SESION = "Cerrar sesion";
	private static final String HEADER3 = "Header";
	private static final String HEADER22 = "Header2";
	private static final String PAGO = "Pago";
	public Header HeaderView()
	{
		this.getElement().getStyle().set(PADDING,  STRING0);
	    
      Header header=new Header();
      RouterLink LogoLink=new RouterLink("",MainView.class);
      Div header1=new Div();
      header.add(header1);
      header1.addClassName(MAIN_LOGO);
      header1.add(LogoLink);
      Div header2=new Div();
      header.add(header2);
      header2.addClassName(HEADER22);
      header.addClassName(HEADER3);
      
	  String logo = VaadinServletService.getCurrent().resolveResource(FRONTEND_IMAGENES_CAR_PNG,VaadinSession.getCurrent().getBrowser());
	  Image im=new Image(logo,LOGO_DE_LA_PAGINA);
	  im.setId(MAIN_IMG);
	  
	
	

	  RouterLink  boton4= new RouterLink(SOBRE_NOSOTROS,SobreNosotrosView.class);
	
	  
	  RouterLink  boton5= new RouterLink("Inicia sesión",SignInPage.class);
	  RouterLink boton7=new RouterLink("Registrate",SignUpAnonymous.class);

	  RouterLink  boton6= new RouterLink(BUSQUEDA,BuscadorCoches.class);

	  header2.add(boton4,boton5,boton7,boton6);
	  return header;
	}
	public Header HeaderClienteView(String username)
	{
this.getElement().getStyle().set(PADDING,  STRING0);
	    
      Header header=new Header();
      RouterLink LogoLink=new RouterLink("",MainView.class);
      Div header1=new Div();
      header.add(header1);
      header1.addClassName(MAIN_LOGO);
      header1.add(LogoLink);
      Div header2=new Div();
      header.add(header2);
      header2.addClassName(HEADER22);
      header.addClassName(HEADER3);
	  String logo = VaadinServletService.getCurrent().resolveResource(FRONTEND_IMAGENES_CAR_PNG,VaadinSession.getCurrent().getBrowser());
	  Image im=new Image(logo,LOGO_DE_LA_PAGINA);
	  im.setId(MAIN_IMG);
	
	  
	  

	  RouterLink  boton4= new RouterLink(SOBRE_NOSOTROS,SobreNosotrosView.class);
	  
	  UnorderedList LoggedIn=new UnorderedList();
	  ListItem listItem1=new ListItem();
	  RouterLink  boton5= new RouterLink();
	  boton5.setText("Bienvenid@ "+username);
	  boton5.setClassName(LOGGED_IN);
	  listItem1.add(boton5);
	  LoggedIn.add(listItem1);
	  UnorderedList LoggedInChild=new UnorderedList();
	  ListItem listItemChild1=new ListItem();
	  RouterLink c1=new RouterLink();
	  c1.setText(MI_PERFIL);
	  listItem1.add(LoggedInChild);
	  LoggedInChild.add(listItemChild1);
	  
	  RouterLink c2=new RouterLink(MIS_RESERVAS,MisReservasView.class);
	  RouterLink c3=new RouterLink();
	  Anchor c4 = new Anchor(LOGOUT, CERRAR_SESION);
	  
	  c3.setText(MIS_REPORTES);
	 
	  
	  listItemChild1.add(c1,c2,c3,c4);
	  

	  RouterLink  boton6= new RouterLink(BUSQUEDA,BuscadorCoches.class);
	  //Añadiendo al menu
	  header2.add(boton6,boton4,LoggedIn,boton6);
	  return header;
	}
	public Header HeaderGestorView(String username)
	{
this.getElement().getStyle().set(PADDING,  STRING0);
	    
      Header header=new Header();
      RouterLink LogoLink=new RouterLink("",MainView.class);
      Div header1=new Div();
      header.add(header1);
      header1.addClassName(MAIN_LOGO);
      header1.add(LogoLink);

      Div header2=new Div();
      header.add(header2);
      header2.addClassName(HEADER22);
      header.addClassName(HEADER3);
      
	  String logo = VaadinServletService.getCurrent().resolveResource(FRONTEND_IMAGENES_CAR_PNG,VaadinSession.getCurrent().getBrowser());
	  Image im=new Image(logo,LOGO_DE_LA_PAGINA);
	  im.setId(MAIN_IMG);
	 
	  

	  
	  RouterLink  boton2= new RouterLink("Reservas",MisReservasGestor.class);

	  RouterLink  boton3= new RouterLink("Vehículos",RegistrarVehiculoView.class);

	  RouterLink  boton4= new RouterLink(SOBRE_NOSOTROS,SobreNosotrosView.class);

	  RouterLink  boton7= new RouterLink("Gestión",GestionView.class);

	  UnorderedList LoggedIn=new UnorderedList();
	  ListItem listItem1=new ListItem();
	  RouterLink  boton5= new RouterLink();
	 
	  boton5.setText("Bienvenido "+username);
	  boton5.setClassName(LOGGED_IN);
	  listItem1.add(boton5);
	  LoggedIn.add(listItem1);
	  UnorderedList LoggedInChild=new UnorderedList();
	  ListItem listItemChild1=new ListItem();
	  RouterLink c1=new RouterLink();
	  c1.setText(MI_PERFIL);
	  listItem1.add(LoggedInChild);
	  LoggedInChild.add(listItemChild1);
	  
	  RouterLink c2=new RouterLink(MIS_RESERVAS,MisReservasView.class);
	  RouterLink c3=new RouterLink();
	  Anchor c4 = new Anchor(LOGOUT, CERRAR_SESION);
	  RouterLink c5=new RouterLink(PAGO,MisReservasView.class);
	  c3.setText(MIS_REPORTES);
	 
	  
	  listItemChild1.add(c1,c2,c3,c4,c5);
	  
	  
	  
	  
	  

	  RouterLink  boton6= new RouterLink(BUSQUEDA,BuscadorCoches.class);
	  //Añadiendo al menu
	  header2.add(boton2,boton6,boton3,boton4,boton7,LoggedIn);
	  return header;
	}
	public Header HeaderGerenteView(String username)
	{
this.getElement().getStyle().set(PADDING,  STRING0);
	    
      Header header=new Header();
      RouterLink LogoLink=new RouterLink("",MainView.class);
      Div header1=new Div();
      header.add(header1);
      header1.addClassName(MAIN_LOGO);
      header1.add(LogoLink);

      Div header2=new Div();
      header.add(header2);
      header2.addClassName("HeaderGerente");
      header.addClassName(HEADER3);
      
	  String logo = VaadinServletService.getCurrent().resolveResource(FRONTEND_IMAGENES_CAR_PNG,VaadinSession.getCurrent().getBrowser());
	  Image im=new Image(logo,LOGO_DE_LA_PAGINA);
	  im.setId(MAIN_IMG);
	 
	  RouterLink  boton1= new RouterLink("Monitorizar",ActividadView.class);

	  RouterLink  boton2= new RouterLink("Reservas",MisReservasGestor.class);

	

	  RouterLink  boton3= new RouterLink("Vehículos",RegistrarVehiculoView.class);

	  RouterLink  boton4= new RouterLink("Auditoria",AuditoriaView.class);

	  RouterLink  boton7= new RouterLink("Gestión",GestionView.class);

	  UnorderedList LoggedIn=new UnorderedList();
	  ListItem listItem1=new ListItem();
	  RouterLink  boton5= new RouterLink();
	  
	  boton5.setText("Bienvenido "+username);
	  boton5.setClassName(LOGGED_IN);
	  listItem1.add(boton5);
	  LoggedIn.add(listItem1);
	  UnorderedList LoggedInChild=new UnorderedList();
	  ListItem listItemChild1=new ListItem();
	  RouterLink c1=new RouterLink();
	  c1.setText(MI_PERFIL);
	  listItem1.add(LoggedInChild);
	  LoggedInChild.add(listItemChild1);
	  
	  RouterLink c2=new RouterLink(MIS_RESERVAS,MisReservasView.class);
	  RouterLink c3=new RouterLink("Registrar",SignUpView.class);
	  Anchor c4 = new Anchor(LOGOUT, CERRAR_SESION);
	  c2.setText(MIS_RESERVAS);
	  RouterLink c5=new RouterLink("Pagos",pagosView.class);
	 
	  
	  listItemChild1.add(c1,c2,c3,c5,c4);
	  
	  
	  
	  
	  

	  RouterLink  boton6= new RouterLink(BUSQUEDA,BuscadorCoches.class);
	  //Añadiendo al menu
	  header2.add(boton1,boton2,boton6,boton3,boton4,boton7,LoggedIn);
	  return header;
	}
	public Footer FooterView()
	{
		  //FOOTER
		  
	      Footer footer=new Footer();
	   	  footer.addClassName("Footer");
	   	  Div footer1=new Div();
	   	  footer.add(footer1);
	   	  footer1.setClassName("Footer1");
	   	  ListItem ejemplo4=new ListItem("Oficinas");
	   	  ListItem ejemplo3=new ListItem("Ofertas");
	   	  ListItem ejemplo=new ListItem("Tarifas");
	   	  ListItem ejemplo2=new ListItem("Autohire");
	   	  UnorderedList l1=new UnorderedList();
	   	  l1.setClassName(LISTA);
	   	  l1.add(ejemplo2);
	   	 l1.add(ejemplo3);
	   	 l1.add(ejemplo4);

	   	  l1.add(ejemplo);
	   	  footer1.add(l1);
	   	  Div footer2=new Div();
	   	  footer.add(footer2);
	   	  footer2.setClassName("Footer2");
	   	  UnorderedList l12=new UnorderedList();
	   	  ListItem ejemplo1=new ListItem("Acerca de Autohired");
	   	  ListItem ejemplo12=new ListItem("Inversores");
	   	 ListItem ejemplo123=new ListItem("Bolsa");
	   	  l12.setClassName(LISTA);
	   	  l12.add(ejemplo1);
	   	  l12.add(ejemplo12);
	 	  l12.add(ejemplo123);
	   	  footer2.add(l12);
	   	  
	   	  Div footer3=new Div();
	   	  footer.add(footer3);
	   	  footer3.setClassName("Footer3");
	 	  UnorderedList l13=new UnorderedList();
	 	  ListItem ejemplo13=new ListItem("Economía");
	 	
	 	  l13.setClassName(LISTA);
	 	  l13.add(ejemplo13);
	 	  l13.add(ejemplo123);
	 	  footer3.add(l13);
	 	  
	 	 Div footer4=new Div();
	   	  
	   	  footer4.setClassName("Footer4");
	 	  UnorderedList l4=new UnorderedList();
	 	  ListItem l41=new ListItem();
	 	  ListItem l42=new ListItem();
	 	  ListItem l43=new ListItem();
	 	  RouterLink Link41=new RouterLink();
	 	  RouterLink Link42=new RouterLink();
	 	  RouterLink Link43=new RouterLink();
	 	  
	 	  //Añadiendo logos de redes sociales
	 	  String logo4 = VaadinServletService.getCurrent().resolveResource("frontend/imagenes/facebook.png",VaadinSession.getCurrent().getBrowser());
		  Image im2=new Image(logo4,LOGO_DE_LA_PAGINA);

		  im2.setId("fb");
		  Link41.add(im2);
		  l41.add(Link41);
		  
		  String logo2 = VaadinServletService.getCurrent().resolveResource("frontend/imagenes/instagram.png",VaadinSession.getCurrent().getBrowser());
		  Image im3=new Image(logo2,LOGO_DE_LA_PAGINA);

		  im3.setId("insta");
		  Link42.add(im3);
		  l42.add(Link42);
		  
		  String logo3 = VaadinServletService.getCurrent().resolveResource("frontend/imagenes/twitter.png",VaadinSession.getCurrent().getBrowser());
		  Image im4=new Image(logo3,LOGO_DE_LA_PAGINA);

		  im4.setId("tw");
		  Link43.add(im4);
		  l43.add(Link43);
		  l4.add(l41,l42,l43);
		  footer4.add(l4);
		  footer.add(footer4);
	 	  return footer;
	}
	public Div BodyView()
	{
		  Div body=new Div();
		  body.addClassName("body");

		  return body;
	}

}
