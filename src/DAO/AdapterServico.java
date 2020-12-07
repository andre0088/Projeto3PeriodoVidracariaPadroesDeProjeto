package DAO;

import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class AdapterServico extends ServicoDAO_Postegres implements CrudMethods{

	@Override
	public void cadastrar(Object dto) throws DadosNaoPreenchidosException, ElementoDuplicadoException  {
		super.cadastrarServico((ServicoDTO) dto);
		
	}

	@Override
	public void editar(Object dto) throws DadosNaoPreenchidosException  {
		super.editarServico((ServicoDTO) dto);
		
	}

	@Override
	public void excluir(Object dto) {
		super.deletarServico((ServicoDTO) dto);
		
	}

	@Override
	public Object listar() {
		return super.listarServicos();
	}
	
}
