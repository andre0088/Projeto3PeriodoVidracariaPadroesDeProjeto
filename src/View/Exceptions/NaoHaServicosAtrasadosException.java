package View.Exceptions;

public class NaoHaServicosAtrasadosException extends Exception{
	public NaoHaServicosAtrasadosException() {
		super("Não há nenhum serviço atrasado.");
	}
}
