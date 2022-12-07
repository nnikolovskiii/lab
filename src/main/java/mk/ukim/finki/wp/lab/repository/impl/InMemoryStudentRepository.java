package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryStudentRepository {
    public Student save(Student student){
        if (student==null || student.getUsername().isEmpty()) {
            return null;
        }
        //permits duplicates
        DataHolder.students.removeIf(s->s.getUsername().equals(student.getUsername()));
        DataHolder.students.add(student);
        return student;
    }

    public List<Student> findAllStudents(){
        return DataHolder.students;
    }

    public List<Student> findAllByNameOrSurname(String text){
        return DataHolder.students.stream().filter(s-> s.getName().contains(text) || s.getSurname().contains(text))
                .collect(Collectors.toList());
    }

    public Student findByUsername(String username){
        Optional<Student> first = DataHolder.students.stream().filter(s -> s.getUsername().equals(username)).findFirst();
        if (first.isEmpty())
            throw new NoSuchElementException();
        return first.get();
    }
}
