package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/AddStudent")
public class ListStudentServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private StudentService studentService;

    public ListStudentServlet(SpringTemplateEngine springTemplateEngine,
                              StudentService studentService) {
        this.springTemplateEngine = springTemplateEngine;
        this.studentService = studentService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //if there is no parameter passed redirect
        if (req.getParameter("courseId") == null){
            resp.sendRedirect("/listCourses");
            return;
        }
        //add the list to all students using StudentService to the context
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("students", studentService.listAll());

        String courseId = req.getParameter("courseId");
        context.setVariable("courseId", courseId);
        //place in the Session object for every servlet to use
        req.getSession().setAttribute("chosenCourse", Long.valueOf(courseId));
        //pass it to the listStudents.html to select a student that we'll be added to the course
        springTemplateEngine.process("listStudents.html", context, resp.getWriter());
    }
}
