package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;


public class JLabelTextos extends JLabel{

	public JLabelTextos(String texto, int x, int y, int z, int w) {
		super(texto);
		setBounds(x,y,z,w);
		setFont(new Font("Consolas",Font.BOLD,16));
		setForeground(Color.white);
//		setHorizontalAlignment(JLabel.RIGHT);
//		setForeground(Color.white);
		
		
	}

	
}
