package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseServiceImpl implements CourseService {
    //dependency injection
    private CourseRepository courseRepository;
    private StudentService studentService;

    public CourseServiceImpl(CourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        try {
            return courseRepository.findAllStudentsByCourse(courseId);
        }catch (NoSuchElementException exception){
            System.out.println("Course ID "+ courseId+" doesn't exist.");
        }
        return null;
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Student student = studentService.searchByUsername(username);
        //another try catch not the main point
        Course course = courseRepository.findById(courseId);
        return courseRepository.addStudentToCourse(student, course);
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAllCourses();
    }

    @Override
    public Course getCourse(Long courseId) {
        return courseRepository.findById(Long.valueOf(courseId));
    }


}
