package mk.ukim.finki.wp.lab.model.exceptions;

public class NoDescriptionException extends RuntimeException{
    public NoDescriptionException() {
        super("No description entered");
    }
}
