package Model.Util;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jaxen.function.ConcatFunction;

import Controller.ControllerFuncionario;
import DTO.FuncionarioDTO;
import View.Exceptions.ElementoNaoEncontradoException;

public class JavaMail {

	private String remetente = "projeto.alugabens@gmail.com";
	private String senha = "projeto120";

	public void enviarEmail(String destinatario, String cpf) throws MessagingException {
		Properties props = new Properties();

		props.put("mail.smtp.user", remetente);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.EnableSSL.enable", "true");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(remetente, senha);
			}
		});
		session.setDebug(true);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(remetente));
		Address[] toUser = InternetAddress.parse(destinatario);
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject("LIMA VIDROS - RECUPERAÇÃO DE SENHA");
		
		ControllerFuncionario control = new ControllerFuncionario();
		FuncionarioDTO fun = new FuncionarioDTO();
		fun.setCpf(cpf);
		try {
			fun = control.recuperarFuncionario(fun);
		} catch (ElementoNaoEncontradoException e) {
		}
		
		message.setText("Sua senha é: "+fun.getSenha());
		Transport.send(message);
	}
	
	
	
}
