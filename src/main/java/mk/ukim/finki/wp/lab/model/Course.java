package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

//data adds getters and setters
@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Student> students;

    @ManyToOne
    private Teacher teacher;
    @Enumerated(value = EnumType.STRING)
    private Type type;

    public Course(){
        students = new ArrayList<>();
    }

    public Course(String name, String description) {
        //se povikuva prazniot konstruktor
        this();
        this.name = name;
        this.description = description;
        //init array
    }


    public Course(String name, String description, Teacher teacher) {
        this();
        this.name = name;
        this.description = description;
        this.teacher = teacher;
    }

    public Course(long parseLong, String description) {

    }

    public Course(Course course, List<Student> students) {
        course.name = name;
        course.description = description;
        course.teacher = teacher;
        this.students = students;

    }

    public Course(Long courseId, String name, String description) {
        this();
        this.id = courseId;
        this.name = name;
        this.description = description;
    }

    public boolean hasTeacher(Teacher t){
        if (teacher == null)
            return false;
        return Objects.equals(teacher.getId(), t.getId());
    }

    public void addStudent(Student student) {
        students.removeIf(s->s.getUsername().equals(student.getUsername()));
        students.add(student);
    }
}
