package View.Exceptions;

public class NaoHaServicosAtrasadosException extends Exception{
	public NaoHaServicosAtrasadosException() {
		super("N�o h� nenhum servi�o atrasado.");
	}
}
