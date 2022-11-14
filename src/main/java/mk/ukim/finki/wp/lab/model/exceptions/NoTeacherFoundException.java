package mk.ukim.finki.wp.lab.model.exceptions;

public class NoTeacherFoundException extends RuntimeException{
    public NoTeacherFoundException(Long id) {
        super(String.format("Teacher with id: %d is not found", id));
    }
}
