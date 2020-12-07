package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class JLabelBemVindo extends JLabel{
	public JLabelBemVindo(String title,int x,int y,int z,int h) {
		this.setForeground(Color.white);
		this.setText(title);
		this.setBounds(x,y,z,h);
		this.setFont(new Font("Consolas", Font.BOLD, 28));
		this.setHorizontalAlignment(JLabel.CENTER);
	}
}
