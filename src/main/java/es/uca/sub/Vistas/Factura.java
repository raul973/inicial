package es.uca.sub.Vistas;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import es.uca.sub.Repositorios.Reserva.Reserva;
import es.uca.sub.Repositorios.Usuario.Usuario;
import es.uca.sub.Repositorios.Vehiculo.Vehiculo;

@WebServlet("/DownloadFileServlet")
public class Factura implements Serializable {
	
	private static final String AUTOHIRED = "Autohired";
	private static final String DD_MM_YYYY = "dd/MM/yyyy";
	transient Document documento;
	int cargo;
	byte [] fichero;
	String FILE = System.getProperty("user.home")+"/Downloads/Factura.pdf";
	
	public byte [] getFichero() {
		return fichero;
	}
	public Factura(int precio,Usuario user) {
		 Document document = new Document();
		this.cargo=precio;
		PdfWriter escritor=null;
		try {
			escritor=PdfWriter.getInstance(document, new FileOutputStream(FILE));
		}catch(Exception e) {
			e.printStackTrace();
		}
		 document.open();
		 addMetaData(document);
		 fichero=document.toString().getBytes();
		 try {
				addTitlePage(document,user,precio);
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 
	      document.close();
	}
	
	public Factura(Reserva item,Vehiculo coche,Usuario user) {
		
        Document document = new Document();
        try {
			PdfWriter.getInstance(document, new FileOutputStream(FILE));
		} catch (Exception e1) {

			e1.printStackTrace();
		} 
        document.open();
        
        addMetaData(document);
        try {
			addTitlePage(document,coche,user,item);
		} catch (Exception e) {
			e.printStackTrace();
		}
        document.close();
	}
	
	private  void addMetaData(Document document) {
        document.addTitle("Factura");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor(AUTOHIRED);
        document.addCreator("Alejandro Díaz Sadoc");
    }
	 private  void addTitlePage(Document document,Usuario user,int precio)
	            throws DocumentException {
		 
	        Paragraph preface = new Paragraph();
	        // We add one empty line
	        
	        
	        Chunk glue = new Chunk(new VerticalPositionMark());
	        Paragraph p = new Paragraph(AUTOHIRED);
	        p.add(new Chunk(glue));
	        
	        Image image=null;
	        try {
				 image = Image.getInstance("classes/META-INF/resources/frontend/imagenes/Logo.jpeg");
				 image.scaleToFit(100, 100);
				 image.setAlignment(Element.ALIGN_RIGHT);
			} catch (Exception e) {

				e.printStackTrace();
			}
	        
	        
	       
	        p.add(image);
	        preface.add(p);
	        
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DD_MM_YYYY);
	    	LocalDate localDate = LocalDate.now();
	    	
	  
	        Paragraph FacturaNumber=new Paragraph("Factura Reserva");
	        FacturaNumber.add(new Chunk(glue));
	        FacturaNumber.add(dtf.format(localDate));
	        
	        
	        PdfPTable t=new PdfPTable(1);
	        t.setWidthPercentage(100);
	        
	        PdfPCell celda = new PdfPCell(new Phrase("Datos usuario:"));
	        PdfPCell celda2 = new PdfPCell(new Phrase("Nombre: "+user.getNombre()));
	        PdfPCell celda3 = new PdfPCell(new Phrase("Apellidos: "+user.getApellidos()));
	        
	        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
	        celda2.setBorderWidthBottom(0);
	        celda2.setBorderWidthTop(0);
	        
	        celda3.setBorderWidthTop(0);
	        t.addCell(celda);
	        t.setHeaderRows(1);
	        t.addCell(celda2);
	        t.addCell(celda3);
	       
	        
	        PdfPTable table = new PdfPTable(2);

	        table.setWidthPercentage(100);
	        PdfPCell c1 = new PdfPCell(new Phrase("Factura Unitaria"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        
	     
	        c1 = new PdfPCell(new Phrase("Importe"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(c1);
	        table.setHeaderRows(1);
	        
	        c1 = new PdfPCell(new Phrase(Integer.toString(precio)+" €"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBorderWidthRight(0);
	        c1.setBorderWidthTop(0);
	        table.addCell(c1);
	        c1 = new PdfPCell(new Phrase(Integer.toString(precio)+" €"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c1.setBorderWidthRight(0);
	        c1.setBorderWidthTop(0);
	        c1.setBorderWidthLeft(0);
	        table.addCell(c1);
	        document.add(preface);
	        
	        addEmptyLine(document, 2);
	        document.add(FacturaNumber);
	        addLine(document);
	        addEmptyLine(document, 2);
	        addLine(document);
	        addEmptyLine(document, 2);
	        document.add(t);
	        addEmptyLine(document, 2);
	        document.add(table);
	        
	        addEmptyLine(document, 2);
	        
	        Paragraph footer=new Paragraph();
	        PdfPTable Observaciones=new PdfPTable(1);
	        Observaciones.setWidthPercentage(25);
	        c1 = new PdfPCell(new Phrase("Observaciones"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        Observaciones.addCell(c1);
	        Observaciones.setHeaderRows(1);
	        c1 = new PdfPCell(new Phrase("Contenido de Observaciones"));
	        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
	        Observaciones.addCell(c1);
	     
	      
	        document.add(footer);  
	    }
    private  void addTitlePage(Document document,Vehiculo coche,Usuario user,Reserva item)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        
        
        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph p = new Paragraph(AUTOHIRED);
        p.add(new Chunk(glue));
        
        Image image=null;
        try {
			 image = Image.getInstance("src/main/webapp/frontend/imagenes/Logo.jpeg");
			 image.scaleToFit(100, 100);
			 image.setAlignment(Element.ALIGN_RIGHT);
		} catch (Exception e) {

			e.printStackTrace();
		} 
        
      
        p.add(image);
        preface.add(p);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DD_MM_YYYY);
    	LocalDate localDate = LocalDate.now();
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
    	 
		Date fechaInicial;
		fechaInicial = item.getFecha_inicio();
		Date fechaFinal;
			fechaFinal = item.getFecha_fin();
		

		int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
		Integer subtotal=dias*coche.getPrecio();
		
    	
    	
    	
        Paragraph FacturaNumber=new Paragraph("Factura Reserva");
        FacturaNumber.add(new Chunk(glue));
        FacturaNumber.add(dtf.format(localDate));
        
        
        PdfPTable t=new PdfPTable(1);
        t.setWidthPercentage(100);
        
        PdfPCell celda = new PdfPCell(new Phrase("Datos usuario:"));
        PdfPCell celda2 = new PdfPCell(new Phrase("Nombre: "+user.getNombre()));
        PdfPCell celda3 = new PdfPCell(new Phrase("Apellidos: "+user.getApellidos()));
        
        celda.setHorizontalAlignment(Element.ALIGN_LEFT);
        celda2.setBorderWidthBottom(0);
        celda2.setBorderWidthTop(0);
        
        celda3.setBorderWidthTop(0);
        t.addCell(celda);
        t.setHeaderRows(1);
        t.addCell(celda2);
        t.addCell(celda3);
       
        
        PdfPTable table = new PdfPTable(6);


        
        table.setWidthPercentage(100);
        PdfPCell c1 = new PdfPCell(new Phrase("Marca de Coche"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Modelo de coche"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Precio"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Fecha de Entrega"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Fecha de devolucion"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Importe"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);
        
        c1 = new PdfPCell(new Phrase(coche.getMarca()));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(coche.getModelo()));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthTop(0);
        c1.setBorderWidthLeft(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(coche.getPrecio().toString()+"€/día"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(dateFormat.format(fechaInicial)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(dateFormat.format(fechaFinal)));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(subtotal.toString()+" €"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthBottom(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
       
        

        c1 = new PdfPCell(new Phrase(""));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(""));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthTop(0);
        c1.setBorderWidthLeft(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(""));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Seguro:"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(item.getSeguro()));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        int seguro=0;
        if(item.getSeguro().equals("Premium")){
          	 c1 = new PdfPCell(new Phrase("100"+" €"));
          	 seguro=100;
        }
        if(item.getSeguro().equals("Normal")){
        	 c1 = new PdfPCell(new Phrase("50"+" €"));
        	 seguro=50;
        }
        if(item.getSeguro().equals("Sin seguro")){
        	 c1 = new PdfPCell(new Phrase(""));
        }
       
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthBottom(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
       
        
        c1 = new PdfPCell(new Phrase(""));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(""));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthTop(0);
        c1.setBorderWidthLeft(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(""));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(""));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("IVA"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
        int redondeo=Math.round(item.getImporte());
        int importe=redondeo+(int)Math.round(item.getImporte()*0.21)+seguro;
        c1 = new PdfPCell(new Phrase(Integer.toString((int)Math.round(item.getImporte()*0.21))));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthBottom(0);
        c1.setBorderWidthTop(0);
        table.addCell(c1);
       
        
        RellenarTabla(table);
        c1 = new PdfPCell(new Phrase("Subtotal"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthLeft(0);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase(importe+" €"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthLeft(0);
        table.addCell(c1);
       
        
        document.add(preface);
        
        addEmptyLine(document, 2);
        document.add(FacturaNumber);
        addLine(document);
        addEmptyLine(document, 2);
        addLine(document);
        addEmptyLine(document, 2);
        document.add(t);
        addEmptyLine(document, 2);
        document.add(table);
        
        addEmptyLine(document, 2);
        
        Paragraph footer=new Paragraph();
        PdfPTable Observaciones=new PdfPTable(1);
        Observaciones.setWidthPercentage(25);
        c1 = new PdfPCell(new Phrase("Observaciones"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        Observaciones.addCell(c1);
        Observaciones.setHeaderRows(1);
        c1 = new PdfPCell(new Phrase("Contenido de Observaciones"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        Observaciones.addCell(c1);
        
        PdfPTable ImporteTotal=new PdfPTable(2);
        ImporteTotal.setWidthPercentage(25);
        c1 = new PdfPCell(new Phrase("Total:"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthBottom(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthTop(0);
        ImporteTotal.addCell(c1);
        c1 = new PdfPCell(new Phrase(importe+" €"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        ImporteTotal.addCell(c1);
        ImporteTotal.setHeaderRows(1);
        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthBottom(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthTop(0);
        ImporteTotal.addCell(c1);
        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorderWidthBottom(0);
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthRight(0);
        c1.setBorderWidthTop(0);
        ImporteTotal.addCell(c1);
        footer.add(Observaciones);
        footer.add(new Chunk(glue));
        footer.add(ImporteTotal);
        
        document.add(footer);  
    }
    public void RellenarTabla(PdfPTable table) {
    	for(int i=0;i<96;i++) {
    		PdfPCell c1 = new PdfPCell(new Phrase(" "));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorderWidthTop(0);
            c1.setBorderWidthBottom(0);
            if(i%6==0) {
            	c1.setBorderWidthRight(0);
            }else if(i%6!=5) {
            	
            
            	c1.setBorderWidthRight(0);
            	c1.setBorderWidthLeft(0);
            }
            table.addCell(c1);
    		
    	}
    	
    }
    private  void addEmptyLine(Document document,int number) {
        for (int i = 0; i < number; i++) {
            try {
				document.add(new Paragraph(" "));
			} catch (DocumentException e) {

				e.printStackTrace();
			}
        }
    }
    private  void addLine(Document document) {
    	try {
			document.add(new LineSeparator());
		} catch (DocumentException e) {

			e.printStackTrace();
		}
    }
    
	

}
