package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity
public class Student {
    @Id
    private String username;
    private String password;
    private String name;
    private String surname;
    private boolean newStudent;

    public Student(String username, String password, String name, String surname, boolean newStudent) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.newStudent = newStudent;
    }

    public Student() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(username, student.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
