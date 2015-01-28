package com.catastrofe.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.catastrofe.dao.DonacionDao;
import com.catastrofe.dao.OngDao;
import com.catastrofe.model.Catastrofe;
import com.catastrofe.model.Donacion;
import com.catastrofe.model.Ong;
import com.catastrofe.model.Usuario;
import com.catastrofe.model.tipoDonacion;

@Named
@SessionScoped
public class DonacionesMB implements Serializable {
	
	@Inject
	private OngDao ongDao;
	@Inject
	private DonacionDao donacionDao;

	private List<Ong> ongs;
	private Ong selectedOngDonacion;
	private int selectedTipoDonacion;
	private double montoDonacion;
	private String tarjetaDonacion;
	private String nroTarjetaDonacion;
	private String descripcionDonacion;
	private String tipoServicioDonacion;
	private int horasDonacion;
	private Usuario usuario;
	private Catastrofe catastrofe;
	private String selectedOngMail;
	private String estilo;
	
	@PostConstruct
	public void init(){
		try{
			System.out.println("*******PostConstruct");
			usuario=(Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			catastrofe=(Catastrofe) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("catastrofe");
			estilo=catastrofe.getCss();
			ongs=ongDao.findOngByCatastrofe(catastrofe.getId());
			horasDonacion=0;
			montoDonacion=0;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public String getSelectedOngMail() {
		return selectedOngMail;
	}


	public void setSelectedOngMail(String selectedOngMail) {
		this.selectedOngMail = selectedOngMail;
	}


	public DonacionDao getDonacionDao() {
		return donacionDao;
	}

	public void setDonacionDao(DonacionDao donacionDao) {
		this.donacionDao = donacionDao;
	}

	public List<Ong> getOngs() {
		return ongs;
	}

	public void setOngs(List<Ong> ongs) {
		this.ongs = ongs;
	}

	public Ong getSelectedOngDonacion() {
		return selectedOngDonacion;
	}

	public void setSelectedOngDonacion(Ong selectedOngDonacion) {
		this.selectedOngDonacion = selectedOngDonacion;
	}

	public int getSelectedTipoDonacion() {
		return selectedTipoDonacion;
	}

	public void setSelectedTipoDonacion(int selectedTipoDonacion) {
		this.selectedTipoDonacion = selectedTipoDonacion;
	}

	public double getMontoDonacion() {
		return montoDonacion;
	}

	public void setMontoDonacion(double montoDonacion) {
		this.montoDonacion = montoDonacion;
	}

	public String getTarjetaDonacion() {
		return tarjetaDonacion;
	}

	public void setTarjetaDonacion(String tarjetaDonacion) {
		this.tarjetaDonacion = tarjetaDonacion;
	}

	public String getNroTarjetaDonacion() {
		return nroTarjetaDonacion;
	}

	public void setNroTarjetaDonacion(String nroTarjetaDonacion) {
		this.nroTarjetaDonacion = nroTarjetaDonacion;
	}

	public String getDescripcionDonacion() {
		return descripcionDonacion;
	}

	public void setDescripcionDonacion(String descripcionDonacion) {
		this.descripcionDonacion = descripcionDonacion;
	}

	public String getTipoServicioDonacion() {
		return tipoServicioDonacion;
	}

	public void setTipoServicioDonacion(String tipoServicioDonacion) {
		this.tipoServicioDonacion = tipoServicioDonacion;
	}

	public int getHorasDonacion() {
		return horasDonacion;
	}

	public void setHorasDonacion(int horasDonacion) {
		this.horasDonacion = horasDonacion;
	}
	
	
	public String getEstilo() {
		return estilo;
	}


	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}


	public void crearDonacionEco(){
		if(selectedOngDonacion==null){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "Debe seleccionar una ong destinataria."));
		}else if(montoDonacion<=0){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "El valor del campo Monto debe ser mayor a 0."));
		}else if(tarjetaDonacion.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "Debe ingresar el tipo de tarjeta a utilizar."));
		}else if(nroTarjetaDonacion.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "Debe ingresar su número de tarjeta"));
		}else{
			Donacion donacion=new Donacion();
			donacion.setTipo(tipoDonacion.Económica);
			donacion.setMonto(montoDonacion);
			donacion.setTarjeta(tarjetaDonacion);
			donacion.setNroTarjeta(nroTarjetaDonacion);		
			donacion.setOng(selectedOngDonacion);
			donacion.setFecha(new Date());
			donacion.setUsuario(usuario);
			donacionDao.create(donacion);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Donación efectuada", "Gracias por colaborar!"));
			limpiarCamposDonacion();
			
		}
	}

	public void limpiarCamposDonacion() {
		selectedOngDonacion=null;
		montoDonacion=0;
		tarjetaDonacion="";
		nroTarjetaDonacion="";
		descripcionDonacion="";
		tipoServicioDonacion="";
		horasDonacion=0;
		
	}
	
	
    public Converter getOngConverter() {
        return new Converter() {
            @Override
            public Object getAsObject(FacesContext context, UIComponent component, String value) {
                if (value.trim().equals("")) {
                    return null;
                } else {
                    try {
                        long number = Long.parseLong(value);

                        for (Ong u : ongs) {
                            if (u.getId() == number) {
                                return u;
                            }
                        }

                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Ong"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            public String getAsString(FacesContext context, UIComponent component, Object value) {
                if (value == null || value.equals("")) {
                    return "";
                } else {
                    return String.valueOf(((Ong) value).getId());
                }

            }

        };
    }
    
    public void crearDonacionBienes(){
    	if(selectedOngDonacion==null){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "Debe seleccionar una ong destinataria."));
		}else if(descripcionDonacion.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "Debe ingresar una descripción de los bienes a donar."));
		}else{
			Donacion donacion=new Donacion();
			donacion.setTipo(tipoDonacion.DeBienes);
			donacion.setDescripcion(descripcionDonacion);			
			donacion.setOng(selectedOngDonacion);
			donacion.setFecha(new Date());
			donacion.setUsuario(usuario);
			donacionDao.create(donacion);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Donación efectuada", "Gracias por colaborar!"));
			limpiarCamposDonacion();
		}
    }
    
    public void crearDonacionServicio(){
    	if(selectedOngDonacion==null){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "Debe seleccionar una ong destinataria."));
		}else if(tipoServicioDonacion.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "Debe ingresar un valor para el campo tipo de servicio."));
		}else if(horasDonacion<=0){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "Debe ingresar la cantidad de horas dedicadas."));
		}else{
			Donacion donacion=new Donacion();
			donacion.setTipo(tipoDonacion.DeServicios);
			donacion.setTipoDeServicio(tipoServicioDonacion);
			donacion.setHorasDedicadas(horasDonacion);
			donacion.setOng(selectedOngDonacion);
			donacion.setFecha(new Date());
			donacion.setUsuario(usuario);
			donacionDao.create(donacion);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Donación efectuada", "Gracias por colaborar!"));
			limpiarCamposDonacion();
		}
    }
    
    public void volver(){
    	try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ "/faces/catastrofe/view.xhtml?id="
									+ catastrofe.getId());
		} catch (IOException e) {
			e.printStackTrace();				
		}
    }
	
    public void paypal(){
    	try {
    		if(selectedOngDonacion==null){
    			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Datos Incompletos", "Debe seleccionar una ong destinataria."));
    		}else{
    			selectedOngMail=selectedOngDonacion.getPaypalMail();
    			FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.redirect(
						FacesContext.getCurrentInstance()
								.getExternalContext()
								.getRequestContextPath()
								+ "/faces/donaciones/paypal.xhtml");
    		}
			
		} catch (IOException e) {
			e.printStackTrace();				
		}
    }

}
