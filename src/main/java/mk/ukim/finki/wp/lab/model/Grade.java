package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Character grade;
    @OneToOne
    private Student student;
    @OneToOne
    private Course course;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public Grade() {
    }

    public Grade(Course course, Student student) {
        this.course= course;
        this.student = student;
    }

    public Grade(LocalDateTime timestamp, Character grade, Course course, Student student) {
        this.course= course;
        this.student = student;
        this.timestamp = timestamp;
        this.grade = grade;
    }
}
