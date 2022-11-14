package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/", "/courses"})
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }


        model.addAttribute("courses", courseService.listAll());
        return "listCourses";
    }

    //името, описот за курсот, како и id на професорот кој е одговорен за тој курс
    @PostMapping
    public String saveCourse(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Long id){
        try {
            courseService.saveCourse(name, description, id);
        }catch (RuntimeException exception){
            return "redirect:/courses?error=" + exception.getMessage();
        }
        return "redirect:/courses";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id){
        this.courseService.deleteById(id);
        return "redirect:/courses";
    }
}
