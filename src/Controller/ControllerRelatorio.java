package Controller;

import DTO.ServicoDTO;
import Model.Relatorio;
import View.Exceptions.FaltaDeDinheiroException;
import View.Exceptions.NaoHaClientesDevendoException;
import View.Exceptions.NaoHaServicosAtrasadosException;
import View.Exceptions.NenhumServicoException;

public class ControllerRelatorio{

	protected Relatorio relatorio;
	
	public ControllerRelatorio() {
		this.relatorio = new Relatorio();
	}
	
	public void quantidadesServicosMensais(int mes, int ano) throws NenhumServicoException {
		relatorio.quantidadesServicosMensais(mes, ano);
		
	}

	public void relatorioClientesDevendo() throws NaoHaClientesDevendoException {
		relatorio.relatorioClientesDevendo();
		
	}

	public void ganhoDeDinheiroMensal(int mes, int ano) throws NenhumServicoException, FaltaDeDinheiroException {
		relatorio.ganhoDeDinheiroMensal(mes, ano);
		
	}

	public void relatorioServicosAtrasados() throws NaoHaServicosAtrasadosException {
		relatorio.relatorioServicosAtrasados();
		
	}
	
	public void gerarNota(ServicoDTO servicoDto) {	
		relatorio.gerarNota(servicoDto);
	}

}
