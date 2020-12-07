package Controller;

import DTO.ServicoDTO;
import Model.Servico;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class ControllerServico{
	
	protected Servico servico;
	
	public ControllerServico() {
		this.servico = new Servico();
	}


	public void cadastrarServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException, ElementoDuplicadoException, Exception {
		servico.cadastrarServico(servicoDto);
		
	}


	public void deletarServico(ServicoDTO servicoDto) {
		servico.deletarServico(servicoDto);
		
	}


	public void editarServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException, Exception {
		servico.editarServico(servicoDto);
		
	}


	public ServicoDTO listarServicos() {
		return servico.listarServicos();
	}
	
	
	
}
