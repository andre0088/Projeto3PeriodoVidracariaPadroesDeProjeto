package DAO;

import DTO.FuncionarioDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class AdapterFuncionario extends FuncionarioDAO_Postgres implements CrudMethods {

	@Override
	public void cadastrar(Object dto) throws DadosNaoPreenchidosException, ElementoDuplicadoException {
		super.cadastrarFuncionario((FuncionarioDTO) dto);
		
	}

	@Override
	public void editar(Object dto) throws DadosNaoPreenchidosException {
		super.editarFuncionario((FuncionarioDTO) dto);
		
	}

	@Override
	public void excluir(Object dto) {
		super.deletarFuncionario((FuncionarioDTO) dto);
		
	}

	@Override
	public Object listar() {
		return super.listarFuncionarios();
	}
	
}
