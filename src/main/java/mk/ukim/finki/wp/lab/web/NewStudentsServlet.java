package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/newStudent")
public class NewStudentsServlet extends HttpServlet {
    private StudentService studentService;
    private final SpringTemplateEngine templateEngine;

    public NewStudentsServlet(StudentService studentService, SpringTemplateEngine templateEngine) {
        this.studentService = studentService;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<Student> collect = studentService.listAll().stream().filter(Student::isNewStudent).collect(Collectors.toList());
        context.setVariable("students", collect);
        templateEngine.process("newStudent.html", context, resp.getWriter());
    }
}
