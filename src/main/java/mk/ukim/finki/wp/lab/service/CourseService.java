package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;

public interface CourseService {
    List<Student> listStudentsByCourse(Long courseId);
    Course addStudentInCourse(String username, Long courseId);
    //extra functions
    List<Course> listAll();
    Course getCourse(Long courseId);
    //extra functions 2.0
    Course saveCourse(String name, String description, Long id);

    void deleteById(Long id);
}
