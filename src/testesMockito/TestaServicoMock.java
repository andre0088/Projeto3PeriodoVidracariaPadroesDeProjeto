package testesMockito;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import DAO.ServicoDAO;
import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestaServicoMock{
	
	ServicoDAO dao = mock(ServicoDAO.class);
	ServicoDTO servicoCorreto;
	ServicoDTO servicoSemCliente;
	ServicoDTO servicoSemDados;
	ArrayList<ServicoDTO> servicos;
	
	@Before
	public void inicializa() {
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		servicoCorreto = mock(ServicoDTO.class);
		when(servicoCorreto.getCpf()).thenReturn("222");
		Date dataPedido;
		try {
			dataPedido = formatter.parse("10/10/2020");
			servicoCorreto.setDataPedido(dataPedido);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dataEntrega = new Date();
		when(servicoCorreto.getDataEntrega()).thenReturn(dataEntrega);
		when(servicoCorreto.getDescricao()).thenReturn("Box de vidro, 1.80 por 1.60. Vidro fumê.");
		when(servicoCorreto.getEndereco()).thenReturn("Rua 424");
		when(servicoCorreto.getId()).thenReturn(1);
		when(servicoCorreto.getPrecoCompleto()).thenReturn(100f);
		when(servicoCorreto.getQtdPago()).thenReturn(100f);
		when(servicoCorreto.getStatus()).thenReturn("Concluido");
		
		servicoSemCliente = mock(ServicoDTO.class);
		when(servicoSemCliente.getCpf()).thenReturn("999999999999");
		Date dataPedido2;
		try {
			dataPedido2 = formatter.parse("10/10/2020");
			when(servicoSemCliente.getDataPedido()).thenReturn(dataPedido2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dataEntrega2 = new Date();
		when(servicoSemCliente.getDataEntrega()).thenReturn(dataEntrega2);
		when(servicoSemCliente.getDescricao()).thenReturn("Box de vidro");
		when(servicoSemCliente.getEndereco()).thenReturn("Rua 32");
		when(servicoSemCliente.getId()).thenReturn(6);
		when(servicoSemCliente.getPrecoCompleto()).thenReturn(100f);
		when(servicoSemCliente.getQtdPago()).thenReturn(100f);
		when(servicoSemCliente.getStatus()).thenReturn("Concluido");

		
		servicoSemDados=  mock(ServicoDTO.class);
		when(servicoSemDados.getId()).thenReturn(7);
		
		
	}
	
	
	@Test
	public void testaCadastroCorreto(){
		try {
			when(dao.cadastrarServico(servicoCorreto)).thenReturn(true);
			assertTrue(dao.cadastrarServico(servicoCorreto));
		} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testaCadastroSemCliente(){
		try {
			when(dao.cadastrarServico(servicoSemCliente)).thenReturn(false);
			assertFalse(dao.cadastrarServico(servicoSemCliente));
		} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testaDataErrada(){
		try {
			when(dao.cadastrarServico(servicoSemDados)).thenReturn(false);
			assertFalse(dao.cadastrarServico(servicoSemDados));
		} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e) {
			e.printStackTrace();
		}
	}
	
}
