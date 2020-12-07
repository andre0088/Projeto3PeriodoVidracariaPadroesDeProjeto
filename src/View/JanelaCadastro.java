package View;

public abstract class JanelaCadastro extends JanelaVidro{
	
	public JanelaCadastro(String titulo) {
		super(titulo,800,500);
		
		repaint();
	}

	public abstract void addLabels();
	
	public abstract void addTextfields();

	public abstract void addBotoes();
}