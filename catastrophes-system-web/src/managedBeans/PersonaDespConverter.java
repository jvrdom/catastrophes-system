package managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.ssacn.ejb.business.remote.PersDesapManagerRemote;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;

@FacesConverter("personDesConverter")
public class PersonaDespConverter implements Converter{
	
	@EJB
	private PersDesapManagerRemote personaDesM;
	
	public static List<PerosnaDesap> listadoPersonasDes;
	
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
            	
            	listadoPersonasDes = personaDesM.getDesaparecidos(51);
            	
            	int number = Integer.parseInt(value);
            	
            	for (PerosnaDesap p : listadoPersonasDes) {
            		if (p.getPerosnaDesapId() == number) {
            			return p;
            		}
            	}
                
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        
		return null;
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((PerosnaDesap) object).getPerosnaDesapId());
        }
        else {
            return null;
        }
    }   
}
