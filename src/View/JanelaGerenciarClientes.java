package View;

import java.awt.Color;
import java.awt.Font;
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

import Controller.ControllerCliente;
import DTO.ClienteDTO;

public class JanelaGerenciarClientes extends JanelaGerenciar {

	private OuvinteJanelaGerenciarClientes ouvinte = new OuvinteJanelaGerenciarClientes(this);

	public JanelaGerenciarClientes() {
		super("Lima Vidros - Gerenciamento de Clientes");
		adicionarJTable();
		addFiltro();
		adicionarJLabel();
		atualizarTabela();
		setVisible(true);
	}

	private JLabel adicionar;
	private JLabel cadastrar;
	private JLabel editar;
	private JLabel excluir;

	public void adicionarJLabel() {
		adicionar = new JLabelPersonalizado("<html><div align=center>Adicionar<br>Serviço</div></html>", 10, 300, 230,
				60);
		adicionar.addMouseListener(ouvinte);

		cadastrar = new JLabelPersonalizado("<html><div align=center>Cadastrar<br>Cliente</div></html>", 10, 380, 230,
				60);
		cadastrar.addMouseListener(ouvinte);

		editar = new JLabelPersonalizado("<html><div align=center>Editar<br>Cliente</div></html>", 10, 460, 230, 60);
		editar.addMouseListener(ouvinte);

		excluir = new JLabelPersonalizado("<html><div align=center>Excluir<br>Cliente</div></html>", 10, 540, 230, 60);
		excluir.addMouseListener(ouvinte);

		add(adicionar);
		add(cadastrar);
		add(editar);
		add(excluir);
	}

	private JTextField tfFiltro = new JTextField();

	private void addFiltro() {
		tfFiltro.setBounds(250, 200, 290, 30);
		tfFiltro.addKeyListener(ouvinte);
		tfFiltro.setFont(new Font("Consolas", Font.BOLD, 16));
		tfFiltro.setToolTipText("Digite aqui para fazer uma busca rápida (Ex: José Silva)");
		add(tfFiltro);
	}

	private DefaultTableModel modelo;
	private JTable tabela;

	public void adicionarJTable() {
		tabela = new JTable();
		tabela.setSelectionMode(0);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabela.setSelectionBackground(new Color(0, 255, 20, 220));
		tabela.setRowHeight(30);

		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(250, 240, 920, 440);
		add(scroll);
	}

	public void atualizarTabela() {
		ControllerCliente controller = new ControllerCliente();

		modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabela.setModel(modelo);

		modelo.addColumn("Nome");
		modelo.addColumn("CPF");
		modelo.addColumn("Telefone");
		modelo.addColumn("Endereço");

		//mudar Font da tabela
		DefaultTableCellRenderer letrasTabela = new DefaultTableCellRenderer() {
			public void setValue(Object value) {
				setFont(new Font("Consolas",Font.BOLD,15));
				super.setValue(value);
			}
		};
		int[] largura = { 220, 200, 200, 320 };
		
		for (int i = 0; i < 4; i++) {
			tabela.getColumnModel().getColumn(i).setPreferredWidth(largura[i]); // setando a largura
			tabela.getColumnModel().getColumn(i).setCellRenderer(letrasTabela); // setando a font
		}
		
		//iterator
		Iterator it = new IteratorArrayList<>(controller.listarClientes().getListaClientes());
		while (it.hasNext()) {
			ClienteDTO clien = (ClienteDTO) it.next();
			Object[] linha = new Object[] { clien.getNome(), clien.getCpf(), clien.getTelefone(), clien.getEndereco() };
			modelo.addRow(linha);
		}
		
		
		tabela.repaint();
	}

	private class OuvinteJanelaGerenciarClientes implements MouseListener, KeyListener {

		private JanelaGerenciarClientes janela;

		public OuvinteJanelaGerenciarClientes(JanelaGerenciarClientes j) {
			janela = j;
		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setOpaque(true);
			label.setBackground(new Color(30, 255, 20, 220));
		}

		public void mouseExited(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setBackground(new Color(00, 75, 200));
			label.setOpaque(true);
		}

		public void mousePressed(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setOpaque(true);
			label.setBackground(new Color(30, 165, 20, 220));
		}

		public void mouseReleased(MouseEvent e) {
			if (e.getSource() == cadastrar) {
				new JanelaCadastroClientes(janela).setLocationRelativeTo(janela);
			}

			else if (e.getSource() == adicionar) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Selecione algum cliente para realizar essa ação.");
				} else {
					Object codigo = modelo.getValueAt(tabela.getSelectedRow(), 1);
					String cpf = (String) codigo;
					new JanelaCadastroServiço(janela,cpf).setLocationRelativeTo(janela);
				}
			}

			else if (e.getSource() == excluir) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Selecione algum cliente para realizar essa ação.");
				} else {
					Object codigo = modelo.getValueAt(tabela.getSelectedRow(), 1);
					String cpf = (String) codigo;
					ClienteDTO cliente = new ClienteDTO();
					cliente.setCpf(cpf);
					Object[] botoes = { "Sim", "Não" };
					int resposta = JOptionPane.showOptionDialog(null, "Deseja Excluir o Cliente?", "Confirmação",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoes, botoes);
					if (resposta == 0) {
						ControllerCliente controlador = new ControllerCliente();
						controlador.deletarCliente(cliente);
						JOptionPane.showMessageDialog(janela, "Cliente excluido com sucesso !");
						janela.atualizarTabela();
					}

				}
			}else if(e.getSource() == editar) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Selecione algum cliente para realizar essa ação.");
				} else {
					Object codigo = modelo.getValueAt(tabela.getSelectedRow(), 1);
					String cpf = (String) codigo;
					new JanelaEditarClientes(janela,cpf).setLocationRelativeTo(janela);
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

	public static void main(String[] args) {
		new JanelaGerenciarClientes();
	}
}
