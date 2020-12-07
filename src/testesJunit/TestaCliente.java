package testesJunit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.log.SysoLogger;

import DAO.ClienteDAO;
import DTO.ClienteDTO;
import Model.Cliente;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class TestaCliente {

	ClienteDAO clienteDao = new ClienteDAO();
	private ClienteDTO clienteCorreto;
	private ClienteDTO clienteRepetido;
	private ClienteDTO clienteVazio;
	private ArrayList<ClienteDTO> clientes;
	
	@Before
	public void inicialize() {
		clienteCorreto = new ClienteDTO();
		clienteCorreto.setCpf("111");
		clienteCorreto.setEndereco("Rua 1");
		clienteCorreto.setNome("André");
		clienteCorreto.setTelefone("87991530164");
		
		try {
			clienteDao.cadastrarCliente(clienteCorreto);
		} catch (ElementoDuplicadoException | DadosNaoPreenchidosException e) {
			System.out.println(e.getMessage());
		}
		
		clienteRepetido = new ClienteDTO();
		clienteRepetido.setCpf("111");
		clienteRepetido.setEndereco("Rua 2");
		clienteRepetido.setNome("Luan");
		clienteRepetido.setTelefone("87996373127");
		
		try {
			clienteDao.cadastrarCliente(clienteRepetido);
		} catch (ElementoDuplicadoException | DadosNaoPreenchidosException e) {
			System.out.println(e.getMessage());
		}
		
		clienteVazio = new ClienteDTO();
		clienteVazio.setCpf("888");
		clienteVazio.setEndereco("Rua 2");
		clienteVazio.setNome("");
		clienteVazio.setTelefone("87996373127");
		try {
			clienteDao.cadastrarCliente(clienteVazio);
		} catch (ElementoDuplicadoException | DadosNaoPreenchidosException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testCadastro() {
		clientes = clienteDao.listarClientes().getListaClientes();
		assertTrue(clientes.contains(clienteCorreto));
		assertFalse(clientes.contains(clienteRepetido));
		assertFalse(clientes.contains(clienteVazio));
	}
	
}
