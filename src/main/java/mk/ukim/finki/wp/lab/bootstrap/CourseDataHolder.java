package mk.ukim.finki.wp.lab.bootstrap;

import lombok.Getter;
import mk.ukim.finki.wp.lab.model.Course;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class CourseDataHolder {
    public static List<Course> courses;

    @PostConstruct
    public void init() {
        courses = new ArrayList<>();
        File file = new File("C:\\Users\\Dell\\IdeaProjects\\lab\\src\\main\\resources\\static\\courses") ;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            reader.lines().forEach(str->{
                String[] split = str.split("\\s+");
                courses.add(new Course(Long.parseLong(split[0]), split[1], split[2]));
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
