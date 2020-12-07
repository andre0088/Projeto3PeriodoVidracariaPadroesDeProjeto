package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Controller.ControllerCliente;
import DTO.ClienteDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class JanelaCadastroClientes extends JanelaCadastro {

	private JanelaGerenciarClientes janela;

	public JanelaCadastroClientes(JanelaGerenciarClientes janela) {
		super("Janela de Cadastro de Clientes");
		this.janela = janela;
		addLabels();
		addTextfields();
		addBotoes();

		// ouvinte de janela para bloquear janela de gerencia enquanto a de cadastro
		// está aberta
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				janela.setEnabled(false);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				janela.setEnabled(true);
				janela.toFront();
			}
		});
		// termina aqui

		setVisible(true);
	}

	private ouvinteCadastroCli ouvinte = new ouvinteCadastroCli(this);

	@Override
	public void addLabels() {
		JLabelTextos lbNome = new JLabelTextos("Nome:", 10, 190, 150, 50);
		JLabelTextos lbEndereco = new JLabelTextos("Endereço:", 10, 230, 150, 50);
		JLabelTextos lbCPF = new JLabelTextos("CPF:", 10, 270, 150, 50);
		JLabelTextos lbTel = new JLabelTextos("Telefone:", 360, 270, 100, 50);

		lbNome.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbEndereco.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbCPF.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbTel.setHorizontalAlignment(JLabelTextos.RIGHT);

		add(lbNome);
		add(lbEndereco);
		add(lbCPF);
		add(lbTel);

	}

	private JTextField tfNome;
	private JTextField tfEndereco;
	private JTextField tfCPF;
	private JTextField tfTel;
	private MaskFormatter mask1;
	private MaskFormatter mask2;

	@Override
	public void addTextfields() {
		Font font = new Font("Consolas", Font.BOLD, 16);

		tfNome = new JTextField();
		tfNome.setBounds(170, 200, 500, 30);
		tfNome.setFont(font);

		tfEndereco = new JTextField();
		tfEndereco.setBounds(170, 240, 500, 30);
		tfEndereco.setFont(font);

		try {
			mask1 = new MaskFormatter("###.###.###-##");
			mask1.setValueContainsLiteralCharacters(false);
			tfCPF = new JFormattedTextField(mask1);
			tfCPF.setBounds(170, 280, 200, 30);
			tfCPF.setFont(font);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		try {
			mask2 = new MaskFormatter("(##) #####-#### ");
			mask2.setValueContainsLiteralCharacters(false);
			tfTel = new JFormattedTextField(mask2);
			tfTel.setBounds(470, 280, 200, 30);
			tfTel.setFont(font);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		add(tfNome);
		add(tfEndereco);
		add(tfCPF);
		add(tfTel);
	}

	private JButton btCadastrar;
	private JButton btCancelar;

	@Override
	public void addBotoes() {
		btCadastrar = new JButton("Cadastrar");
		btCadastrar.setBounds(440, 370, 100, 30);
		btCadastrar.addActionListener(ouvinte);

		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(250, 370, 100, 30);
		btCancelar.addActionListener(new OuvinteDoBtVoltar(this));

		add(btCancelar);
		add(btCadastrar);
	}

	private class ouvinteCadastroCli implements ActionListener {

		private JanelaCadastroClientes j;

		public ouvinteCadastroCli(JanelaCadastroClientes janela) {
			this.j = janela;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btCadastrar) {
				ControllerCliente control = new ControllerCliente();
				ClienteDTO c = new ClienteDTO();
				c.setNome(tfNome.getText());
				c.setEndereco(tfEndereco.getText());
				c.setCpf(tfCPF.getText());
				c.setTelefone(tfTel.getText());
				try {
					control.cadastrarCliente(c);
					JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
					janela.atualizarTabela();
					j.dispose();
				} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e1) {
					JOptionPane.showMessageDialog(j, e1.getMessage());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(j, e1.getMessage());
				}
			}
		}

	}

}
