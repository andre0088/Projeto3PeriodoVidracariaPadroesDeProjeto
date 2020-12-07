package Controller;

import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class ControllerFacade {
	
	protected ControllerRelatorio controlRelatorio;
	protected ControllerServico controlServico;
	
	public ControllerFacade() {
		this.controlRelatorio = new ControllerRelatorio();
		this.controlServico = new ControllerServico();
	}
	
	public void cadastroDeServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException, ElementoDuplicadoException, Exception {
		controlServico.cadastrarServico(servicoDto);
		controlRelatorio.gerarNota(servicoDto);
	}
	
}
