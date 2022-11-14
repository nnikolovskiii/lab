package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//site novi studenti sto se dodavaat

@WebServlet(urlPatterns = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {
    //why final?
    private final SpringTemplateEngine templateEngine;
    private CourseService courseService;

    public StudentEnrollmentSummary(SpringTemplateEngine templateEngine,
                                    CourseService courseService) {
        this.templateEngine = templateEngine;
        this.courseService = courseService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //this servlet must be accessed by a post
        resp.sendRedirect("/listCourses");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        long courseId = (long) req.getSession().getAttribute("chosenCourse");

        //if there is no student selected we want to redirect to /AddStudent
        if (req.getParameter("student") == null){
            resp.sendRedirect("/AddStudent?courseId="+courseId);
            return;
        }
        //get the Student username from the form
        String studentUsername = req.getParameter("student");

        //don't use objects just keys
        courseService.addStudentInCourse(studentUsername, courseId);

        //add list of students by course in context
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("students", courseService.listStudentsByCourse(courseId));
        //add a method to the service to get the name of a given courseId
        context.setVariable("courseName", courseService.getCourse(courseId).getName());
        this.templateEngine.process("studentsInCourse.html", context, resp.getWriter());
    }
}
