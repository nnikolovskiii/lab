package mk.ukim.finki.wp.lab.bootstrap;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    int i =0;
    public static List<Course> courses;
    public static List<Student> students;
    public static List<Teacher> teachers;

    @PostConstruct
    public void init() {
       /* Teacher t1 = new Teacher("Gjorgi", "Madzarov");
        Teacher t2 = new Teacher("Marija", "Mihova");
        Teacher t3 = new Teacher("Dimitar", "Trajanov");
        Teacher t4 = new Teacher("Vladimir", "Zdravevski");
        Teacher t5 = new Teacher("Verica", "Bakeva");
        teachers = new ArrayList<>();
        teachers.add(t1);
        teachers.add(t2);
        teachers.add(t3);
        teachers.add(t4);
        teachers.add(t5);
        //adding courses
        courses = new ArrayList<>()*/
        File file = new File("C:\\Users\\Dell\\IdeaProjects\\lab\\src\\main\\resources\\static\\courses") ;
        /*try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            reader.lines().forEach(str->{
                String[] split = str.split("\\s+");
                courses.add(new Course(split[0], split[1], teachers.get(i)));
                i++;
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //adding students
        students = new ArrayList<>();
        file = new File("C:\\Users\\Dell\\IdeaProjects\\lab\\src\\main\\resources\\static\\students");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            bufferedReader.lines().forEach(str->{
                String[] split = str.split("\\s+");
                students.add(new Student(split[0], split[1], split[2], split[3], false));
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //adding teachers


    }
}
