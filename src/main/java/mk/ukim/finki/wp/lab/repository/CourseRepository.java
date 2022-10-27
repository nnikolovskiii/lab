package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.CourseDataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class CourseRepository {

    public List<Course> findAllCourses(){
        return CourseDataHolder.courses;
    }
    public Course findById(Long courseId) throws  NoSuchElementException{
        //null potential error
        //throw exception
        Optional<Course> first = CourseDataHolder.courses.stream().filter(c -> c.getCourseId().equals(courseId)).findFirst();
        if (first.isEmpty())
            throw new NoSuchElementException();
        return first.get();
    }
    public List<Student> findAllStudentsByCourse(Long courseId){
        return findById(courseId).getStudents();
    }
    public Course addStudentToCourse(Student student, Course course){
        course.getStudents().add(student);
        return course;
    }
}
