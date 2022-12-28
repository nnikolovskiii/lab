package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(value = "/AddStudent")
public class ListStudentController extends HttpServlet {
    private final StudentService studentService;
    private final CourseService coursesService;

    public ListStudentController(StudentService studentService, CourseService coursesService) {
        this.studentService = studentService;
        this.coursesService = coursesService;
    }

    @GetMapping
    protected String getAddStudentPage(@RequestParam(required = false) String courseId,
                         HttpServletRequest req,
                         Model model){
        //add the list to all students using StudentService to the context
        Long id = null;
        if (courseId != null) {
            req.getSession().setAttribute("chosenCourse", Long.valueOf(courseId));
        }

        id = (Long) req.getSession().getAttribute("chosenCourse");

        model.addAttribute("students", studentService.listAll());
        model.addAttribute("courseId", courseId);



        //find by id
        Optional<Course> course = coursesService.getCourse(id);
        course.ifPresent(value -> req.getSession().setAttribute("course", value));

        model.addAttribute("bodyContent", "listStudents");
        return "master-template";
    }
}
