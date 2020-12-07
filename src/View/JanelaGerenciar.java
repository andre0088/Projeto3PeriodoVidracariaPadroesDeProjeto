package View;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Controller.ControllerRelatorio;
import Controller.ControllerFuncionario;
import DTO.FuncionarioDTO;
import View.Exceptions.NaoHaClientesDevendoException;
import View.Exceptions.NaoHaServicosAtrasadosException;

public class JanelaGerenciar extends JanelaVidro{
	
	private Point point = new Point();
	
	public JanelaGerenciar(String title) {
		super(title,1200,700);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		adicionarJMenus();
		adicionarJLabels();
	}
	
	private OuvinteJanela ouvinte = new OuvinteJanela();
	private JMenuItem sair ;
	private JMenuItem funcionarios;
	private JMenuItem clientes;
	private JMenuItem servicos;
	private JMenuItem relatorioClientes;
	private JMenuItem relatorioServicos;
	private JMenuItem relatorioQuantidade;
	private JMenuItem relatorioLucro;
	
	ControllerFuncionario controlador = new ControllerFuncionario();
	FuncionarioDTO logado = controlador.getUsuarioLogado();
	
	public final void adicionarJMenus() {
		
		JPanel painel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		painel.setBounds(0,25,getWidth(),25);
		
		
		JMenuBar menuBar=new JMenuBar();
		menuBar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});

		JMenu logout =new JMenu("Logout");
		
		sair = new JMenuItem("Sair");
		sair.addActionListener(new OuvinteJanela());
		logout.add(sair);
		
		menuBar.add(logout);
		
		JMenu menuAcesso = new JMenu("Menu de Acesso");
		
		funcionarios = new JMenuItem("Gerenciar Funcionários");
		funcionarios.addActionListener(ouvinte);
		if(logado.getCargo().equalsIgnoreCase("admin") || logado.getCargo().equals("Gerente")) {
			menuAcesso.add(funcionarios);
		}
		
		clientes = new JMenuItem("Gerenciar Clientes");
		clientes.addActionListener(ouvinte);
		menuAcesso.add(clientes);
		
		servicos = new JMenuItem("Gerenciar Serviços");
		servicos.addActionListener(ouvinte);
		menuAcesso.add(servicos);
		
		JMenu menuRelatorios = new JMenu("Menu de Relatórios");
		
		relatorioClientes = new JMenuItem("Clientes Individados");
		relatorioClientes.addActionListener(ouvinte);
		menuRelatorios.add(relatorioClientes);
		
		relatorioServicos = new JMenuItem("Serviços Atrasados");
		relatorioServicos.addActionListener(ouvinte);
		menuRelatorios.add(relatorioServicos);
		
		relatorioQuantidade = new JMenuItem("Quantidade de Serviços Mensais");
		relatorioQuantidade.addActionListener(ouvinte);
		menuRelatorios.add(relatorioQuantidade);
		
		relatorioLucro = new JMenuItem("Ganho Mensal");
		relatorioLucro.addActionListener(ouvinte);
		menuRelatorios.add(relatorioLucro);
		
		menuBar.add(logout);
		menuBar.add(menuAcesso);
		if(logado.getCargo().equalsIgnoreCase("admin") || logado.getCargo().equals("Gerente")) {
			menuBar.add(menuRelatorios);
		}
		painel.add(menuBar);
		getContentPane().add(painel);
		
	}
	
	public final void adicionarJLabels() {
		JLabel bemVindo = new JLabelBemVindo("Bem Vindo!", 10, 200, 230 , 60);
		String[] nome = logado.getNome().split(" ");
		JLabel func = new JLabelBemVindo(nome[0], 10, 240, 230 , 60);

		add(bemVindo);
		add(func);
	}
	
	private class OuvinteJanela implements ActionListener{

		ControllerRelatorio control = new ControllerRelatorio();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == sair) {
				ControllerFuncionario control = new ControllerFuncionario();
				control.fazerLogoff();
				dispose();
				new JanelaLogin();
			}else if(e.getSource()==funcionarios) {
				dispose();
				new JanelaGerenciarFuncionarios();
			}else if(e.getSource()==clientes) {
				dispose();
				new JanelaGerenciarClientes();
			}else if(e.getSource()==servicos) {
				dispose();
				new JanelaGerenciarServicos();
			}else if(e.getSource()==relatorioClientes) {
				try {
					control.relatorioClientesDevendo();
				} catch (NaoHaClientesDevendoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}else if(e.getSource()==relatorioServicos) {
				try {
					control.relatorioServicosAtrasados();
				} catch (NaoHaServicosAtrasadosException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}else if(e.getSource()==relatorioQuantidade) {
				new JanelaRelatorios("Quantidade");
			}else {
				new JanelaRelatorios("Lucro");
			}
		}
		
	}
	public static void main(String[] args) {
		new JanelaGerenciarClientes();
	}
}
