package View;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Font;

import Controller.ControllerFuncionario;
import DTO.FuncionarioDTO;

public class JanelaGerenciarFuncionarios extends JanelaGerenciar{
	
	private OuvinteJanelaGerenciarFuncionarios ouvinte = new OuvinteJanelaGerenciarFuncionarios(this); 
	
	public JanelaGerenciarFuncionarios() {
		super("Gerenciamento de Funcionarios");
		adicionarJLabel();
		addFiltro();
		adicionarJTable();
		atualizarTabela();
		this.setVisible(true);
	}
	
	
	private JLabel cadastrar;
	private JLabel editar;
	private JLabel excluir;
	
	public void adicionarJLabel() {
		cadastrar = new JLabelPersonalizado("<html><div align=center>Cadastrar<br>Funcionário</div></html>",10,330,230,60);
		cadastrar.addMouseListener(ouvinte);
		
		editar = new JLabelPersonalizado("<html><div align=center>Editar<br>Funcionário</div></html>",10,410,230,60);
		editar.addMouseListener(ouvinte);
		
		excluir = new JLabelPersonalizado("<html><div align=center>Excluir<br>Funcionário</div></html>",10 ,490 ,230 , 60);
		excluir.addMouseListener(ouvinte);
		
		add(cadastrar);
		add(editar);
		add(excluir);
	}
	
	private JTextField tfFiltro = new JTextField();

	private void addFiltro() {
		tfFiltro.setBounds(250, 200, 290, 30);
		tfFiltro.addKeyListener(ouvinte);
		tfFiltro.setFont(new Font("Consolas",Font.BOLD,16));
		tfFiltro.setToolTipText("Digite aqui para fazer uma busca rápida (Ex: José Silva)");
		add(tfFiltro);
	}
	
	private DefaultTableModel modelo;
	private JTable tabela;
	
	public void adicionarJTable() {
		tabela = new JTable();
		tabela.setSelectionMode(0);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.setSelectionBackground(new Color(0, 255, 20, 180));
		tabela.setRowHeight(30);
	
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(250, 240, 920, 440);
		add(scroll);
	}
	
	public void atualizarTabela() {
		ControllerFuncionario controller = new ControllerFuncionario();
		
		modelo = new DefaultTableModel(){
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		tabela.setModel(modelo);
		
		modelo.addColumn("Nome");
		modelo.addColumn("CPF");
		modelo.addColumn("Telefone");
		modelo.addColumn("Salário");
		modelo.addColumn("Cargo");
		modelo.addColumn("Endereço");
		
		DefaultTableCellRenderer letrasTabela = new DefaultTableCellRenderer() {
			public void setValue(Object value) {
				setFont(new Font("Consolas",Font.BOLD,15));
				super.setValue(value);
			}
		};		
		int[] largura = {220,140,150,80,110,380};
		for(int i=0;i<6;i++) {
			tabela.getColumnModel().getColumn(i).setPreferredWidth(largura[i]);
			tabela.getColumnModel().getColumn(i).setCellRenderer(letrasTabela);
		}
	
		Iterator it = new IteratorArrayList<>(controller.listarFuncionarios().getListaFuncionarios());
		while (it.hasNext()) {
			FuncionarioDTO fun = (FuncionarioDTO) it.next();
			if(fun.getCargo().equalsIgnoreCase("Admin")==false) {
				Object[] linha = new Object[] {
						fun.getNome(), fun.getCpf(), fun.getTelefone(),
						fun.getSalario(),fun.getCargo(), fun.getEndereco()
				};
				modelo.addRow(linha);
			}	
		}
		
		tabela.repaint();
		
	}
	
	private class OuvinteJanelaGerenciarFuncionarios implements MouseListener, KeyListener {

		private JanelaGerenciarFuncionarios janela;

		public OuvinteJanelaGerenciarFuncionarios(JanelaGerenciarFuncionarios j) {
			janela = j;
		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setOpaque(true);
			label.setBackground(new Color(30,255,20,220));
		}

		public void mouseExited(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setBackground(new Color(00,75,200));
			label.setOpaque(true);
		}

		public void mousePressed(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setOpaque(true);
			label.setBackground(new Color(30,165,20,220));
		
		}

		public void mouseReleased(MouseEvent e) {
			if (e.getSource() == cadastrar) {
				new JanelaCadastroFuncionarios(janela).setLocationRelativeTo(janela);
			}
			
			else if(e.getSource() == excluir) {
				if(tabela.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(null, "Selecione algum funcionário para realizar essa ação.");
				}else {
					Object codigo=modelo.getValueAt(tabela.getSelectedRow(),1);
					String cpf = (String) codigo;
					FuncionarioDTO funcionario = new FuncionarioDTO();
					funcionario.setCpf(cpf);
					Object[] botoes = {"Sim", "Não"};  
				    int resposta = JOptionPane.showOptionDialog(null,"Deseja Excluir esse Funcionário?", "Confirmação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoes, botoes);
				    if(resposta == 0) {
				    	ControllerFuncionario controlador = new ControllerFuncionario();
				    	controlador.deletarFuncionario(funcionario);
				    	JOptionPane.showMessageDialog(janela, "Funcionário excluido com sucesso !");
				    	janela.atualizarTabela();
				    }
				
				}
			}
			
			else if(e.getSource() == editar) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Selecione algum funcionário para realizar essa ação.");
				} else {
					Object codigo = modelo.getValueAt(tabela.getSelectedRow(), 1);
					String cpf = (String) codigo;
					new JanelaEditarFuncionarios(janela,cpf).setLocationRelativeTo(janela);
				}
			}
		}

		public void keyPressed(KeyEvent e) {
			TableRowSorter organizador = new TableRowSorter(modelo);
			tabela.setRowSorter(organizador);
			String filtro = tfFiltro.getText();
			if (filtro.length() == 0) {
				organizador.setRowFilter(null);
			} else {
				try {
					organizador.setRowFilter(RowFilter.regexFilter("(?i)" + filtro));
				} catch (Exception e1) {
				}
			}
			repaint();
		}

		public void keyReleased(KeyEvent e) {
			TableRowSorter organizador = new TableRowSorter(modelo);
			tabela.setRowSorter(organizador);
			String filtro = tfFiltro.getText();
			if (filtro.length() == 0) {
				organizador.setRowFilter(null);
			} else {
				try {
					organizador.setRowFilter(RowFilter.regexFilter("(?i)" + filtro));
				} catch (Exception e1) {
				}
			}
			repaint();
		}

		public void keyTyped(KeyEvent e) {
			TableRowSorter organizador = new TableRowSorter(modelo);
			tabela.setRowSorter(organizador);
			String filtro = tfFiltro.getText();
			if (filtro.length() == 0) {
				organizador.setRowFilter(null);
			} else {
				try {
					organizador.setRowFilter(RowFilter.regexFilter("(?i)" + filtro));
				} catch (Exception e1) {
				}
			}
			repaint();
		}
	}

}
