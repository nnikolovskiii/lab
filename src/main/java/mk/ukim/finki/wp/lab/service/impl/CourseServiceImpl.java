package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.NoDescriptionException;
import mk.ukim.finki.wp.lab.model.exceptions.NoNameException;
import mk.ukim.finki.wp.lab.model.exceptions.NoTeacherFoundException;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.repository.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service

public class CourseServiceImpl implements CourseService {
    //dependency injection
    //gi smeniv site service da se repository
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;

        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
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
        Course course = courseRepository.findById(courseId).orElseThrow(NoSuchElementException::new);
        return course.getStudents();
    }

    @Override
    @Transactional
    public Course addStudentInCourse(String username, Long courseId) {
        //moze ke treba da stavam custom exception
        Student student = studentRepository.findById(username)
                .orElseThrow(NoSuchElementException::new);

        Optional<Course> byId = courseRepository.findById(courseId);
        if (byId.isPresent()) {
            //dodavam direktno na kursot
            Course course = byId.get();
            course.addStudent(student);
            //go updatenuvam kursot
            courseRepository.save(course);
        }

        return null;
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourse(Long courseId) {
        return courseRepository.findById(Long.valueOf(courseId));
    }

    @Override
    public Course saveCourse(Course course,  Long id) {
        //vidi dali se prazni name ili description
        //I don't know if this is even needed
        if(course.getName().isEmpty())
            throw new NoNameException();
        if(course.getDescription().isEmpty())
            throw new NoDescriptionException();

        //vidi dali go ima toj profesor
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new NoTeacherFoundException(id));
        course.setTeacher(teacher);

        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }


}
