package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class CourseRepository {

    public List<Course> findAllCourses(){
        return DataHolder.courses;
    }
    public Course findById(Long courseId) throws  NoSuchElementException{
        //null potential error
        //throw exception
        Optional<Course> first = DataHolder.courses.stream().filter(c -> c.getCourseId().equals(courseId)).findFirst();
        if (first.isEmpty())
            throw new NoSuchElementException();
        return first.get();
    }
    public List<Student> findAllStudentsByCourse(Long courseId){
        return findById(courseId).getStudents();
    }
    public Course addStudentToCourse(Student student, Course course){
        //remove with the same username in order to have no duplicates
        course.getStudents().removeIf(s->s.getUsername().equals(student.getUsername()));
        course.getStudents().add(student);
        return course;
    }
}
