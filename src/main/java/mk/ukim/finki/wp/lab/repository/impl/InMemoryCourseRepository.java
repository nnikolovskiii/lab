package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class InMemoryCourseRepository {

    public List<Course> findAllCourses(){
        return DataHolder.courses;
    }
    public Optional<Course> findById(Long courseId) throws  NoSuchElementException{
        //null potential error
        //throw exception
        return DataHolder.courses.stream().filter(c -> c.getId().equals(courseId)).findFirst();
    }
    public Course addStudentToCourse(Student student, Course course){
        //remove with the same username in order to have no duplicates
        course.getStudents().removeIf(s->s.getUsername().equals(student.getUsername()));
        course.getStudents().add(student);
        return course;
    }

    public Course save(String name, String description, Teacher teacher) {
        Course course = new Course(name, description, teacher);
        //remove duplicate
        DataHolder.courses.removeIf(c -> c.getName().equals(name));
        DataHolder.courses.add(course);
        return course;
    }

    public void deleteById(Long id) {
        DataHolder.courses.removeIf(i -> i.getId().equals(id));
    }
}
