package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeacherService {
    public List<Teacher> findAll();
    //optional moze da stoi ovde
    public Optional<Teacher> findById(Long id);

    public Teacher addTeacher(String name, String surname, LocalDate dayOfEmployment);

}
