package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.Conexao;
import DTO.ClienteDTO;
import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;

public class ClienteDAO_Postgres implements InterfaceClienteDAO {

	//falta lanças as exceções
	@Override
	public void cadastrarCliente(ClienteDTO clienteDto) throws ElementoDuplicadoException, DadosNaoPreenchidosException { 
		if (clienteDto.getCpf().equals("") || clienteDto.getEndereco().equals("") || clienteDto.getNome().equals("")|| clienteDto.getTelefone().equals("")) {
			throw new DadosNaoPreenchidosException();
		}
		try {
			if (recuperarCliente(clienteDto)!=null) {
				throw new ElementoDuplicadoException();
			}
		} catch (ElementoNaoEncontradoException e1) {
		}
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("INSERT INTO Cliente (nome,endereco,cpf,telefone) VALUES (?,?,?,?)");
			pst.setString(1, clienteDto.getNome());
			pst.setString(2, clienteDto.getEndereco());
			pst.setString(3, clienteDto.getCpf());
			pst.setString(4, clienteDto.getTelefone());
			pst.execute();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public void deletarCliente(ClienteDTO clienteDto) {
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("DELETE FROM Cliente where cpf=?");
			pst.setString(1, clienteDto.getCpf());
			pst.execute();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editarCliente(ClienteDTO clienteDto) throws DadosNaoPreenchidosException {
		if (clienteDto.getCpf().equals("") || clienteDto.getEndereco().equals("") || clienteDto.getNome().equals("")
				|| clienteDto.getTelefone().equals("")) {
			throw new DadosNaoPreenchidosException();
		}
		
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("update Cliente set nome=?, endereco=?,cpf=?,telefone=? where cpf =\'"+clienteDto.getCpf()+"\';");
			pst.setString(1, clienteDto.getNome());
			pst.setString(2, clienteDto.getEndereco());
			pst.setString(3, clienteDto.getCpf());
			pst.setString(4, clienteDto.getTelefone());
			pst.execute();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ClienteDTO recuperarCliente(ClienteDTO clienteDto) throws ElementoNaoEncontradoException {
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		ClienteDTO cli=null;
		try {
			pst = con.prepareStatement("SELECT * FROM Cliente where cpf=?");
			pst.setString(1, clienteDto.getCpf());
			ResultSet rs = pst.executeQuery();
			if (rs==null) {
				throw new ElementoNaoEncontradoException();
			}
			while(rs.next()) {
				cli=new ClienteDTO();
				cli.setNome(rs.getString("nome"));
				cli.setEndereco(rs.getString("endereco"));
				cli.setCpf(rs.getString("cpf"));
				cli.setTelefone(rs.getString("telefone"));
			}
			rs.close();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cli;
	}

	@Override
	public ClienteDTO listarClientes() {
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		ClienteDTO cliente = new ClienteDTO();
		try {
			pst = con.prepareStatement("SELECT * FROM Cliente");
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ClienteDTO cli = new ClienteDTO();
				cli.setNome(rs.getString("nome"));
				cli.setEndereco(rs.getString("endereco"));
				cli.setCpf(rs.getString("cpf"));
				cli.setTelefone(rs.getString("telefone"));
				cliente.getListaClientes().add(cli);
			}
			rs.close();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}

	@Override
	public ClienteDTO nomeCliente(ServicoDTO servicoDto) {
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		ClienteDTO cli = null;
		try {
			pst = con.prepareStatement("SELECT nome FROM Cliente where cpf='"+servicoDto.getCpf()+"'");
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				cli=new ClienteDTO();
				cli.setNome(rs.getString("nome"));
			}
			rs.close();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cli;
	}

}
