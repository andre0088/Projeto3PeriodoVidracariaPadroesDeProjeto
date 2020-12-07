package Controller;

import DTO.FuncionarioDTO;
import Model.Funcionario;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;
import View.Exceptions.ElementoNaoEncontradoException;

public class ControllerFuncionario{
	
	protected Funcionario funcionario;
	
	public ControllerFuncionario() {
		this.funcionario = new Funcionario();
	}
	
	public void cadastrarFuncionario(FuncionarioDTO funcionarioDto) throws DadosNaoPreenchidosException, ElementoDuplicadoException, Exception {
		funcionario.cadastrarFuncionario(funcionarioDto);
		
	}

	
	public void deletarFuncionario(FuncionarioDTO funcionarioDto) {
		funcionario.deletarFuncionario(funcionarioDto);
		
	}

	
	public void editarFuncionario(FuncionarioDTO funcionarioDto) throws DadosNaoPreenchidosException, Exception {
		funcionario.editarFuncionario(funcionarioDto);
		
	}

	
	public FuncionarioDTO listarFuncionarios() {
		return funcionario.listarFuncionarios();
	}

	
	public FuncionarioDTO recuperarFuncionario(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException {
		return funcionario.recuperarFuncionario(funcionarioDto);
	}

	
	public FuncionarioDTO fazerLogin(FuncionarioDTO funcionarioDto) throws ElementoNaoEncontradoException {
		return funcionario.fazerLogin(funcionarioDto);
	}
	
	public void fazerLogoff() {
		funcionario.fazerLogoff();
	}
	
	public FuncionarioDTO getUsuarioLogado() {
		return funcionario.usuarioLogado();
	}
	
	public boolean verificarCpfEmail(String email,String cpf){
		FuncionarioDTO f1 = new FuncionarioDTO();
		f1.setEmail(email);
		f1.setCpf(cpf);
		FuncionarioDTO f2;
		try {
			f2 = recuperarFuncionario(f1);
			if (f2.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		} catch (ElementoNaoEncontradoException e) {
		}
		return false;
	}
	
}
