package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//data adds getters and setters
@Data
public class Course {
    private Long id;
    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
    private Teacher teacher;

    public Course(Long courseId, String name, String description) {
        this.id = (long) (Math.random() * 1000);
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        //init array
        students = new ArrayList<>();
    }

    public Course(Long courseId, String name, String description, List<Student> students) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.students = students;
    }
}
