package Model;

import DAO.InterfaceRelatoriosDAO;
import DAO.RelatoriosDAO;
import DTO.ServicoDTO;
import View.Exceptions.FaltaDeDinheiroException;
import View.Exceptions.NaoHaClientesDevendoException;
import View.Exceptions.NaoHaServicosAtrasadosException;
import View.Exceptions.NenhumServicoException;

public class Relatorio{

	protected InterfaceRelatoriosDAO interRelatorios;
	
	public Relatorio() {
		this.interRelatorios = new RelatoriosDAO();
	}
	
	public void quantidadesServicosMensais(int mes, int ano) throws NenhumServicoException {
		interRelatorios.quantidadesServicosMensais(mes, ano);
		
	}

	public void relatorioClientesDevendo() throws NaoHaClientesDevendoException {
		interRelatorios.relatorioClientesDevendo();
		
	}

	public void ganhoDeDinheiroMensal(int mes, int ano) throws NenhumServicoException, FaltaDeDinheiroException {
		interRelatorios.ganhoDeDinheiroMensal(mes, ano);
		
	}

	public void relatorioServicosAtrasados() throws NaoHaServicosAtrasadosException {
		interRelatorios.relatorioServicosAtrasados();
		
	}
	
	public void gerarNota(ServicoDTO servicoDto) {
		interRelatorios.gerarNota(servicoDto);
	}

}
