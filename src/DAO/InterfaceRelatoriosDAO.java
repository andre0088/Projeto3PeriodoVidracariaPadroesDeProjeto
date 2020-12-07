package DAO;

import DTO.ServicoDTO;
import View.Exceptions.FaltaDeDinheiroException;
import View.Exceptions.NaoHaClientesDevendoException;
import View.Exceptions.NaoHaServicosAtrasadosException;
import View.Exceptions.NenhumServicoException;

public interface InterfaceRelatoriosDAO {

	public void quantidadesServicosMensais(int mes,int ano) throws NenhumServicoException;
	
	public void ganhoDeDinheiroMensal(int mes, int ano) throws NenhumServicoException,FaltaDeDinheiroException;

	public void relatorioClientesDevendo() throws NaoHaClientesDevendoException;
	
	public void relatorioServicosAtrasados() throws NaoHaServicosAtrasadosException;
	
	public void gerarNota(ServicoDTO compra);
	
}
