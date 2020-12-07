package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import Controller.ControllerFuncionario;
import DTO.FuncionarioDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoDuplicadoException;

public class JanelaCadastroFuncionarios extends JanelaCadastro {

	private JanelaGerenciarFuncionarios janela;
	
	public JanelaCadastroFuncionarios(JanelaGerenciarFuncionarios janela) {
		super("Janela de Cadastro de Funcionários");
		this.janela=janela;
		addLabels();
		addTextfields();
		addBotoes();
		
		// ouvinte de janela para bloquear janela de gerencia enquanto a de cadastro está aberta
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
	
	private ouvinteCadastroFun ouvinte = new ouvinteCadastroFun(this);

	@Override
	public void addLabels() {
		JLabelTextos lbNome = new JLabelTextos("Nome:",10,190,150,50);
		JLabelTextos lbEndereco = new JLabelTextos("Endereço:",10,230,150,50);
		JLabelTextos lbCPF = new JLabelTextos("CPF:",10,270,150,50);
		JLabelTextos lbTel = new JLabelTextos("Telefone:",360,270,100,50);
		JLabelTextos lbCargo = new JLabelTextos("Cargo:",10,310,150,50);
		JLabelTextos lbSalario = new JLabelTextos("Salario:",360,310,100,50);
		JLabelTextos lbSenha1= new JLabelTextos("Senha:",10,350,150,50);
		JLabelTextos lbSenha2= new JLabelTextos("<html><div align=right>Confirmar<br>Senha:</div></html>",380,350,100,50);
		JLabelTextos lbEmail = new JLabelTextos("<html><div align=right>Email para<br>Recuperar Senha:</div></html>",10,390,150,50);
		
		
		lbNome.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbEndereco.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbCPF.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbTel.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbCargo.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbSalario.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbSenha1.setHorizontalAlignment(JLabelTextos.RIGHT);
	
		
		add(lbNome);
		add(lbEndereco);
		add(lbCPF);
		add(lbTel);
		add(lbCargo);
		add(lbSalario);
		add(lbSenha1);
		add(lbSenha2);
		add(lbEmail);

	}
	
	private JTextField tfNome;
	private JTextField tfEndereco;
	private JTextField tfCPF;
	private JTextField tfTel;
	private JComboBox<String> tfCargo;
	private JTextField tfSalario; 
	private JPasswordField tfSenha1;
	private JPasswordField tfSenha2;
	private JTextField tfEmail;
	
	@Override
	public void addTextfields() {
		Font font = new Font("Consolas",Font.BOLD,16);
		
		tfNome = new JTextField();
		tfNome.setBounds(170,200,500,30);
		tfNome.setFont(font);
		
		tfEndereco = new JTextField();
		tfEndereco.setBounds(170,240,500,30);
		tfEndereco.setFont(font);
		
		MaskFormatter mask;
		try {
			mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			tfCPF = new JFormattedTextField(mask);
			tfCPF.setBounds(170,280,200,30);
			tfCPF.setFont(font);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		try {
			mask = new MaskFormatter("(##) #####-#### ");
			mask.setValueContainsLiteralCharacters(false);
			tfTel = new JFormattedTextField(mask);
			tfTel.setBounds(470,280,200,30);
			tfTel.setFont(font);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		String[] cargos= {"Atendente", "Mão de obra", "Gerente"};
		tfCargo = new JComboBox<String>(cargos);
		tfCargo.setBounds(170,320,200,30);
		tfCargo.setFont(font);

		try {
			mask = new MaskFormatter("R$ #.###,##");
			mask.setValueContainsLiteralCharacters(false);
			mask.setPlaceholderCharacter('0');
			mask.setValidCharacters("1234567890");
			tfSalario = new JFormattedTextField(mask);
			tfSalario.setBounds(470,320,200,30);
			tfSalario.setFont(font);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		tfSenha1 = new JPasswordField();
		tfSenha1.setBounds(170, 360, 200, 30);
		tfSenha1.setFont(font);
		
		tfSenha2 = new JPasswordField();
		tfSenha2.setBounds(470, 360, 200, 30);
		tfSenha2.setFont(font);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(170,400,500,30);
		tfEmail.setFont(font);
		
		add(tfNome);
		add(tfEndereco);
		add(tfCPF);
		add(tfTel);
		add(tfCargo);
		add(tfSalario);
		add(tfSenha1);
		add(tfSenha2);
		add(tfEmail);
	}

	private JButton btCadastrar; 
	private JButton btCancelar; 
	
	@Override
	public void addBotoes() {
		btCadastrar = new JButton("Cadastrar");
		btCadastrar.setBounds(440, 440, 100, 30);
		btCadastrar.addActionListener(ouvinte);
		
		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(250, 440, 100, 30);
		btCancelar.addActionListener(new OuvinteDoBtVoltar(this));
		
		add(btCancelar);
		add(btCadastrar);
	}
	
	private class ouvinteCadastroFun implements ActionListener{

		private JanelaCadastroFuncionarios j;
		
		public ouvinteCadastroFun(JanelaCadastroFuncionarios j) {
			this.j = j;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btCadastrar) {
				ControllerFuncionario control = new ControllerFuncionario();
				FuncionarioDTO f = new FuncionarioDTO();
				f.setNome(tfNome.getText());
				f.setEndereco(tfEndereco.getText());
				f.setCpf(tfCPF.getText());
				f.setCargo((String)tfCargo.getSelectedItem());
				f.setTelefone(tfTel.getText());
				f.setEmail(tfEmail.getText());
				String senha="";
				String sM = tfSalario.getText();
				String salarioNormal= ""+sM.charAt(3)+sM.charAt(5)+sM.charAt(6)+sM.charAt(7)+"."+sM.charAt(9)+sM.charAt(10);
				f.setSalario(Float.parseFloat(salarioNormal));
				if(tfSenha1.getText().equals(tfSenha2.getText())) {
					senha=tfSenha1.getText();
				}
				f.setSenha(senha);
				try {
					control.cadastrarFuncionario(f);
					JOptionPane.showMessageDialog(j, "Funcionário Cadastrado com Sucesso!");
					j.dispose();
					janela.atualizarTabela();
				} catch (DadosNaoPreenchidosException | ElementoDuplicadoException  e1) {
					JOptionPane.showMessageDialog(j, e1.getMessage());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(j, e1.getMessage());
				}
			}
		}
		
	}
	
}
