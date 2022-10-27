package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.StudentDataHolder;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {
    public Student save(Student student){
        if (student==null || student.getUsername().isEmpty()) {
            return null;
        }
        //permits duplicates
        StudentDataHolder.students.removeIf(s->s.getUsername().equals(student.getUsername()));
        StudentDataHolder.students.add(student);
        return student;
    }

    public List<Student> findAllStudents(){
        return StudentDataHolder.students;
    }

    public List<Student> findAllByNameOrSurname(String text){
        return StudentDataHolder.students.stream().filter(s-> s.getName().contains(text) || s.getSurname().contains(text))
                .collect(Collectors.toList());
    }

    public Student findByUsername(String username){
        Optional<Student> first = StudentDataHolder.students.stream().filter(s -> s.getUsername().equals(username)).findFirst();
        if (first.isEmpty())
            throw new NoSuchElementException();
        return first.get();
    }
}
