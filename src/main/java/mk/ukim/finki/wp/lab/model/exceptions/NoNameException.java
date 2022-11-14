package mk.ukim.finki.wp.lab.model.exceptions;

public class NoNameException extends RuntimeException{
    public NoNameException() {
        super("No description entered");
    }
}
