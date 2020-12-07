package testesJunit;

import static org.junit.Assert.assertFalse;
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

public class testaServicoJunit {
	
	ServicoDAO dao = new ServicoDAO();
	ServicoDTO servicoCorreto;
	ServicoDTO servicoSemCliente;
	ServicoDTO servicoDataErrada;
	ArrayList<ServicoDTO> servicos;
	
	@Before
	public void inicializa() {
		servicoCorreto = new ServicoDTO();
		servicoCorreto.setCpf("222");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dataPedido;
		try {
			dataPedido = formatter.parse("10/10/2020");
			servicoCorreto.setDataPedido(dataPedido);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dataEntrega = new Date();
		servicoCorreto.setDataEntrega(dataEntrega);
		servicoCorreto.setDescricao("Box de vidro, 1.80 por 1.60. Vidro fumê.");
		servicoCorreto.setEndereco("Rua 2");
		servicoCorreto.setId(1);
		servicoCorreto.setPrecoCompleto(100);
		servicoCorreto.setQtdPago(100);
		servicoCorreto.setStatus("Concluido");
		
		try {
			dao.cadastrarServico(servicoCorreto);
		} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e2) {
			System.out.println(e2.getMessage());
		}
		
		servicoSemCliente = new ServicoDTO();
		servicoSemCliente.setCpf("999999999999");
		Date dataPedido2;
		try {
			dataPedido2 = formatter.parse("10/10/2020");
			servicoSemCliente.setDataPedido(dataPedido2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dataEntrega2 = new Date();
		servicoSemCliente.setDataEntrega(dataEntrega2);
		servicoSemCliente.setDescricao("Box de vidro, 1.80 por 1.60. Vidro fumê.");
		servicoSemCliente.setEndereco("Rua 2");
		servicoSemCliente.setId(2);
		servicoSemCliente.setPrecoCompleto(100);
		servicoSemCliente.setQtdPago(100);
		servicoSemCliente.setStatus("Concluido");
		
		try {
			dao.cadastrarServico(servicoSemCliente);
		} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e1) {
			System.out.println(e1.getMessage());
		}
		
		servicoDataErrada =  new ServicoDTO();
		servicoDataErrada.setCpf("222");
		Date dataPedido3;
		try {
			dataPedido3 = formatter.parse("16/10/2020");
			servicoDataErrada.setDataPedido(dataPedido3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dataEntrega3 = new Date();
		servicoDataErrada.setDataEntrega(dataEntrega3);
		servicoDataErrada.setDescricao("Acessórios de borracha.");
		servicoDataErrada.setEndereco("Rua 2");
		servicoDataErrada.setId(3);
		servicoDataErrada.setPrecoCompleto(100);
		servicoDataErrada.setQtdPago(100);
		servicoDataErrada.setStatus("Concluido");
		
		try {
			dao.cadastrarServico(servicoDataErrada);
		} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	
	@Test
	public void testaCadastroCorreto(){
		servicos = dao.listarServicos().getListaServicos();
		assertTrue(dao.contains(servicoCorreto));
		
	}
	
	@Test
	public void testaCadastroSemCliente(){
		servicos = dao.listarServicos().getListaServicos();
		assertTrue(dao.contains(servicoSemCliente));
		
	}
	
	@Test
	public void testaDataErrada(){
		servicos = dao.listarServicos().getListaServicos();
		assertTrue(dao.contains(servicoDataErrada));
	}
	
}
