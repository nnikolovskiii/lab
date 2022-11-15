package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.NoDescriptionException;
import mk.ukim.finki.wp.lab.model.exceptions.NoNameException;
import mk.ukim.finki.wp.lab.model.exceptions.NoTeacherFoundException;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.apache.tomcat.util.modeler.NoDescriptorRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    //dependency injection
    private CourseRepository courseRepository;
    private StudentService studentService;
    private TeacherService teacherService;

    public CourseServiceImpl(CourseRepository courseRepository, StudentService studentService
    ,TeacherService teacherService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        try {
            return this.findAllStudentsByCourse(courseId);
        }catch (NoSuchElementException exception){
            System.out.println("Course ID "+ courseId+" doesn't exist.");
        }
        return null;
    }

    public List<Student> findAllStudentsByCourse(Long courseId){
        Optional<Course> course = courseRepository.findById(courseId);
        return course.map(Course::getStudents).orElse(null);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Student student = studentService.searchByUsername(username);
        //another try catch not the main point
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            return courseRepository.addStudentToCourse(student, course.get());
        }
        return null;
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAllCourses();
    }

    @Override
    public Optional<Course> getCourse(Long courseId) {
        return courseRepository.findById(Long.valueOf(courseId));
    }

    @Override
    public Course saveCourse(String name, String description, Long id) {
        //vidi dali se prazni name ili description
        if(name.isEmpty())
            throw new NoNameException();
        if(description.isEmpty())
            throw new NoDescriptionException();
        //vidi dali go ima toj profesor
        Teacher teacher = teacherService.findById(id).orElseThrow(() -> new NoTeacherFoundException(id));

        return courseRepository.save(name, description, teacher);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }


}
