package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import DTO.FuncionarioDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;

public class FuncionarioDAO implements InterfaceFuncionarioDAO {

	private XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
	private XStream xstreamUsuarioLogado = new XStream(new DomDriver("ISO-8859-1"));
	private File arquivo = new File("funcionarios.xml");
	private File arquivoUsuarioLogado = new File("usuarioLogado.xml");

	private ArrayList<FuncionarioDTO> listaFuncionarios = new ArrayList<FuncionarioDTO>();
	private FuncionarioDTO usuarioLogado = new FuncionarioDTO();

	public FuncionarioDAO() {
		this.listaFuncionarios = recuperarFuncionarios();
		usuarioLogado = recuperarUserLogado();
	}

	public void salvarFuncionarios(ArrayList<FuncionarioDTO> listaFuncionarios) {
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
		xml += xstream.toXML(listaFuncionarios);
		try {
			if (!arquivo.exists()) {
				arquivo.createNewFile();
			}
			PrintWriter gravar = new PrintWriter(arquivo);
			gravar.print(xml);
			gravar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<FuncionarioDTO> recuperarFuncionarios() {
		try {
			if (arquivo.exists()) {
				FileInputStream fis = new FileInputStream(arquivo);
				return (ArrayList<FuncionarioDTO>) xstream.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new ArrayList<FuncionarioDTO>();
	}

	
	
	public void salvarFunLogado(FuncionarioDTO usuarioLogado) {
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
		xml += xstreamUsuarioLogado.toXML(usuarioLogado);
		try {
			if (!arquivoUsuarioLogado.exists()) {
				arquivoUsuarioLogado.createNewFile();
			}
			PrintWriter gravar = new PrintWriter(arquivoUsuarioLogado);
			gravar.print(xml);
			gravar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FuncionarioDTO recuperarUserLogado() {
		try {
			if (arquivoUsuarioLogado.exists()) {
				FileInputStream fis = new FileInputStream(arquivoUsuarioLogado);
				return (FuncionarioDTO) xstreamUsuarioLogado.fromXML(fis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new FuncionarioDTO();
	}
	
	public boolean contains(FuncionarioDTO funcionario) {
		try {
			recuperarFuncionario(funcionario);
			return true;
		} catch (ElementoNaoEncontradoException e) {
			return false;
		}
	}

	public boolean confirmLogin(FuncionarioDTO fun) throws ElementoNaoEncontradoException {
		boolean bandeira = true;
		
		if(fazerLogin(fun)==null) {
			bandeira = false;
		};
		
		return bandeira;
	}
	
	@Override
	public FuncionarioDTO fazerLogin(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException {

		FuncionarioDTO funcionario = null;

		for (FuncionarioDTO fun : listaFuncionarios) {
			if (fun.getCpf().equals(funcionarioDto.getCpf()) && fun.getSenha().equals(funcionarioDto.getSenha())) {
				funcionario = fun;
			}
		}
		if (funcionario == null) {
			throw new ElementoNaoEncontradoException();
		}
		setUsuarioLogado(funcionario);
		return funcionario;
	}

	@Override
	public void fazerLogoff() {
		setUsuarioLogado(null);
	}

	@Override
	public FuncionarioDTO getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public void setUsuarioLogado(FuncionarioDTO usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		salvarFunLogado(usuarioLogado);
	}
	
	@Override
	public void cadastrarFuncionario(FuncionarioDTO funcionarioDto)
			throws DadosNaoPreenchidosException, ElementoDuplicadoException {

		if (funcionarioDto.getCpf().equals("") || funcionarioDto.getEndereco().equals("")
				|| funcionarioDto.getNome().equals("") || funcionarioDto.getTelefone().equals("")
				|| funcionarioDto.getCargo().equals("") || funcionarioDto.getSalario() == 0
				|| funcionarioDto.getSenha().equals("")) {
			throw new DadosNaoPreenchidosException();
		}

		try {
			recuperarFuncionario(funcionarioDto);
			throw new ElementoDuplicadoException();
		} catch (ElementoNaoEncontradoException e) {
			listaFuncionarios.add(funcionarioDto);
			salvarFuncionarios(listaFuncionarios);
		}
		
		
		

	}

	@Override
	public void deletarFuncionario(FuncionarioDTO funcionarioDto) {
		try {
			listaFuncionarios.remove(recuperarFuncionario(funcionarioDto));
			salvarFuncionarios(listaFuncionarios);
		} catch (ElementoNaoEncontradoException e) {
			e.printStackTrace();
		}

	}
	
	public boolean login(FuncionarioDTO funcionario) {
		try {
			fazerLogin(funcionario);
			return true;
		} catch (ElementoNaoEncontradoException e) {
			return false;
		}
	}

	@Override
	public void editarFuncionario(FuncionarioDTO funcionarioDto) throws DadosNaoPreenchidosException {
		if (funcionarioDto.getCpf().equals("") || funcionarioDto.getEndereco().equals("")
				|| funcionarioDto.getNome().equals("") || funcionarioDto.getTelefone().equals("")
				|| funcionarioDto.getCargo().equals("") || funcionarioDto.getSenha().equals("")
				|| funcionarioDto.getSalario() == 0 || funcionarioDto.getEmail().equals("")) {
			throw new DadosNaoPreenchidosException();
		}

		try {
			FuncionarioDTO funcionario = recuperarFuncionario(funcionarioDto);
			funcionario.setCpf(funcionarioDto.getCpf());
			funcionario.setEndereco(funcionarioDto.getEndereco());
			funcionario.setNome(funcionarioDto.getNome());
			funcionario.setTelefone(funcionarioDto.getTelefone());
			funcionario.setCargo(funcionarioDto.getCargo());
			funcionario.setSenha(funcionarioDto.getSenha());
			funcionario.setSalario(funcionarioDto.getSalario());
			funcionario.setEmail(funcionarioDto.getEmail());
			salvarFuncionarios(listaFuncionarios);
		} catch (ElementoNaoEncontradoException e) {
			e.printStackTrace();
		}

	}

	@Override
	public FuncionarioDTO listarFuncionarios() {
		FuncionarioDTO funcionario = new FuncionarioDTO();
		for (FuncionarioDTO funcionarioDto : recuperarFuncionarios()) {
			funcionario.getListaFuncionarios().add(funcionarioDto);
		}
		return funcionario;
	}

	@Override
	public FuncionarioDTO recuperarFuncionario(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException {
		for (int i = 0; i < listaFuncionarios.size(); i++) {
			if (listaFuncionarios.get(i).getCpf().equals(funcionarioDto.getCpf())) {
				return listaFuncionarios.get(i);
			}
		}
		throw new ElementoNaoEncontradoException();
	}

}