package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Connection.Conexao;

public class JanelaVidro extends JFrame{
	
	
	private Point point = new Point();
	private OuvinteDaJanelaVidro ouvinte = new OuvinteDaJanelaVidro(this);
	
	public JanelaVidro(String titulo, int comprimento, int altura) {
		setSize(comprimento,altura);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);
		setUndecorated(true);
		setBackground(new Color(60,100,160,190));
		setIconImage(new ImageIcon(getClass().getResource("/LogoFinal.png")).getImage());
		
		addWindowListener(new WindowAdapter() {

		});
		
		addBarraDeTitulos(titulo);
		
	}

	private JLabel fechar = new JLabel();
	private JLabel minimizar = new JLabel();
	
	private void addBarraDeTitulos(String titulo) {
		JLabelTextos lbTitulo = new JLabelTextos(titulo, 5, 0, getWidth(), 30);
		
		JPanel painel = new JPanel();
		painel.setBounds(0, 0, getWidth(), 30);
		painel.setLayout(null);
		painel.add(lbTitulo);
		painel.setBackground(new Color(40,40,40));
		// ouvinte de mover a janela
		painel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});
		painel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});
		
		
		fechar.setBounds((painel.getWidth() - 30), 0, 30, 30);
		ImageIcon imagem = new ImageIcon(getClass().getResource("/botaoFechar.png"));
		Image redimencionada = imagem.getImage().getScaledInstance(fechar.getWidth(), fechar.getHeight(),
				Image.SCALE_SMOOTH);
		fechar.setIcon(new ImageIcon(redimencionada));
		fechar.addMouseListener(ouvinte);
		fechar.setName("fechar");

		minimizar.setBounds((getWidth() - 63), 0, 30, 30);
		imagem = new ImageIcon(getClass().getResource("/botaoMinimizar.png"));
		redimencionada = imagem.getImage().getScaledInstance(minimizar.getWidth(), minimizar.getHeight(),
				Image.SCALE_SMOOTH);
		minimizar.setIcon(new ImageIcon(redimencionada));
		minimizar.addMouseListener(ouvinte);
		minimizar.setName("minimizar");

		JLabel logo = new JLabel("LIMA VIDROS");
		logo.setOpaque(true);
		logo.setBackground(new Color(0, 75, 255, 150));
		logo.setForeground(new Color(30,255,20,220));
		logo.setFont(new Font("Concolas", Font.BOLD, 50));
		logo.setHorizontalAlignment(JLabel.CENTER);
		logo.setBounds(0, 75, getWidth(), 100);
		logo.setBorder(BorderFactory.createEtchedBorder());
		
		
		painel.add(fechar);
		painel.add(minimizar);
		add(painel);
		add(logo);
	}
	protected class OuvinteDaJanelaVidro implements MouseListener {
		private JFrame janela;

		public OuvinteDaJanelaVidro(JFrame j) {
			janela = j;
		}

		public void mouseClicked(MouseEvent e) {
//			if (e.getSource() == lbbtFechar) {
//				central.setUsuarioLogado(null);
//				pers.salvarCentral(central, "unica");
//				dispose();
//			} else if (e.getSource() == lbbtMinimizar) {
//				setExtendedState(ICONIFIED);
//			} else if (e.getSource() == logo) {
//				if (central.getUsuarioAdm() == null)
//					JOptionPane.showMessageDialog(janela,
//							"Você precisa cadastrar o Administrador para poder iniciar o sistema!", "Aviso",
//							JOptionPane.INFORMATION_MESSAGE);
//				else {
//					new JanListaBens().setLocationRelativeTo(janela);
//					;
//					dispose();
//				}
//			}
		}

		public void mouseEntered(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setSize(25, 25);
//			JLabel label = (JLabel) e.getSource();
		}

		public void mouseExited(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setSize(30, 30);
		}

		public void mousePressed(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setSize(35, 35);
//			label.setOpaque(true);
//			label.setBackground(new Color(200, 200, 200));
		}

		public void mouseReleased(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			if (label == fechar) {
				dispose();
			} else if (label == minimizar) {
				label.setSize(30, 30);
				setExtendedState(ICONIFIED);
			} else
				label.setSize(30, 30);

		}

	}
}
