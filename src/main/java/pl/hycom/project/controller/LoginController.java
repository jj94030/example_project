package pl.hycom.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.hycom.project.service.Employee;
import pl.hycom.project.service.EmployeeService;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final EmployeeService employeeService;

    @Autowired
    public LoginController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        Employee employee = new Employee();
        modelAndView.addObject("employee", employee);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView createNewEmployee(@Valid Employee employee, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Employee employeeExists = employeeService.findEmployeeByEmail(employee.getEmail());
        if (employeeExists != null) {
            bindingResult
                    .rejectValue("email", "error.employee",
                            "There is already a employee registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            employeeService.saveEmployee(employee);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("employee", new Employee());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.findEmployeeByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
}
