package Controller;

import DTO.FuncionarioDTO;
import View.JanelaLogin;

public class Main {
	public static void main(String[] args) {
		ControllerFuncionario control = new ControllerFuncionario();
		if(control.listarFuncionarios().getListaFuncionarios().isEmpty()) {
			FuncionarioDTO administrador = new FuncionarioDTO();
			administrador.setCargo("admin");
			administrador.setCpf("000.000.000-00");
			administrador.setEndereco("admin");
			administrador.setNome("admin");
			administrador.setSalario(1000f);
			administrador.setSenha("123");
			administrador.setTelefone("admin");
			try {
				control.cadastrarFuncionario(administrador);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		new JanelaLogin().setLocationRelativeTo(null);
	}
}
