package mk.ukim.finki.wp.lab.model;

import lombok.Data;

@Data
public class Student {
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
}
