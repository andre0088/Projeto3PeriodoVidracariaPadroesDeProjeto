package Model;

import java.util.Date;

import DAO.AdapterServico;
import DAO.CrudMethods;
import DAO.InterfaceServicoDAO;
import DAO.ServicoDAO;
import DAO.ServicoDAO_Postegres;
import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class Servico{
	
	private int id;
	private String cpf;
	private String descricao;
	private String endereco;
	private float precoCompleto;
	private float qtdPago;
	private Date dataPedido;
	private Date dataEntrega;
	private String status;
	
	protected InterfaceServicoDAO interfaceServicoDao;
	protected CrudMethods crudAdapter;
	
	public Servico() {
		this.interfaceServicoDao = new ServicoDAO_Postegres();
		this.crudAdapter = new AdapterServico();
	}
	

	public void cadastrarServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException, ElementoDuplicadoException,Exception {
		crudAdapter.cadastrar(servicoDto);
		
	}


	public void deletarServico(ServicoDTO servicoDto) {
		crudAdapter.excluir(servicoDto);
		
	}


	public void editarServico(ServicoDTO servicoDto) throws DadosNaoPreenchidosException, Exception {
		crudAdapter.editar(servicoDto);
		
	}


	public ServicoDTO listarServicos() {
		return (ServicoDTO) crudAdapter.listar();
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public CrudMethods getCrudAdapter() {
		return crudAdapter;
	}


	public void setCrudAdapter(CrudMethods crudAdapter) {
		this.crudAdapter = crudAdapter;
	}


	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public float getPrecoCompleto() {
		return precoCompleto;
	}
	public void setPrecoCompleto(float precoCompleto) {
		this.precoCompleto = precoCompleto;
	}
	public float getQtdPago() {
		return qtdPago;
	}
	public void setQtdPago(float qtdPago) {
		this.qtdPago = qtdPago;
	}
	public Date getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}
	public Date getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
