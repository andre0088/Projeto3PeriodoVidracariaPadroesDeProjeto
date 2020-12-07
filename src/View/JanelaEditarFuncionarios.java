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
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import Controller.ControllerFuncionario;
import DTO.FuncionarioDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.ElementoNaoEncontradoException;

public class JanelaEditarFuncionarios extends JanelaCadastro{
	
	private JanelaGerenciarFuncionarios janela;
	private String cpf;
	
	public JanelaEditarFuncionarios(JanelaGerenciarFuncionarios janela,String cpf) {
		super("Janela de Edição de Funcionários");
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
		
		setVisible(true);
	}

	private ouvinteEditarFun ouvinte = new ouvinteEditarFun(this);

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
	private JFormattedTextField tfSalario; 
	private JPasswordField tfSenha1;
	private JPasswordField tfSenha2;
	private JTextField tfEmail;
	
	@Override
	public void addTextfields() {
		ControllerFuncionario control = new ControllerFuncionario();
		FuncionarioDTO funcUtil = new FuncionarioDTO();
		funcUtil.setCpf(cpf);
		FuncionarioDTO func = null;
		try {
			func=control.recuperarFuncionario(funcUtil);
		} catch (ElementoNaoEncontradoException e3) {
			e3.printStackTrace();
		}
		
		Font font = new Font("Consolas",Font.BOLD,16);
		
		tfNome = new JTextField(func.getNome());
		tfNome.setBounds(170,200,500,30);
		tfNome.setFont(font);
		
		tfEndereco = new JTextField(func.getEndereco());
		tfEndereco.setBounds(170,240,500,30);
		tfEndereco.setFont(font);
		
		MaskFormatter mask;
		try {
			mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			tfCPF = new JFormattedTextField(mask);
			tfCPF.setBounds(170,280,200,30);
			tfCPF.setFont(font);
			tfCPF.setText(func.getCpf());
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		try {
			mask = new MaskFormatter("(##) #####-#### ");
			mask.setValueContainsLiteralCharacters(false);
			tfTel = new JFormattedTextField(mask);
			tfTel.setBounds(470,280,200,30);
			tfTel.setFont(font);
			tfTel.setText(func.getTelefone());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		String[] cargos= {"Atendente", "Mão de obra", "Gerente"};
		tfCargo = new JComboBox<String>(cargos);
		tfCargo.setBounds(170,320,200,30);
		tfCargo.setFont(font);
		tfCargo.setSelectedItem(func.getCargo());
		
		try {
			mask = new MaskFormatter("R$ #.###,##");
			mask.setValueContainsLiteralCharacters(false);
			mask.setPlaceholderCharacter('0');
			mask.setValidCharacters("1234567890");
			tfSalario = new JFormattedTextField(mask);
			tfSalario.setBounds(470,320,200,30);
			tfSalario.setFont(font);
			String sal = String.valueOf(func.getSalario());
			tfSalario.setValue(String.valueOf(sal.replace(".", "")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		tfSenha1 = new JPasswordField();
		tfSenha1.setBounds(170, 360, 200, 30);
		tfSenha1.setFont(font);
		tfSenha1.setText(func.getSenha());
		
		tfSenha2 = new JPasswordField();
		tfSenha2.setBounds(470, 360, 200, 30);
		tfSenha2.setFont(font);
		tfSenha2.setText(func.getSenha());
		
		tfEmail = new JTextField();
		tfEmail.setBounds(170,400,500,30);
		tfEmail.setFont(font);
		tfEmail.setText(func.getEmail());
		
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

	private JButton btEditar; 
	private JButton btCancelar; 
	
	@Override
	public void addBotoes() {
		btEditar = new JButton("Editar");
		btEditar.setBounds(440, 440, 100, 30);
		btEditar.addActionListener(ouvinte);
		
		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(250, 440, 100, 30);
		btCancelar.addActionListener(new OuvinteDoBtVoltar(this));
		
		add(btCancelar);
		add(btEditar);
	}
	
	private class ouvinteEditarFun implements ActionListener{

		private JanelaEditarFuncionarios j;
		
		public ouvinteEditarFun(JanelaEditarFuncionarios j) {
			this.j = j;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btEditar) {
				ControllerFuncionario control = new ControllerFuncionario();
				FuncionarioDTO f = new FuncionarioDTO();
				f.setNome(tfNome.getText());
				f.setEndereco(tfEndereco.getText());
				f.setCpf(tfCPF.getText());
				f.setCargo((String)tfCargo.getSelectedItem());
				f.setTelefone(tfTel.getText());
				f.setEmail(tfEmail.getText());
				String senha="";
				String sm = tfSalario.getText();
				String salarioNormal= ""+sm.charAt(3)+sm.charAt(5)+sm.charAt(6)+sm.charAt(7)+"."+sm.charAt(9)+sm.charAt(10);
				f.setSalario(Float.parseFloat(salarioNormal));
				if(tfSenha1.getText().equals(tfSenha2.getText())) {
					senha=tfSenha1.getText();
				}
				f.setSenha(senha);
				try {
					control.editarFuncionario(f);
					JOptionPane.showMessageDialog(j, "Funcionário Editado com Sucesso!");
					j.dispose();
					janela.atualizarTabela();
				} catch (DadosNaoPreenchidosException e1) {
					JOptionPane.showMessageDialog(j, e1.getMessage());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(j, e1.getMessage());
				}
			}
		}
		
	}
	public static void main(String[] args) {
		new JanelaGerenciarFuncionarios();
	}
}
