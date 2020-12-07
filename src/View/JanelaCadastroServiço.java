package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controller.ControllerFacade;
import Controller.ControllerServico;
import DTO.ServicoDTO;
import View.Exceptions.DadosNaoPreenchidosException;
import View.Exceptions.DataInvalidaException;
import View.Exceptions.ElementoDuplicadoException;

public class JanelaCadastroServiço extends JanelaCadastro{

	private String cpfCliente;
	private JanelaGerenciarClientes janela;
	private ouvinteCadastroSer ouvinte = new ouvinteCadastroSer(this);
	
	ControllerFacade controlFacade = new ControllerFacade();
	
	public JanelaCadastroServiço(JanelaGerenciarClientes janela,String cpfCliente) {
		super("Janela de Cadastro de Serviços");
		this.janela=janela;
		this.cpfCliente=cpfCliente;
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

	
	
	@Override
	public void addLabels() {
		JLabelTextos lbDescricao = new JLabelTextos("Descrição:",10,200,150,70);
		JLabelTextos lbEndereco = new JLabelTextos("Endereço:",10,270,150,50);
		JLabelTextos lbDataEntrega = new JLabelTextos("Data Entrega:",10,320,150,50);
		JLabelTextos lbStatus = new JLabelTextos("Status:",380,320,100,50);
		JLabelTextos lbPreco= new JLabelTextos("Preço:",10,370,150,50);
		JLabelTextos lbQtdPaga = new JLabelTextos("Qtd. Paga:",380,375,100,40);
		
		lbDescricao.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbEndereco.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbDataEntrega.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbStatus.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbPreco.setHorizontalAlignment(JLabelTextos.RIGHT);
		lbQtdPaga.setHorizontalAlignment(JLabelTextos.RIGHT);
		
		add(lbDescricao);
		add(lbEndereco);
		add(lbDataEntrega);
		add(lbStatus);
		add(lbPreco);
		add(lbQtdPaga);
	}

	private JTextArea taDescricao;
	private JTextField tfEndereco;
	private JTextField tfData;
	private JComboBox<String> tfSatus;
	private JTextField tfPreco;
	private JTextField tfQtdPaga;
	
	@Override
	public void addTextfields() {
		Font font = new Font("Consolas",Font.BOLD,15);
		
		taDescricao = new JTextArea();
		JScrollPane sPainel = new JScrollPane(taDescricao);
		sPainel.setBounds(170,200,500,70);
		taDescricao.setBounds(0,0,480,60);
		taDescricao.setWrapStyleWord(true);
		taDescricao.setLineWrap(true);
		taDescricao.setFont(font);
		
		tfEndereco = new JTextField();
		tfEndereco.setBounds(170,280,500,30);
		tfEndereco.setFont(font);

		tfData = new JTextField();
		tfData.setBounds(170,330,180,30);
		tfData.setFont(font);
		
		String[] status1 = {"Pendete", "Em Andamento", "Concluído"};
		tfSatus = new JComboBox<String>(status1);
		tfSatus.setBounds(490,330,180,30);
		tfSatus.setFont(font);
		
		tfPreco = new JTextField();
		tfPreco.setBounds(170,380,180,30);
		tfPreco.setFont(font);
		
		tfQtdPaga = new JTextField();
		tfQtdPaga.setBounds(490,380,180,30);
		tfQtdPaga.setFont(font);
		
		add(sPainel);
		add(tfEndereco);
		add(tfData);
		add(tfSatus);
		add(tfPreco);
		add(tfQtdPaga);
	}

	private JButton btCancelar;
	private JButton btCadastrar;
	
	@Override
	public void addBotoes() {
		btCancelar = new JButton("Cancelar");
		btCancelar.setBounds(250, 430, 100, 30);
		btCancelar.addActionListener(new OuvinteDoBtVoltar(this));
		btCadastrar = new JButton("Cadastrar");
		btCadastrar.setBounds(440, 430, 100, 30);
		btCadastrar.addActionListener(ouvinte);
		
		add(btCancelar);
		add(btCadastrar);
	}
	
	private class ouvinteCadastroSer implements ActionListener{

		private JanelaCadastroServiço janela;
		
		public ouvinteCadastroSer(JanelaCadastroServiço janela) {
			this.janela = janela;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btCadastrar) {
				ControllerServico control = new ControllerServico();
				ServicoDTO servico = new ServicoDTO();
				ArrayList<ServicoDTO> servicos = control.listarServicos().getListaServicos();
				servico.setCpf(cpfCliente);
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				Date data;
				
				
				int ultimoId = 0;
				if(servicos.isEmpty()==false) {
					ultimoId = servicos.get(servicos.size()-1).getId();
				}
				
				try {
					data = formato.parse(tfData.getText());
					if(data.compareTo(new Date()) == -1) {
						throw new DataInvalidaException();
					}
					servico.setDataEntrega(data);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(janela,"Formato de data inválido.");
				} catch (DataInvalidaException e1) {
					JOptionPane.showMessageDialog(janela,e1.getMessage());
				}
				servico.setDataPedido(new Date());
				servico.setDescricao(taDescricao.getText());
				servico.setEndereco(tfEndereco.getText());
				servico.setId(ultimoId+1);
				servico.setPrecoCompleto(Float.parseFloat(tfPreco.getText()));
				servico.setQtdPago(Float.parseFloat(tfQtdPaga.getText()));
				servico.setStatus((String) tfSatus.getSelectedItem());
				try {
					controlFacade.cadastroDeServico(servico);
					JOptionPane.showMessageDialog(janela, "Serviço cadastrado com sucesso!");
					janela.dispose();
				} catch (DadosNaoPreenchidosException | ElementoDuplicadoException e1) {
					JOptionPane.showMessageDialog(janela, e1.getMessage());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(janela, e1.getMessage());
				}
			}
		}
	}
}
