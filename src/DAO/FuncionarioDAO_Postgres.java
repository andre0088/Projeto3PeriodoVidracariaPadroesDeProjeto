package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Connection.Conexao;
import DTO.FuncionarioDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;

public class FuncionarioDAO_Postgres implements InterfaceFuncionarioDAO {

	@Override
	public void cadastrarFuncionario(FuncionarioDTO funcionarioDto)	throws DadosNaoPreenchidosException, ElementoDuplicadoException {
		if (funcionarioDto.getCpf().equals("") || funcionarioDto.getEndereco().equals("")
				|| funcionarioDto.getNome().equals("") || funcionarioDto.getTelefone().equals("")
				|| funcionarioDto.getCargo().equals("") || funcionarioDto.getSalario() == 0
				|| funcionarioDto.getSenha().equals("")) {
			throw new DadosNaoPreenchidosException();
		}

		try {
			if (recuperarFuncionario(funcionarioDto)!=null) {
				throw new ElementoDuplicadoException();
			}
		} catch (ElementoNaoEncontradoException e1) {
		}
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("INSERT INTO Funcionario VALUES (?,?,?,?,?,?,?,?)");
			pst.setString(1, funcionarioDto.getNome());
			pst.setString(2, funcionarioDto.getEndereco());
			pst.setString(3, funcionarioDto.getCpf());
			pst.setString(4, funcionarioDto.getTelefone());
			pst.setString(5, funcionarioDto.getSenha());
			pst.setString(6, funcionarioDto.getCargo());
			pst.setFloat(7, funcionarioDto.getSalario());
			pst.setString(8, funcionarioDto.getEmail());
			pst.execute();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deletarFuncionario(FuncionarioDTO funcionarioDto) {
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("DELETE FROM Funcionario where cpf=?");
			pst.setString(1, funcionarioDto.getCpf());
			pst.execute();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
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
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(	"update Funcionario set nome=?, endereco=?,cpf=?,telefone=?,"
						+ "senha=?, cargo=?, salario=?, email=? where cpf ='"+funcionarioDto.getCpf()+"'");
			pst.setString(1, funcionarioDto.getNome());
			pst.setString(2, funcionarioDto.getEndereco());
			pst.setString(3, funcionarioDto.getCpf());
			pst.setString(4, funcionarioDto.getTelefone());
			pst.setString(5, funcionarioDto.getSenha());
			pst.setString(6, funcionarioDto.getCargo());
			pst.setFloat(7, funcionarioDto.getSalario());
			pst.setString(8, funcionarioDto.getEmail());
			pst.execute();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public FuncionarioDTO listarFuncionarios() {
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		FuncionarioDTO funcionario = new FuncionarioDTO();
		try {
			pst = con.prepareStatement("SELECT * FROM Funcionario");
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				FuncionarioDTO fun = new FuncionarioDTO();
				fun.setNome(rs.getString("nome"));
				fun.setEndereco(rs.getString("endereco"));
				fun.setCpf(rs.getString("cpf"));
				fun.setTelefone(rs.getString("telefone"));
				fun.setSenha(rs.getString("senha"));
				fun.setCargo(rs.getString("cargo"));
				fun.setSalario(rs.getFloat("salario"));
				fun.setEmail(rs.getString("email"));
				funcionario.getListaFuncionarios().add(fun);
			}
			rs.close();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return funcionario;
	}

	@Override
	public FuncionarioDTO recuperarFuncionario(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException {
		Connection con = Conexao.getInstancia();
		PreparedStatement pst;
		FuncionarioDTO fun = null;
		try {
			pst = con.prepareStatement("SELECT * FROM Funcionario where cpf=?");
			pst.setString(1, funcionarioDto.getCpf());
			ResultSet rs = pst.executeQuery();
			if (rs==null) {
				throw new ElementoNaoEncontradoException();
			}
			while(rs.next()) {
				fun = new FuncionarioDTO();
				fun.setNome(rs.getString("nome"));
				fun.setEndereco(rs.getString("endereco"));
				fun.setCpf(rs.getString("cpf"));
				fun.setTelefone(rs.getString("telefone"));
				fun.setSenha(rs.getString("senha"));
				fun.setCargo(rs.getString("cargo"));
				fun.setSalario(rs.getFloat("salario"));
				fun.setEmail(rs.getString("email"));
			}
			rs.close();
			Conexao.fecharConexao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fun;
	}

	@Override
	public FuncionarioDTO fazerLogin(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException {
		
		FuncionarioDTO funcionario;
		try {
			funcionario = recuperarFuncionario(funcionarioDto);
			if (funcionario != null && funcionario.getSenha().equals(funcionarioDto.getSenha())){
				setUsuarioLogado(funcionario);
				return funcionario;
			}else {
				throw new ElementoNaoEncontradoException();
			}
		} catch (ElementoNaoEncontradoException e) {
			throw new ElementoNaoEncontradoException();
		}
		
	
	}
	
	@Override
	public void setUsuarioLogado(FuncionarioDTO f) {
		 new FuncionarioDAO().setUsuarioLogado(f);
	}

	@Override
	public FuncionarioDTO getUsuarioLogado() {
		return new FuncionarioDAO().getUsuarioLogado();
	}

	@Override
	public void fazerLogoff() {
		new FuncionarioDAO().setUsuarioLogado(null);
	}

}
