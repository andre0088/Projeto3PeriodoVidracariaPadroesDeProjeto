package Model;

import DAO.AdapterCliente;
//import DAO.ClienteDAO;
import DAO.ClienteDAO_Postgres;
import DAO.CrudMethods;
import DAO.InterfaceClienteDAO;
import DTO.ClienteDTO;
import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;

public class Cliente extends Pessoa{
	
	protected InterfaceClienteDAO interclienteDao;
	protected CrudMethods crudAdapter;
	
	public Cliente() {
		this.interclienteDao = new ClienteDAO_Postgres();
		crudAdapter = new AdapterCliente();
	}
	
	public void cadastrarCliente(ClienteDTO clienteDto) throws ElementoDuplicadoException, DadosNaoPreenchidosException,Exception {
		crudAdapter.cadastrar(clienteDto);
		
	}

	public void deletarCliente(ClienteDTO clienteDto) {
		crudAdapter.excluir(clienteDto);
		
	}

	public void editarCliente(ClienteDTO clienteDto) throws DadosNaoPreenchidosException,Exception {
		crudAdapter.editar(clienteDto);
		
	}

	public ClienteDTO recuperarCliente(ClienteDTO clienteDto) throws ElementoNaoEncontradoException {
		return interclienteDao.recuperarCliente(clienteDto);
	}

	public ClienteDTO listarClientes() {
		return (ClienteDTO) crudAdapter.listar();
	}
	
	public ClienteDTO nomeCliente(ServicoDTO servicoDto) {
		return interclienteDao.nomeCliente(servicoDto);
	}
	
}
