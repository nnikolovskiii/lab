package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping(value = "/createTeacher")
public class CreateTeacherController {
    private final TeacherService teacherService;

    public CreateTeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCreateTeacherSite(){
        return "add-teacher";
    }

    @PostMapping
    public String saveTeacher(@RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          LocalDate dayOfEmployment){
        teacherService.addTeacher(name, surname, dayOfEmployment);
        return "redirect:listCourses";
    }
}
