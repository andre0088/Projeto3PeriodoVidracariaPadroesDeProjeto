package DAO;

import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public interface InterfaceServicoDAO {
	
	public boolean cadastrarServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException,ElementoDuplicadoException;
	
	public void deletarServico(ServicoDTO servicoDto);
	
	public void editarServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException;
	
	public ServicoDTO listarServicos();
	
}
