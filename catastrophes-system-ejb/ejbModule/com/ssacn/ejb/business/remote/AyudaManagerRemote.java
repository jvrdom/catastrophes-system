package com.ssacn.ejb.business.remote;

import javax.ejb.Remote;

import com.ssacn.ejb.persistence.entity.PedidoAyuda;

@Remote
public interface AyudaManagerRemote {
	public void create(PedidoAyuda pedido);
}
