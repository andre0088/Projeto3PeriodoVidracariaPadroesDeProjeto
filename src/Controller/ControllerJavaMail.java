package Controller;

import javax.mail.MessagingException;

import Model.Util.JavaMail;
import View.Exceptions.DadosNaoPreenchidosException;

public class ControllerJavaMail {
	
	private JavaMail email = new JavaMail();
	
	public void enviarEmail(String destinatario, String cpf) throws MessagingException, DadosNaoPreenchidosException{
		if (new ControllerFuncionario().verificarCpfEmail(destinatario, cpf)) {
			email.enviarEmail(destinatario,cpf);
		}else {
			throw new DadosNaoPreenchidosException();
		}
	}
	
}
