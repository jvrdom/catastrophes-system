package com.catastrofe.view;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.catastrofe.dao.PedidoAyudaDao;
import com.catastrofe.model.Donacion;
import com.catastrofe.model.PedidoAyuda;

@Named
@ViewScoped
public class ReportePedidoAyudaMB implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fechaPedidoInicial;
	private String fechaPedidoFinal;
	private List<PedidoAyuda> pedidos;
	
	@EJB
	private PedidoAyudaDao pedidoAyudaDao;
	
	@PostConstruct
	public void init(){
		try {

            Date nuevo = new Date();
            SimpleDateFormat otroFormato = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String fecha=otroFormato.format(nuevo);				
			System.out.println("****findByDate");
			Date date = otroFormato.parse(fecha);
			pedidos=pedidoAyudaDao.findByDate(date);
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getFechaPedidoInicial() {
		return fechaPedidoInicial;
	}
	public void setFechaPedidoInicial(String fechaPedidoInicial) {
		this.fechaPedidoInicial = fechaPedidoInicial;
	}
	public String getFechaPedidoFinal() {
		return fechaPedidoFinal;
	}
	public void setFechaPedidoFinal(String fechaPedidoFinal) {
		this.fechaPedidoFinal = fechaPedidoFinal;
	}
	public List<PedidoAyuda> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<PedidoAyuda> pedidos) {
		this.pedidos = pedidos;
	}
	
	public void buscarSolicitudes(){
		System.out.println("inicia buscar donaciones");
		if(fechaPedidoInicial.isEmpty()){
			System.out.println("****listAll");
			pedidos=pedidoAyudaDao.listAll(null, null);
			
		}else if(fechaPedidoFinal.isEmpty()){
			try {
				
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date nuevo = (Date) formatoDelTexto.parse(fechaPedidoInicial);
                SimpleDateFormat otroFormato = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                String fecha=otroFormato.format(nuevo);				
				System.out.println("****findByDate");
				Date date = otroFormato.parse(fecha);
				pedidos=pedidoAyudaDao.findByDate(date);
		 
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}else{

			try {
				
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                SimpleDateFormat otroFormato = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                
                Date nuevoInicial = (Date) formatoDelTexto.parse(fechaPedidoInicial);                
                String fechaI=otroFormato.format(nuevoInicial);		
               
                Date nuevoFinal = (Date) formatoDelTexto.parse(fechaPedidoFinal);
                String fechaF=otroFormato.format(nuevoFinal);	
		
				System.out.println("****findbetweenDate");
				Date dateInicial = otroFormato.parse(fechaI);
				Date dateFinal = otroFormato.parse(fechaF);
				pedidos=pedidoAyudaDao.findbetweenDate(dateInicial,dateFinal);
		 
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}		
	}

}
