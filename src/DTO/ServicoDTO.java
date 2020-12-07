package DTO;

import java.util.Date;
import java.util.ArrayList;

public class ServicoDTO {
	
	private ArrayList<ServicoDTO> listaServicos = new ArrayList<ServicoDTO>();
	
	private int id;
	private String cpf;
	private String descricao;
	private String endereco;
	private float precoCompleto;
	private float qtdPago;
	private Date dataPedido;
	private Date dataEntrega;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setListaServicos(ArrayList<ServicoDTO> listaServicos) {
		this.listaServicos = listaServicos;
	}
	public ServicoDTO() {
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
	public ArrayList<ServicoDTO> getListaServicos() {
		return listaServicos;
	}
	public void setListaClientes(ArrayList<ServicoDTO> listaServicos) {
		this.listaServicos = listaServicos;
	}
	
}
