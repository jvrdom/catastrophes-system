package managedBeans;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import utilesWeb.UtilesWeb;

import com.ssacn.ejb.business.remote.CatastrofeManagerRemote;
import com.ssacn.ejb.business.remote.DonacionManagerRemote;
import com.ssacn.ejb.business.remote.OngManagerRemote;
import com.ssacn.ejb.persistence.entity.DeBienes;
import com.ssacn.ejb.persistence.entity.DeServicios;
import com.ssacn.ejb.persistence.entity.Economica;
import com.ssacn.ejb.persistence.entity.Ong;

@ManagedBean(name = "catastrofeMB")
@ViewScoped
public class CatastrofeMB {
	@EJB
	private OngManagerRemote ongM;
	@EJB
	private DonacionManagerRemote donacionM;

	private List<Ong> ongs;
	private Ong selectedOngDonacion;
	private int selectedTipoDonacion;
	private double montoDonacion;
	private String tarjetaDonacion;
	private String nroTarjetaDonacion;
	private String descripcionDonacion;
	private String tipoServicioDonacion;
	private int horasDonacion;
	private int idCatastrofe=1;

	@PostConstruct
	public void init() {
		montoDonacion = 0;
		ongs = ongM.findOngByCatastrofe(idCatastrofe);
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

	public void crearDonacionEco() {
		if (montoDonacion < 1) {
			String txt = "Debe ingresar el monto.";
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_WARN, txt, txt);
			FacesContext.getCurrentInstance().addMessage("successInfo",
					facesMsg);
		} else if (tarjetaDonacion.isEmpty()) {
			String txt = "Debe ingresar tipo de tarjeta.";
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_WARN, txt, txt);
			FacesContext.getCurrentInstance().addMessage("successInfo",
					facesMsg);
		} else if (nroTarjetaDonacion.isEmpty()) {
			String txt = "Debe ingresar el Nro de tarjeta.";
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_WARN, txt, txt);
			FacesContext.getCurrentInstance().addMessage("successInfo",
					facesMsg);
		} else {
			Economica donacion = new Economica();
			donacion.setMonto(montoDonacion);
			donacion.setTarjeta(tarjetaDonacion);
			donacion.setNroTarjeta(nroTarjetaDonacion);
			donacion.setFecha(new Date());
			donacionM.createEconmica(donacion);
			String txt = "Gracias por su colaboración!!";
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_INFO, txt, txt);
			FacesContext.getCurrentInstance().addMessage("successInfo",
					facesMsg);
			limpiarCampos();
		}
	}

	public void crearDonacionBienes() {
		if (descripcionDonacion.isEmpty()) {
			String txt = "Debe ingresar la descripción.";
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_WARN, txt, txt);
			FacesContext.getCurrentInstance().addMessage("successInfo",
					facesMsg);
		} else {
			DeBienes donacion = new DeBienes();
			donacion.setDescrpcion(descripcionDonacion);
			donacion.setFecha(new Date());
			donacionM.createDeBienes(donacion);
			String txt = "Gracias por su colaboración!!";
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_INFO, txt, txt);
			FacesContext.getCurrentInstance().addMessage("successInfo",
					facesMsg);
			limpiarCampos();
		}
	}

	public void crearDonacionServicio() {
		if (tipoServicioDonacion.isEmpty()) {
			String txt = "Debe ingresar el tipo de servicio.";
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_WARN, txt, txt);
			FacesContext.getCurrentInstance().addMessage("successInfo",
					facesMsg);
		} else if (horasDonacion < 1) {
			String txt = "Debe ingresar la cantidad de horas.";
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_WARN, txt, txt);
			FacesContext.getCurrentInstance().addMessage("successInfo",
					facesMsg);
		} else {
			DeServicios donacion = new DeServicios();
			donacion.setTipoDeServicio(tipoServicioDonacion);
			donacion.setHorasDedicadas(horasDonacion);
			donacion.setFecha(new Date());
			donacionM.createDeservicios(donacion);
			String txt = "Gracias por su colaboración!!";
			FacesMessage facesMsg = new FacesMessage(
					FacesMessage.SEVERITY_INFO, txt, txt);
			FacesContext.getCurrentInstance().addMessage("successInfo",
					facesMsg);
			limpiarCampos();
		}
	}

	public void limpiarCampos() {
		selectedOngDonacion = null;
		montoDonacion = 0;
		tarjetaDonacion = "";
		nroTarjetaDonacion = "";
		descripcionDonacion = "";
		tipoServicioDonacion = "";
		horasDonacion = 0;
	}

}
