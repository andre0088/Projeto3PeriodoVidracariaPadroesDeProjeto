package DAO;

import DTO.ClienteDTO;
import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;

public interface InterfaceClienteDAO {

	public void cadastrarCliente(ClienteDTO clienteDto) throws ElementoDuplicadoException, DadosNaoPreenchidosException;

	public void deletarCliente(ClienteDTO clienteDto);

	public void editarCliente(ClienteDTO clienteDto) throws DadosNaoPreenchidosException;

	public ClienteDTO recuperarCliente(ClienteDTO clienteDto) throws ElementoNaoEncontradoException;

	public ClienteDTO listarClientes();
	
	public ClienteDTO nomeCliente(ServicoDTO servicoDto);

}
