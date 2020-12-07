package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.itextpdf.text.log.SysoLogger;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import DTO.ClienteDTO;
import DTO.FuncionarioDTO;
import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;

public class ClienteDAO implements InterfaceClienteDAO {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo = new File("clientes.xml");
	
	private ArrayList<ClienteDTO> listaClientes = new ArrayList<ClienteDTO>();
	
	public ClienteDAO() {
		this.listaClientes=recuperarClientes();
	}
	
	public void salvarClientes(ArrayList<ClienteDTO> listaClientes){
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
		xml += xstream.toXML(listaClientes);
		try {
			if(!arquivo.exists()) {
				arquivo.createNewFile();
			}
			PrintWriter gravar = new PrintWriter(arquivo);
			gravar.print(xml);
			gravar.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ClienteDTO> recuperarClientes() {
		try {
			if(arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<ClienteDTO>)xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<ClienteDTO>();
	}

	@Override
	public void cadastrarCliente(ClienteDTO clienteDto) throws ElementoDuplicadoException, DadosNaoPreenchidosException {

		if (clienteDto.getCpf().equals("") || clienteDto.getEndereco().equals("") || clienteDto.getNome().equals("")|| clienteDto.getTelefone().equals("")) {
			throw new DadosNaoPreenchidosException();
		}

		try {
			recuperarCliente(clienteDto);
			throw new ElementoDuplicadoException();
		} catch (ElementoNaoEncontradoException e) {
			listaClientes.add(clienteDto);
			salvarClientes(listaClientes);
		}

	}

	@Override
	public void deletarCliente(ClienteDTO clienteDto) {
		try {
			listaClientes.remove(recuperarCliente(clienteDto));
			salvarClientes(listaClientes);
		} catch (ElementoNaoEncontradoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editarCliente(ClienteDTO clienteDto) throws DadosNaoPreenchidosException {

		if (clienteDto.getCpf().equals("") || clienteDto.getEndereco().equals("") || clienteDto.getNome().equals("")
				|| clienteDto.getTelefone().equals("")) {
			throw new DadosNaoPreenchidosException();
		}

		try {
			ClienteDTO cliente = recuperarCliente(clienteDto);
			cliente.setEndereco(clienteDto.getEndereco());
			cliente.setNome(clienteDto.getNome());
			cliente.setTelefone(clienteDto.getTelefone());
			salvarClientes(listaClientes);
		} catch (ElementoNaoEncontradoException e) {
			e.printStackTrace();
		}

	}

	@Override
	public ClienteDTO listarClientes() {
		ClienteDTO cliente = new ClienteDTO();
		for (ClienteDTO clienteDto : listaClientes) {
			cliente.getListaClientes().add(clienteDto);
		}
		return cliente;
	}

	@Override
	public ClienteDTO recuperarCliente(ClienteDTO clienteDto) throws ElementoNaoEncontradoException {

		for (int i = 0; i < listaClientes.size(); i++) {
			if (listaClientes.get(i).getCpf().equals(clienteDto.getCpf())) {
				return listaClientes.get(i);
			}
		}
		throw new ElementoNaoEncontradoException();
	}

	public ArrayList<ClienteDTO> getListaClientes() {
		return listaClientes;
	}
	
	@Override
	public ClienteDTO nomeCliente(ServicoDTO servicoDto) {
		
		ClienteDTO novo = new ClienteDTO();
		for(ClienteDTO cliente: listaClientes) {
			if(cliente.getCpf().equals(servicoDto.getCpf())) {
				novo.setNome(cliente.getNome());
				break;
			}
				
		}
		return novo;
		
	}
	
	public void setListaClientes(ArrayList<ClienteDTO> listaClientes) {
		this.listaClientes = listaClientes;
	}
	public static void main(String[] args) {
		FuncionarioDAO dao = new FuncionarioDAO();
		FuncionarioDTO funcionario1 = new FuncionarioDTO();
		System.out.println(dao.listarFuncionarios().getListaFuncionarios().get(0).getCpf());
	}
	
}
