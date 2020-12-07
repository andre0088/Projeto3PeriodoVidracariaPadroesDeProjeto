package View;

import java.text.ParseException;

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Controller.ControllerJavaMail;
import View.Exceptions.DadosNaoPreenchidosException;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaRecuperarSenha extends JanelaVidro {

	private OuvinteDeRecuperarSenha ouvinte = new OuvinteDeRecuperarSenha(this);
	
	public JanelaRecuperarSenha() {
		super("Recuperar Senha", 500, 400);
		addLabels();
		addFields();
		addBotoes();
		
		setVisible(true);
	}
	
	private void addLabels() {
		JLabelTextos lbEmail = new JLabelTextos("<html><div align=right>Digite<br>seu Email:</div></html>", 10, 190, 110, 50);
		JLabelTextos lbCpf = new JLabelTextos("<html><div align=right>Digite<br>seu nº CPF:</div></html>", 10, 240, 110, 50);

		lbEmail.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbCpf.setHorizontalAlignment(JLabelTextos.RIGHT);
		
		add(lbCpf);
		add(lbEmail);
	}

	private JTextField email;
	private JFormattedTextField cpf;

	private void addFields() {
		Font font = new Font("Consolas",Font.BOLD,16);
		
		email= new JTextField();
		email.setBounds(130, 200, 300, 30);
		email.setFont(font);
		
		try {
			cpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
			cpf.setBounds(130, 250, 300, 30);
			cpf.setFont(font);
		} catch (ParseException e) {
		}
		
		add(email);
		add(cpf);
	}
	
	private JButton btCancelar;
	private JButton btEnviar;

	private void addBotoes() {
		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(150, 300, 100, 30);
		btCancelar.addActionListener(ouvinte);

		btEnviar = new JButton("Enviar");
		btEnviar.setBounds(270, 300, 100, 30);
		btEnviar.addActionListener(ouvinte);
		
		add(btCancelar);
		add(btEnviar);
	}
	
	public class OuvinteDeRecuperarSenha implements ActionListener {

		private JanelaRecuperarSenha janela;

		public OuvinteDeRecuperarSenha(JanelaRecuperarSenha j) {
			janela = j;
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btCancelar) {
				new JanelaLogin().setLocationRelativeTo(janela);
				dispose();
			} else if (e.getSource() == btEnviar) {
				ControllerJavaMail control = new ControllerJavaMail();
				try {
					control.enviarEmail(email.getText(),cpf.getText());
						JOptionPane.showMessageDialog(janela, "Você receberá um e-mail com a sua senha");
						new JanelaLogin().setLocationRelativeTo(janela);
						dispose();
				} catch (MessagingException | DadosNaoPreenchidosException e1) {
					JOptionPane.showMessageDialog(janela, e1.getMessage());
				}
				
			}
		}
	}

	public static void main(String[] args) {
		new JanelaRecuperarSenha();
	}

}
