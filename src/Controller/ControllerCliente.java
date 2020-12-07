package Controller;

import DTO.ClienteDTO;
import DTO.ServicoDTO;
import Model.Cliente;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;
public class ControllerCliente {

	protected Cliente cliente;
	
	public ControllerCliente() {
		this.cliente = new Cliente();
	}

	public void cadastrarCliente(ClienteDTO clienteDto) throws ElementoDuplicadoException, DadosNaoPreenchidosException, Exception {

		cliente.cadastrarCliente(clienteDto);

	}

	public void deletarCliente(ClienteDTO clienteDto) {

		cliente.deletarCliente(clienteDto);

	}

	public void editarCliente(ClienteDTO clienteDto) throws DadosNaoPreenchidosException, Exception {

		cliente.editarCliente(clienteDto);

	}

	public ClienteDTO recuperarCliente(ClienteDTO clienteDto) throws ElementoNaoEncontradoException {

		return cliente.recuperarCliente(clienteDto);
	}

	public ClienteDTO listarClientes() {

		return cliente.listarClientes();
	}
	
	public ClienteDTO nomeCliente(ServicoDTO servicoDto) {
		return cliente.nomeCliente(servicoDto);
	}

}
