package View.Exceptions;

public class ElementoDuplicadoException extends Exception{
	public ElementoDuplicadoException() {
		super("Esse número de identificação já existe.");
	}
}
