package mk.ukim.finki.wp.lab.bootstrap;

import lombok.Getter;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class StudentDataHolder {
    public static List<Student> students;

    @PostConstruct
    public void init() {
        students = new ArrayList<>();
        File file = new File("C:\\Users\\Dell\\IdeaProjects\\lab\\src\\main\\resources\\static\\students");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            bufferedReader.lines().forEach(str->{
                String[] split = str.split("\\s+");
                students.add(new Student(split[0], split[1], split[2], split[3]));
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
