package com.ssacn.ejb.util;

import com.ssacn.ejb.bean.Sexo;

public class UserUtiles {
	
	public UserUtiles() {
		
	}
	
	public Sexo getSexo(String sexo){
		if (sexo.equalsIgnoreCase(Sexo.Masculino.toString())){
			return Sexo.Masculino;
		} else {
			return Sexo.Femenino;
		}
	}
}
