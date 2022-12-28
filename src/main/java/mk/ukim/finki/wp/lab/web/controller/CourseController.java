package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = { "/courses"})
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) Long id,
                                 Model model){
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        if (id != null){
            Optional<Teacher> teacher = teacherService.findById(id);
            teacher.ifPresent(value -> model.addAttribute("teacher", value));

        }

        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        List<Course> courses = courseService.listAll();
        courses = courses.stream().sorted(Comparator.comparing(Course::getName)).collect(Collectors.toList());
        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "listCourses");
        return "master-template";
    }

    //името, описот за курсот, како и id на професорот кој е одговорен за тој курс
    @PostMapping
    public String saveNewCourse(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Long id){


        //the id will be added when it will be created
        Course course = new Course(name, description);
        try {
            courseService.saveCourse(course, id);
        }catch (RuntimeException exception){
            return "redirect:/courses?error=" + exception.getMessage();
        }



        return "redirect:/courses";
    }
    //http://localhost:8080/AddStudent?_method=DELETE
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id){
        this.courseService.deleteById(id);
        return "redirect:/courses";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditCoursePage(@PathVariable Long id, Model model){
        if(this.courseService.getCourse(id).isPresent()){
            Course course = this.courseService.getCourse(id).get();
            List<Teacher> teachers = this.teacherService.findAll();
            model.addAttribute("course", course);
            model.addAttribute("teachers", teachers);
            return "add-course";
        }
        return "redirect:/courses?error=CourseNotFound";

    }

    @GetMapping("/add-course")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddCoursePage(Model model){
        List<Teacher> teachers = this.teacherService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("bodyContent", "add-course");
        return "master-template";
    }


    //courseId to check if we clicked the edited button
    @PostMapping("/add")
    public String saveCourse(@RequestParam(required = false) Long courseId,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Long id)
    {

        try {
            Course course = null;
            if (courseId == null)
                course = new Course(name,description);
            else {
                course = courseService.getCourse(courseId).get();
                course.setName(name);
                course.setDescription(description);
            }
            courseService.saveCourse(course ,id);
        }catch (RuntimeException exception)
        {
            return "redirect:/courses?error=" + exception.getMessage();
        }

        return "redirect:/courses";
    }
}
