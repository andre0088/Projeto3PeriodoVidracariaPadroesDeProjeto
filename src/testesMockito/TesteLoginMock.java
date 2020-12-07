package testesMockito;

import DAO.FuncionarioDAO;
import DTO.FuncionarioDTO;
import View.Exceptions.ElementoNaoEncontradoException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class TesteLoginMock {
	FuncionarioDAO dao = mock(FuncionarioDAO.class);
	FuncionarioDTO funcionarioVazio;
	
	@Before
	public void inicializa() {
		
		funcionarioVazio = mock(FuncionarioDTO.class);
		when(funcionarioVazio.getCargo()).thenReturn("Atendente");
		when(funcionarioVazio.getCpf()).thenReturn("123");
		when(funcionarioVazio.getSalario()).thenReturn(100f);
		when(funcionarioVazio.getSenha()).thenReturn("123");
	}
	
	@Test
	public void loginInvalido() {

		try {
			when(dao.fazerLogin(funcionarioVazio)).thenReturn(null);
			assertNull(dao.fazerLogin(funcionarioVazio));
		} catch (ElementoNaoEncontradoException e) {
			e.printStackTrace();
		}
		
	}

}
