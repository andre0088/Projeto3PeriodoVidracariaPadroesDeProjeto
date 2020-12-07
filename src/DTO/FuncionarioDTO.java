package DTO;

import java.util.ArrayList;

import Model.Pessoa;

public class FuncionarioDTO extends Pessoa {
	
	private ArrayList<FuncionarioDTO> listaFuncionarios = new ArrayList<FuncionarioDTO>();
	
	private String senha;
	private String cargo;
	private float salario;
	private String email;

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public float getSalario() {
		return salario;
	}
	public void setSalario(float salario) {
		this.salario = salario;
	}
	public ArrayList<FuncionarioDTO> getListaFuncionarios() {
		return listaFuncionarios;
	}
	public void setListaFuncionarios(ArrayList<FuncionarioDTO> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}