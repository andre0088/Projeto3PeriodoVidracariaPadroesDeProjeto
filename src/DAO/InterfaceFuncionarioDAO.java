package DAO;

import DTO.FuncionarioDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;

public interface InterfaceFuncionarioDAO {
	
	public void cadastrarFuncionario(FuncionarioDTO funcionarioDto) throws DadosNaoPreenchidosException, ElementoDuplicadoException;
	
	public void deletarFuncionario(FuncionarioDTO funcionarioDto);
	
	public void editarFuncionario(FuncionarioDTO funcionarioDto) throws DadosNaoPreenchidosException;
	
	public FuncionarioDTO listarFuncionarios();
	
	public FuncionarioDTO recuperarFuncionario(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException;
	
	public FuncionarioDTO fazerLogin(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException;
	
	public FuncionarioDTO getUsuarioLogado();
	
	public void setUsuarioLogado(FuncionarioDTO f);

	public void fazerLogoff();
}
