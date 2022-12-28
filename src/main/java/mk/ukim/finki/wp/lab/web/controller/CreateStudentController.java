package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/createStudent")
public class CreateStudentController {
    private StudentService studentService;

    public CreateStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    protected String getCreateStudentPage(Model model){
        Boolean addStudent = (Boolean) model.getAttribute("addStudent");
        //init
        if (addStudent == null) {
            addStudent = true;
            model.addAttribute("addStudent", addStudent);
        }
        else
            //every other cycle
            //change values, ako e T -> F, ako e F -> T
            addStudent = !addStudent;

        model.addAttribute("bodyContent", "createStudent");
        return "master-template";
    }

    @PostMapping
    protected String saveStudent(@RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam String username,
                                 @RequestParam String password,
                                 HttpServletRequest req){
        studentService.save(username, password, name, surname);

        return "redirect:/AddStudent?courseId="+ req.getSession().getAttribute("chosenCourse");
    }
}
