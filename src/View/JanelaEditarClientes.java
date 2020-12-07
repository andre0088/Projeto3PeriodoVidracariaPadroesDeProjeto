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
import View.Exceptions.ElementoNaoEncontradoException;

public class JanelaEditarClientes extends JanelaCadastro{
	
	private JanelaGerenciarClientes janela;
	private String cpf;
	
	public JanelaEditarClientes(JanelaGerenciarClientes janela,String cpf) {
		super("Janela de Edição de Clientes");
		this.janela=janela;
		this.cpf=cpf;
		addLabels();
		addTextfields();
		addBotoes();
		
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
		this.setVisible(true);
	}
	
	private ouvinteEdicaoCli ouvinte = new ouvinteEdicaoCli(this);

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

	@Override
	public void addTextfields() {
		ControllerCliente control = new ControllerCliente();
		ClienteDTO cliente = new ClienteDTO();
		cliente.setCpf(cpf);
		ClienteDTO cli = null;
		try {
			cli = control.recuperarCliente(cliente);
		} catch (ElementoNaoEncontradoException e) {
			e.printStackTrace();
		}
		
		Font font = new Font("Consolas", Font.BOLD, 16);

		tfNome = new JTextField(cli.getNome());
		tfNome.setBounds(170, 200, 500, 30);
		tfNome.setFont(font);

		tfEndereco = new JTextField(cli.getEndereco());
		tfEndereco.setBounds(170, 240, 500, 30);
		tfEndereco.setFont(font);

		MaskFormatter mask;
		try {
			mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			tfCPF = new JFormattedTextField(mask);
			tfCPF.setText(cli.getCpf());
			tfCPF.setBounds(170, 280, 200, 30);
			tfCPF.setFont(font);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		try {
			mask = new MaskFormatter("(##) #####-#### ");
			mask.setValueContainsLiteralCharacters(false);
			tfTel = new JFormattedTextField(mask);
			tfTel.setBounds(470, 280, 200, 30);
			tfTel.setFont(font);
			tfTel.setText(cli.getTelefone());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		add(tfNome);
		add(tfEndereco);
		add(tfCPF);
		add(tfTel);
	}

	private JButton btEditar;
	private JButton btCancelar;

	@Override
	public void addBotoes() {
		btEditar = new JButton("Editar");
		btEditar.setBounds(440, 370, 100, 30);
		btEditar.addActionListener(ouvinte);

		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(250, 370, 100, 30);
		btCancelar.addActionListener(new OuvinteDoBtVoltar(this));

		add(btCancelar);
		add(btEditar);
	}

	private class ouvinteEdicaoCli implements ActionListener {

		private JanelaEditarClientes j;

		public ouvinteEdicaoCli(JanelaEditarClientes janela) {
			this.j = janela;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btEditar) {
				ControllerCliente control = new ControllerCliente();
				ClienteDTO c = new ClienteDTO();
				c.setNome(tfNome.getText());
				c.setEndereco(tfEndereco.getText());
				c.setCpf(tfCPF.getText());
				c.setTelefone(tfTel.getText());
				try {
					control.editarCliente(c);
					JOptionPane.showMessageDialog(null, "Cliente editado com sucesso!");
					janela.atualizarTabela();
					j.dispose();
				} catch (DadosNaoPreenchidosException e1) {
					JOptionPane.showMessageDialog(j, e1.getMessage());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(j, e1.getMessage());
				}
			}
		}

	}
	
	
	
}
