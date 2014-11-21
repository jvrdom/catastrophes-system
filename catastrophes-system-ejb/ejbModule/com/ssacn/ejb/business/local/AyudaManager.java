package com.ssacn.ejb.business.local;

import com.ssacn.ejb.business.remote.AyudaManagerRemote;

import com.ssacn.ejb.persistence.entity.PedidoAyuda;
import com.ssacn.ejb.persistence.jpaController.JpaAyudaController;

public class AyudaManager  implements AyudaManagerRemote{
	private JpaAyudaController ayudaController;
	
    /**
     * Default constructor. 
     */
    public AyudaManager() {
    	ayudaController = new JpaAyudaController();

    }
    
	@Override
	public void create(PedidoAyuda pedido) {
		ayudaController.create(pedido);
	}

}
