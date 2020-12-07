package testesJunit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import DAO.FuncionarioDAO;
import DTO.FuncionarioDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class TestaFuncionario {

	FuncionarioDAO dao = new FuncionarioDAO();
	FuncionarioDTO funcionarioCorreto;
	FuncionarioDTO funcionarioRepetido;
	FuncionarioDTO funcionarioVazio;
	ArrayList<FuncionarioDTO> funcionarios;
	
	@Before
	public void inicialize() {
		funcionarioCorreto = new FuncionarioDTO();
		funcionarioCorreto.setCargo("Atendente");
		funcionarioCorreto.setCpf("222");
		funcionarioCorreto.setEmail("an@ann.com");
		funcionarioCorreto.setEndereco("Rua shxl");
		funcionarioCorreto.setNome("An");
		funcionarioCorreto.setSalario(28);
		funcionarioCorreto.setSenha("123456");
		funcionarioCorreto.setTelefone("555555");
		try {
			dao.cadastrarFuncionario(funcionarioCorreto);
		} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e) {
			System.out.println(e.getMessage());
		}
		
		funcionarioRepetido = new FuncionarioDTO();
		funcionarioRepetido.setCargo("Atendente");
		funcionarioRepetido.setCpf("222");
		funcionarioRepetido.setEmail("an@an.com");
		funcionarioRepetido.setEndereco("Rua shxl");
		funcionarioRepetido.setNome("A");
		funcionarioRepetido.setSalario(27);
		funcionarioRepetido.setSenha("123");
		funcionarioRepetido.setTelefone("875585826");
		try {
			dao.cadastrarFuncionario(funcionarioRepetido);
		} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e) {
			System.out.println(e.getMessage());
		}
		
		funcionarioVazio = new FuncionarioDTO();
		funcionarioVazio = new FuncionarioDTO();
		funcionarioVazio.setCargo("Atendente");
		funcionarioVazio.setCpf("444");
		funcionarioVazio.setEmail("an@an.com");
		funcionarioVazio.setEndereco("Rua shxl");
		funcionarioVazio.setNome("A");
		funcionarioVazio.setSalario(27);
		funcionarioVazio.setSenha("12345678");
		funcionarioVazio.setTelefone("875585826");
	}
	
	@Test
	public void testCadastro() {
		funcionarios = dao.listarFuncionarios().getListaFuncionarios();
		assertTrue(dao.contains(funcionarioCorreto));
		assertFalse(dao.contains(funcionarioRepetido));
		assertFalse(dao.contains(funcionarioVazio));


	}
	
	@Test
	public void testaLogin() {
	
		assertTrue(dao.login(funcionarioCorreto));
		assertFalse(dao.login(funcionarioVazio));
	}
}
