package DAO;

import DTO.ClienteDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class AdapterCliente extends ClienteDAO_Postgres implements CrudMethods{

	@Override
	public void cadastrar(Object dto) throws ElementoDuplicadoException, DadosNaoPreenchidosException  {
		super.cadastrarCliente((ClienteDTO) dto);
		
	}

	@Override
	public void editar(Object dto) throws DadosNaoPreenchidosException  {
		super.editarCliente((ClienteDTO) dto);
		
	}

	@Override
	public void excluir(Object dto)  {
		super.deletarCliente((ClienteDTO) dto);
		
	}

	@Override
	public Object listar() {
		return super.listarClientes();
	}
	
}
