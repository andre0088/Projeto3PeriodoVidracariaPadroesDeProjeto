package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

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
import Controller.ControllerServico;
import DTO.ClienteDTO;
import DTO.ServicoDTO;

public class JanelaGerenciarServicosGerais extends JanelaGerenciar {
	private OuvinteJanelaGerenciar ouvinte = new OuvinteJanelaGerenciar(this);

	public JanelaGerenciarServicosGerais() {
		super("Lima Vidros - Gerenciamento de Serviços em Geral");
		adicionarJLabel();
		addFiltro();
		adicionarJTable();
		atualizarTabela();
		this.setVisible(true);
	}

	private JLabel editar;
	private JLabel excluir;
	private JLabel voltar;

	public void adicionarJLabel() {
		editar = new JLabelPersonalizado("<html><div align=center>Editar<br>Serviço</div></html>", 10, 330, 230, 60);
		editar.addMouseListener(ouvinte);
		excluir = new JLabelPersonalizado("<html><div align=center>Excluir<br>Serviço</div></html>", 10, 410, 230, 60);
		excluir.addMouseListener(ouvinte);
		voltar = new JLabelPersonalizado("<html><div align=center>Voltar</div></html>", 10, 490, 230, 60);
		voltar.addMouseListener(ouvinte);
		add(editar);
		add(excluir);
		add(voltar);
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

		JScrollPane conteiner = new JScrollPane(tabela);
		conteiner.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		conteiner.setBounds(250, 240, 920, 440);
		add(conteiner);
	}

	public void atualizarTabela() {
		ControllerServico controller = new ControllerServico();
		ControllerCliente controlCli = new ControllerCliente();

		modelo = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modelo.addColumn("ID");
		modelo.addColumn("Cliente");
		modelo.addColumn("Endereço");
		modelo.addColumn("Preço Total");
		modelo.addColumn("Preço Pago");
		modelo.addColumn("Data Pedido");
		modelo.addColumn("Data Entrega");
		modelo.addColumn("Status");
		modelo.addColumn("Descrição");

		Iterator it = new IteratorArrayList<>(controller.listarServicos().getListaServicos());
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		tabela.setModel(modelo);

		while (it.hasNext()) {
			ServicoDTO ser = (ServicoDTO) it.next();
			String nome = "";
			for (ClienteDTO cli : controlCli.listarClientes().getListaClientes()) {
				if (cli.getCpf().equals(ser.getCpf())) {
					nome = cli.getNome();
				}
			}

			Object[] linha = new Object[] { ser.getId(), nome, ser.getEndereco(), ser.getPrecoCompleto(),
					ser.getQtdPago(), formato.format(ser.getDataPedido()), formato.format(ser.getDataEntrega()),
					ser.getStatus(), ser.getDescricao() };
			modelo.addRow(linha);

		}

		// mudar Font da tabela
		DefaultTableCellRenderer letrasTabela = new DefaultTableCellRenderer() {
			public void setValue(Object value) {
				setFont(new Font("Consolas", Font.BOLD, 15));
				super.setValue(value);
			}
		};
		int[] largura = { 50, 220, 330, 90, 90, 90, 90, 100, 700 };
		for (int i = 0; i < 9; i++) {
			tabela.getColumnModel().getColumn(i).setPreferredWidth(largura[i]);
			tabela.getColumnModel().getColumn(i).setCellRenderer(letrasTabela);
		}
		tabela.repaint();
	}

	private class OuvinteJanelaGerenciar implements MouseListener, KeyListener {

		private JanelaGerenciarServicosGerais janela;

		public OuvinteJanelaGerenciar(JanelaGerenciarServicosGerais j) {
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
			if (e.getSource() == voltar) {
				new JanelaGerenciarServicos().setLocationRelativeTo(janela);
				dispose();
			} else if (e.getSource() == excluir) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Selecione algum serviço para realizar essa ação.");
				} else {
					Object codigo = modelo.getValueAt(tabela.getSelectedRow(), 0);
					int id = (int)codigo;
					ServicoDTO servico = new ServicoDTO();
					servico.setId(id);
					Object[] botoes = { "Sim", "Não" };
					int resposta = JOptionPane.showOptionDialog(null, "Deseja Excluir o Serviço?", "Confirmação",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, botoes, botoes);
					if (resposta == 0) {
						ControllerServico controlador = new ControllerServico();
						controlador.deletarServico(servico);
						JOptionPane.showMessageDialog(janela, "Servico excluido com sucesso !");
						janela.dispose();
						new JanelaGerenciarServicosGerais().setLocationRelativeTo(null);
					}

				}
			} else if (e.getSource() == editar) {
				if (tabela.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Selecione algum serviço para realizar essa ação.");
				} else {
					ControllerServico control = new ControllerServico();
					Object codigo = modelo.getValueAt(tabela.getSelectedRow(), 0);
					int id = (int) codigo;
					String cpf = "";
					for (ServicoDTO servico : control.listarServicos().getListaServicos()) {
						if (servico.getId()==id) {
							cpf = servico.getCpf();
						}
					}
					new JanelaEditarServicos(janela, id, cpf).setLocationRelativeTo(janela);
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
		new JanelaGerenciarServicosGerais();
	}
}
