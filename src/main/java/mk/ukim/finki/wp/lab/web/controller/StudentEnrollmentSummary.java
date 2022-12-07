package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//site novi studenti sto se dodavaat

@Controller
@RequestMapping(value = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {
    private final CourseService courseService;
    private final GradeService gradeService;

    public StudentEnrollmentSummary(CourseService courseService, GradeService gradeService) {
        this.courseService = courseService;
        this.gradeService = gradeService;
    }

    public void settingUpModel(Model model, Long courseId){
        List<Student> students = courseService.listStudentsByCourse(courseId);
        Map<String, Grade> grades = gradeService.getGrades(students, courseId);
        model.addAttribute("students", students);
        model.addAttribute("grades", grades );

        model.addAttribute("courseName", courseService.getCourse(courseId).get().getName());
    }

    @GetMapping
    protected String getStudentEnrollmentSummaryPage(Model model,
                                                   HttpServletRequest req){
        long courseId = (long) req.getSession().getAttribute("chosenCourse");

        settingUpModel(model, courseId);

        return "studentsInCourse.html";
    }

    @PostMapping
    protected String saveStudentToCourse(@RequestParam String student,
                                         HttpServletRequest req,
                                         Model model){

        long courseId = (long) req.getSession().getAttribute("chosenCourse");
        if (student == null){
            return "redirect:/AddStudent?courseId=" + courseId;
        }
        courseService.addStudentInCourse(student, courseId);

        settingUpModel(model, courseId);
       return getStudentEnrollmentSummaryPage(model, req);
    }

    @PostMapping("/lol")
    public String filter(@RequestParam(required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                     LocalDateTime from,
                         @RequestParam(required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                     LocalDateTime to,
                         Model model,
                         HttpServletRequest req){
        long courseId = (long) req.getSession().getAttribute("chosenCourse");
        settingUpModel(model, courseId);

        if (from != null && to!=null){
            Map<String, Grade> grades = (Map<String, Grade>) model.getAttribute("grades");
            List<Student> students = (List<Student>) model.getAttribute("students");
            List<Student> collect = students.stream().filter(s -> {
                if (grades.containsKey(s.getUsername())) {
                    Grade grade = grades.get(s.getUsername());
                    return grade.getTimestamp().isBefore(to) && grade.getTimestamp().isAfter(from);
                }
                return false;
            }).collect(Collectors.toList());
            model.addAttribute("students", collect);

        }
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        return "studentsInCourse.html";
    }
}
