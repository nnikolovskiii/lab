package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.repository.impl.InMemoryStudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    //dependencies
    private final StudentRepository studentRepository;


    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        return studentRepository.findAllByNameOrSurname(text, text);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        //must have the username key
        if (username==null || username.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Student student = new Student(username, password, name, surname, true);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student searchByUsername(String username) {
        //vaka moze da se handle-ne Optional
        return studentRepository.findById(username)
                .orElseThrow(NoSuchElementException::new);

    }
}
