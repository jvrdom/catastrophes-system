package com.ssacn.ejb.business.remote;

import java.util.List;

import javax.ejb.Remote;

import com.ssacn.ejb.persistence.entity.Ong;

@Remote
public interface OngManagerRemote {
	public void create(Ong ong);
	public void edit(Ong ong);
	public List<Ong> getOngs();
	public List<Ong> findOngByCatastrofe(int idCatastrofe);
	public void delete(int id);

}
