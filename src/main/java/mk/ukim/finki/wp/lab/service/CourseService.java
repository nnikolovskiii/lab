package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Student> listStudentsByCourse(Long courseId);
    Course addStudentInCourse(String username, Long courseId);
    //extra functions
    List<Course> listAll();
    Optional<Course> getCourse(Long courseId);
    //extra functions 2.0
    Course saveCourse(Course course, Long id);

    void deleteById(Long id);

    List<Student> findAllStudentsByCourse(Long courseId);
}
