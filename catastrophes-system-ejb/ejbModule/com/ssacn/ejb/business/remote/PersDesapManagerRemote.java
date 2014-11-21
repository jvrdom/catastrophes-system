package com.ssacn.ejb.business.remote;


import java.util.List;
import javax.ejb.Remote;
import com.ssacn.ejb.persistence.entity.PerosnaDesap;


@Remote
public interface PersDesapManagerRemote {
	
	public List<PerosnaDesap> getDesaparecidos(int idCatastrofe);
	public void createPerosnaDesap(String nombre, String apellido, String telefono, String descripcion, String status, int idUsuario, int idCatastrofe);


}
