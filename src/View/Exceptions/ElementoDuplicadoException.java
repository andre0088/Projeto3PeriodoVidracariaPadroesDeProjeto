package View.Exceptions;

public class ElementoDuplicadoException extends Exception{
	public ElementoDuplicadoException() {
		super("Esse n�mero de identifica��o j� existe.");
	}
}
