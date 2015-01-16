package com.catastrofe.view;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import com.catastrofe.dao.Audit_LoginDao;
import com.catastrofe.model.Audit_Login;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@Named
@ViewScoped
public class ReporteUsoMB implements Serializable{
	
	
	private List<Audit_Login> listaUsuarios;
	private List<Audit_Login> listaAdmin;
	private List<Audit_Login> listaRescatistas;
	private LineChartModel lineModel;
	@EJB
	private Audit_LoginDao auditDao;
	
	@PostConstruct
	public void init(){
		listaUsuarios=auditDao.listAllByRol("usuario");
		listaAdmin=auditDao.listAllByRol("administrador");
		listaRescatistas=auditDao.listAllByRol("rescatista");
		System.out.println("cantidad de Audit usuarios:"+listaUsuarios.size());
		createLineModel();
	}
	

	public List<Audit_Login> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Audit_Login> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Audit_Login> getListaAdmin() {
		return listaAdmin;
	}

	public void setListaAdmin(List<Audit_Login> listaAdmin) {
		this.listaAdmin = listaAdmin;
	}

	public List<Audit_Login> getListaRescatistas() {
		return listaRescatistas;
	}

	public void setListaRescatistas(List<Audit_Login> listaRescatistas) {
		this.listaRescatistas = listaRescatistas;
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}
	
	private void createLineModel() {
		System.out.println("en createLineModel");
		lineModel = initCategoryModel();
        lineModel.setTitle("Grafica de uso del sitio");
        lineModel.setLegendPosition("e");
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Mes"));
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Ingreso");
        yAxis.setMin(0);
        yAxis.setMax(200);
		
	}
	
	private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries usuarios = new ChartSeries();
        usuarios.setLabel("Usuarios");
    	//DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy"); 
    	int mes1=0,mes2=0,mes3=0,mes4=0,mes5=0,mes6=0,mes7=0,mes8=0,mes9=0,mes10=0,mes11=0,mes12=0;
        for(Audit_Login audit:listaUsuarios){
        	Calendar cal = Calendar.getInstance();
    		cal.setTime(audit.getFecha());
        	Date hoy=new Date();
        	Calendar cal2 = Calendar.getInstance();
    		cal.setTime(hoy);    		
        	if(cal.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)){       		        		
        		int month = cal.get(Calendar.MONTH);
        		switch(month){
        		case 0:
        			mes1+=1;
        			break;
        		case 1:
        			mes2+=1;
        			break;
        		case 2:
        			mes3+=1;
        			break;
        		case 3:
        			mes4+=1;
        			break;
        		case 4:
        			mes5+=1;
        			break;
        		case 5:
        			mes6+=1;
        			break;
        		case 6:
        			mes7+=1;
        			break;
        		case 7:
        			mes8+=1;
        			break;
        		case 8:
        			mes9+=1;
        			break;
        		case 9:
        			mes10+=1;
        			break;
        		case 10:
        			mes11+=1;
        			break;
        		case 11:
        			mes12+=1;
        			break;
        		}        	
        	
        	}
        	
        }
        System.out.println("valor de mes 01-"+mes1);
        usuarios.set("01", mes1);
        usuarios.set("02", mes2);
        usuarios.set("03", mes3);
        usuarios.set("04", mes4);
        usuarios.set("05", mes5);
        usuarios.set("06", mes6);
        usuarios.set("07", mes7);
        usuarios.set("08", mes8);
        usuarios.set("09", mes9);
        usuarios.set("10", mes10);
        usuarios.set("11", mes11);
        usuarios.set("12", mes12);
 
        ChartSeries administradores = new ChartSeries();
        administradores.setLabel("Administradores");
        mes1=0;mes2=0;mes3=0;mes4=0;mes5=0;mes6=0;mes7=0;mes8=0;mes9=0;mes10=0;mes11=0;mes12=0;
        for(Audit_Login audit:listaAdmin){
        	Calendar cal = Calendar.getInstance();
    		cal.setTime(audit.getFecha());
        	Date hoy=new Date();
        	Calendar cal2 = Calendar.getInstance();
    		cal.setTime(hoy);    		
        	if(cal.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)){ 
        		int month = cal.get(Calendar.MONTH);
        		switch(month){
        		case 0:
        			mes1+=1;
        			break;
        		case 1:
        			mes2+=1;
        			break;
        		case 2:
        			mes3+=1;
        			break;
        		case 3:
        			mes4+=1;
        			break;
        		case 4:
        			mes5+=1;
        			break;
        		case 5:
        			mes6+=1;
        			break;
        		case 6:
        			mes7+=1;
        			break;
        		case 7:
        			mes8+=1;
        			break;
        		case 8:
        			mes9+=1;
        			break;
        		case 9:
        			mes10+=1;
        			break;
        		case 10:
        			mes11+=1;
        			break;
        		case 11:
        			mes12+=1;
        			break;
        		}        	
        	
        	}
        	
        }
        administradores.set("01", mes1);
        administradores.set("02", mes2);
        administradores.set("03", mes3);
        administradores.set("04", mes4);
        administradores.set("05", mes5);
        administradores.set("06", mes6);
        administradores.set("07", mes7);
        administradores.set("08", mes8);
        administradores.set("09", mes9);
        administradores.set("10", mes10);
        administradores.set("11", mes11);
        administradores.set("12", mes12);
        
        ChartSeries rescatistas = new ChartSeries();
        rescatistas.setLabel("Rescatistas");
        mes1=0;mes2=0;mes3=0;mes4=0;mes5=0;mes6=0;mes7=0;mes8=0;mes9=0;mes10=0;mes11=0;mes12=0;
        for(Audit_Login audit:listaRescatistas){
        	Calendar cal = Calendar.getInstance();
    		cal.setTime(audit.getFecha());
        	Date hoy=new Date();
        	Calendar cal2 = Calendar.getInstance();
    		cal.setTime(hoy);    		
        	if(cal.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)){ 
        		int month = cal.get(Calendar.MONTH);
        		switch(month){
        		case 0:
        			mes1+=1;
        			break;
        		case 1:
        			mes2+=1;
        			break;
        		case 2:
        			mes3+=1;
        			break;
        		case 3:
        			mes4+=1;
        			break;
        		case 4:
        			mes5+=1;
        			break;
        		case 5:
        			mes6+=1;
        			break;
        		case 6:
        			mes7+=1;
        			break;
        		case 7:
        			mes8+=1;
        			break;
        		case 8:
        			mes9+=1;
        			break;
        		case 9:
        			mes10+=1;
        			break;
        		case 10:
        			mes11+=1;
        			break;
        		case 11:
        			mes12+=1;
        			break;
        		}        	
        	
        	}
        	
        }
        rescatistas.set("01", mes1);
        rescatistas.set("02", mes2);
        rescatistas.set("03", mes3);
        rescatistas.set("04", mes4);
        rescatistas.set("05", mes5);
        rescatistas.set("06", mes6);
        rescatistas.set("07", mes7);
        rescatistas.set("08", mes8);
        rescatistas.set("09", mes9);
        rescatistas.set("10", mes10);
        rescatistas.set("11", mes11);
        rescatistas.set("12", mes12);
 
        model.addSeries(usuarios);
        model.addSeries(administradores);
        model.addSeries(rescatistas);
        
        return model;
    }
 
	
	
	
}
