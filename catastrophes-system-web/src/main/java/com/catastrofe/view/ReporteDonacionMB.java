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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import org.omnifaces.cdi.ViewScoped;

import com.catastrofe.dao.DonacionDao;
import com.catastrofe.model.Donacion;
import com.catastrofe.model.PedidoAyuda;

@Named
@ViewScoped
public class ReporteDonacionMB implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fechaDonacionInicial;
	private String fechaDonacionFinal;
	private List<Donacion> donaciones;
	
	@EJB
	private DonacionDao donacionesDao;
	
	@PostConstruct
	public void init(){
		try {
			
            //SimpleDateFormat formatoDelTexto = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            //Date nuevo = (Date) formatoDelTexto.parse(fechaDonacionInicial);
            Date nuevo = new Date();
            SimpleDateFormat otroFormato = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String fecha=otroFormato.format(nuevo);				
			System.out.println("****findByDate");
			Date date = otroFormato.parse(fecha);
			donaciones=donacionesDao.findByDate(date);
	 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}


	public String getFechaDonacionInicial() {
		return fechaDonacionInicial;
	}


	public void setFechaDonacionInicial(String fechaDonacionInicial) {
		this.fechaDonacionInicial = fechaDonacionInicial;
	}


	public String getFechaDonacionFinal() {
		return fechaDonacionFinal;
	}


	public void setFechaDonacionFinal(String fechaDonacionFinal) {
		this.fechaDonacionFinal = fechaDonacionFinal;
	}


	public List<Donacion> getDonaciones() {
		return donaciones;
	}


	public void setDonaciones(List<Donacion> donaciones) {
		this.donaciones = donaciones;
	}

	
	public void buscarDonaciones(){
		System.out.println("inicia buscar donaciones");
		if(fechaDonacionInicial.isEmpty()){
			System.out.println("****listAll");
			donaciones=donacionesDao.listAll(null, null);
			
		}else if(fechaDonacionFinal.isEmpty()){
			try {
				
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date nuevo = (Date) formatoDelTexto.parse(fechaDonacionInicial);
                SimpleDateFormat otroFormato = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                String fecha=otroFormato.format(nuevo);				
				System.out.println("****findByDate");
				Date date = otroFormato.parse(fecha);
				donaciones=donacionesDao.findByDate(date);
		 
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}else{

			try {
				
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                SimpleDateFormat otroFormato = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                
                Date nuevoInicial = (Date) formatoDelTexto.parse(fechaDonacionInicial);                
                String fechaI=otroFormato.format(nuevoInicial);		
               
                Date nuevoFinal = (Date) formatoDelTexto.parse(fechaDonacionFinal);
                String fechaF=otroFormato.format(nuevoFinal);	
		
				System.out.println("****findbetweenDate");
				Date dateInicial = otroFormato.parse(fechaI);
				Date dateFinal = otroFormato.parse(fechaF);
				donaciones=donacionesDao.findbetweenDate(dateInicial,dateFinal);
		 
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		if(donaciones!=null){
		System.out.println("cantidad de donaciones encontradas "+donaciones.size());
		}else{
			System.out.println("donaciones es null");
		}
	}
	
	public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
 
        for (Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellValue(cell.getStringCellValue().toUpperCase());
                cell.setCellStyle(style);
            }
        }
    }
	
	
}
