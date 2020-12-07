package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Controller.ControllerFuncionario;
import DTO.FuncionarioDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoNaoEncontradoException;

public class JanelaLogin extends JanelaVidro{

	private OuvinteDaJanelaDeLogin ouvinte = new OuvinteDaJanelaDeLogin(this);

	public JanelaLogin() throws HeadlessException {
		super("Janela de Login",400,620);
		addLabels();
		addFields();
		addBotoes();

		setVisible(true);
	}
	
	private JLabelTextos esqueciASenha;

	private void addLabels() {
		JLabelTextos usuario = new JLabelTextos("Usuario", 75, 230, 100, 30);
		usuario.setHorizontalAlignment(JLabel.LEFT);

		JLabelTextos senha = new JLabelTextos("Senha", 75, 290, 100, 30);
		senha.setHorizontalAlignment(JLabel.LEFT);

		esqueciASenha = new JLabelTextos("<html><u>Esqueci a Senha</u></html>",135, 450, 150, 35);
		esqueciASenha.addMouseListener(ouvinte);
		esqueciASenha.setName("esqueci");
		
		add(usuario);
		add(senha);
		add(esqueciASenha);
	}

	private JTextField tfLogin = new JTextField();
	private JPasswordField tfSenha = new JPasswordField();

	private void addFields() {

		MaskFormatter mask;
		try {
			mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			tfLogin = new JFormattedTextField(mask);
			tfLogin.setBounds(75, 260, 250, 30);
			tfLogin.setFont(new Font("Consolas", Font.BOLD, 16));
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		tfSenha.setBounds(75, 320, 250, 30);
		tfSenha.setFont(new Font("Consolas", Font.BOLD, 16));
		
		add(tfLogin);
		add(tfSenha);

	}

	private JButton entrar;

	private void addBotoes() {
		
		entrar = new JButton("Entrar");
		entrar.setBounds(150, 400, 100, 35);
		entrar.setVerticalAlignment(JLabel.CENTER);
		entrar.setFont(new Font("Consolas", Font.BOLD, 16));
		entrar.addActionListener(ouvinte);

		add(entrar);

	}

	protected class OuvinteDaJanelaDeLogin implements ActionListener, MouseListener {
		private JFrame janela;

		public OuvinteDaJanelaDeLogin(JFrame j) {
			janela = j;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==entrar) {
				ControllerFuncionario controlador = new ControllerFuncionario();
				String login = tfLogin.getText();
				String senha = tfSenha.getText();
				FuncionarioDTO funcionario = new FuncionarioDTO();
				funcionario.setCpf(login);
				funcionario.setSenha(senha);
				ViewFactory fabrica = new ViewFactory();
				try {
					FuncionarioDTO funcionarioDto = controlador.fazerLogin(funcionario);
					fabrica.logar(funcionarioDto);
					janela.dispose();
				} catch (ElementoNaoEncontradoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				
				
			}
		}

		public void mouseClicked(MouseEvent e) {
			
		}

		public void mouseEntered(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			if (label.getName().equalsIgnoreCase("esqueci")) {
				label.setForeground(Color.blue);
			} else
				label.setSize(35, 35);
//			JLabel label = (JLabel) e.getSource();
		}

		public void mouseExited(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			if (label.getName().equalsIgnoreCase("esqueci")) {
				label.setForeground(Color.white);
			} else
				label.setSize(30, 30);
		}

		public void mousePressed(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			if (label.getName().equalsIgnoreCase("esqueci")) {
				label.setForeground(new Color(0,0,150));
			} else
				label.setSize(25, 25);
//			label.setOpaque(true);
//			label.setBackground(new Color(200, 200, 200));
		}

		public void mouseReleased(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			if (label.getName().equalsIgnoreCase("esqueci")) {
				label.setForeground(new Color(0,0,150));
				new JanelaRecuperarSenha().setLocationRelativeTo(janela);
				dispose();
			} else
				label.setSize(30, 30);

		}

	}

	public static void main(String[] args) {
		new JanelaLogin();
	}
}
