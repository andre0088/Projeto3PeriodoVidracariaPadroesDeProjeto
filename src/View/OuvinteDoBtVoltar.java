package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class OuvinteDoBtVoltar implements ActionListener{

	private JFrame janela;
	
	public OuvinteDoBtVoltar(JFrame janela){
		this.janela=janela;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		janela.dispose();
	}

}
