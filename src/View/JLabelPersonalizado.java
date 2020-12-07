package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class JLabelPersonalizado extends JLabel{
	public JLabelPersonalizado(String title, int x, int y, int z, int h) {
		this.setText(title);
		this.setBounds(x,y,z,h);
		this.setFont(new Font("Consolas", Font.BOLD, 20));
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setOpaque(true);
		this.setBackground(new Color(00,75,200));
		this.setForeground(Color.WHITE);
		this.setHorizontalAlignment(JLabel.CENTER);
	}
}
