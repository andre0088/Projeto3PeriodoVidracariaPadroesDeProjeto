package DTO;

import java.util.ArrayList;

import Model.Pessoa;

public class ClienteDTO extends Pessoa {
	
	private ArrayList<ClienteDTO> listaClientes = new ArrayList<ClienteDTO>();

	public ArrayList<ClienteDTO> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(ArrayList<ClienteDTO> listaClientes) {
		this.listaClientes = listaClientes;
	}

}
