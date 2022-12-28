package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.GradeRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public GradeServiceImpl(GradeRepository repository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.gradeRepository = repository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Map<String, Grade> getGrades(List<Student> students, Long courseId) {
        Map<String, Grade> map = new HashMap<>();
        Course course = courseRepository.findById(courseId).orElseThrow(NoSuchElementException::new);

        students.forEach(s->{
            Grade grade = gradeRepository.findByStudentAndCourse(s, course);
            if (grade != null)
                map.put(s.getUsername(), grade);
        });

        return map;
    }


    @Override
    public Grade save(LocalDateTime timestamp, String grade, Course course, String studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(NoSuchElementException::new);
        if (grade.length()>1){
            throw new NoSuchElementException();
        }
        Character c = grade.charAt(0);
        Grade gradeObj = new Grade(timestamp, c, course, student);
        return gradeRepository.save(gradeObj);
    }

    @Override
    public void deleteById(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public Optional<Grade> getGrade(Long id) {
        return gradeRepository.findById(id);
    }

    @Override
    public void save(Long id, String grade, LocalDateTime timestamp) {
        Grade gradeObj = gradeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if (grade.length()>1){
            throw new NoSuchElementException();
        }
        gradeObj.setGrade(grade.charAt(0));
        gradeObj.setTimestamp(timestamp);
        gradeRepository.save(gradeObj);
    }

    @Override
    public Character getGradeForStudent(Student s) {
        return null;
    }

    @Override
    public List<Student> findByGradeBetween(String from1, String to1) {
        return null;
    }



}
