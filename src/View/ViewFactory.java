package View;

import DTO.FuncionarioDTO;

public class ViewFactory{

	public JanelaGerenciar logar(FuncionarioDTO funcionarioDto) {
		switch (funcionarioDto.getCargo()) {
		case "Atendente":
			return new JanelaGerenciarClientes();
		case "M�o de obra":
			return new JanelaGerenciarServicos();
		default:
			return new JanelaGerenciarFuncionarios();
			
		}
	}
	
}