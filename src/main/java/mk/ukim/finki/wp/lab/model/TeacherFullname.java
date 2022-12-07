package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.io.Serializable;

@Data

public class TeacherFullname implements Serializable {
    private String name;
    private String surname;

    public TeacherFullname() {
    }

    public TeacherFullname(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
