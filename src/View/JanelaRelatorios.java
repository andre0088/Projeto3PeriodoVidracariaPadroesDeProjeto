package View;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import Controller.ControllerRelatorio;
import View.Exceptions.FaltaDeDinheiroException;
import View.Exceptions.NenhumServicoException;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JanelaRelatorios extends JanelaVidro{
	
	private String tipo;
	
	public JanelaRelatorios(String tipo) {
		super("Janela de relatórios",400,400);
		this.tipo=tipo;
		addLabels();
		addFields();
		addBotoes();
		
		
		setVisible(true);
	}

	public void addLabels() {
		JLabelTextos lbMes = new JLabelTextos("<html><div align=right>Selecione <br>o mês: </div></html>", 40, 190, 100, 50);
		JLabelTextos lbAno= new JLabelTextos("<html><div align=right>Digite <br>o ano: </div></html>", 40, 240, 100, 50);
		
		lbMes.setHorizontalAlignment(JLabel.RIGHT);
		lbAno.setHorizontalAlignment(JLabel.RIGHT);
		
		add(lbMes);
		add(lbAno);
	}
	JComboBox<String> mes; 
	JFormattedTextField ano; 

	public void addFields() {
		Font font = new Font("Consolas",Font.BOLD,15);
		
		String[] meses = {"Janeiro", "Fevereiro","Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
		mes = new JComboBox(meses);
		mes.setFont(font);
		mes.setBounds(160, 200, 150, 30);
		
		try {
			MaskFormatter mask = new MaskFormatter("####");
			ano = new JFormattedTextField(mask);
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String sdt = String.valueOf(sdf.format(dt));
			ano.setText(sdt); 
			ano.setFont(font);
			ano.setBounds(160, 250, 150, 30);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		add(mes);
		add(ano);
	}
		
	JButton cancelar;
	JButton gerar;
	
	public void addBotoes() {
		cancelar = new JButton("Cancelar");
		cancelar.setBounds(100, 310, 90, 40);
		cancelar.addActionListener(new OuvinteDoBtVoltar(this));

		gerar = new JButton("<html><div align=center>Gerar<br>Relatorio</div></html>");
		gerar.setBounds(210, 310, 100, 40);
		gerar.addActionListener(new OuvinteBtGerar(this));
		
		add(cancelar);
		add(gerar);
	}
	
	private class OuvinteBtGerar implements ActionListener{

		private JanelaRelatorios janela;
		
		public OuvinteBtGerar(JanelaRelatorios janela) {
			this.janela=janela;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ControllerRelatorio control = new ControllerRelatorio();
			int mesRelatorio = (mes.getSelectedIndex()+1);
			int anoRelatorio = Integer.parseInt(ano.getText());
			if(tipo.equals("Quantidade")) {
				try {
					control.quantidadesServicosMensais(mesRelatorio, anoRelatorio);
					janela.dispose();
				} catch (NumberFormatException | NenhumServicoException e1) {
					JOptionPane.showMessageDialog(janela, e1.getMessage());
				}
			}else {
				try {
					control.ganhoDeDinheiroMensal(mesRelatorio, anoRelatorio);
					janela.dispose();
				} catch (NumberFormatException | NenhumServicoException | FaltaDeDinheiroException e1) {
					JOptionPane.showMessageDialog(janela, e1.getMessage());
				}
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		new JanelaRelatorios("tipo a");
	}
}