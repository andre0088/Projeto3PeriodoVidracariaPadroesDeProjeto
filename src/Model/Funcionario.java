package Model;

import DAO.AdapterFuncionario;
import DAO.CrudMethods;
import DAO.FuncionarioDAO_Postgres;
import DAO.InterfaceFuncionarioDAO;
import DTO.FuncionarioDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;

public class Funcionario extends Pessoa{
	
	private String senha;
	private String cargo;
	private float salario;
	private String email;
	
	protected InterfaceFuncionarioDAO InterFuncionarioDao;
	protected CrudMethods crudAdapter;
	
	public Funcionario() {
		this.InterFuncionarioDao = new FuncionarioDAO_Postgres();
		this.crudAdapter = new AdapterFuncionario();
	}
	
	public void cadastrarFuncionario(FuncionarioDTO funcionarioDto) throws DadosNaoPreenchidosException, ElementoDuplicadoException, Exception {
		crudAdapter.cadastrar(funcionarioDto);
		
	}
	
	public void deletarFuncionario(FuncionarioDTO funcionarioDto) {
		crudAdapter.excluir(funcionarioDto);
		
	}
	
	public void editarFuncionario(FuncionarioDTO funcionarioDto) throws DadosNaoPreenchidosException,Exception {
		crudAdapter.editar(funcionarioDto);
		
	}

	public FuncionarioDTO listarFuncionarios() {
		
		return (FuncionarioDTO) crudAdapter.listar();
	}
	
	public FuncionarioDTO recuperarFuncionario(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException {
		
		return InterFuncionarioDao.recuperarFuncionario(funcionarioDto);
	}
	
	public FuncionarioDTO fazerLogin(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException {
		return InterFuncionarioDao.fazerLogin(funcionarioDto);
	}
	
	public void fazerLogoff() {
		InterFuncionarioDao.fazerLogoff();
	}
	
	public FuncionarioDTO usuarioLogado() {
		return InterFuncionarioDao.getUsuarioLogado();
	}

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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}