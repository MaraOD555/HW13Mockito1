package pro.sky.HW13Mockito.EmployeesController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.HW13Mockito.Employees.Employees;
import pro.sky.HW13Mockito.EmployeesService.EmployeesService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeesController {
    private static EmployeesService employeesService;
    public EmployeesController(EmployeesService employeesService) {
        EmployeesController.employeesService = employeesService;
    }
    @GetMapping("/add")
    public Employees addEmployee(@RequestParam("lastName") String lastName,
                                 @RequestParam("firstName") String firstName,
                                 String departmentId, int salaryOfEmployee){
        Employees employee = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        return employeesService.addEmployee(employee);
    }
    @GetMapping("/remove")
    public Employees removeEmployee(@RequestParam("lastName") String lastName,
                                    @RequestParam("firstName") String firstName,
                                    String departmentId, int salaryOfEmployee){
        Employees employee = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        return employeesService.removeEmployee(employee);

    }
    @GetMapping("/find")
    public Employees findEmployee(@RequestParam("lastName") String lastName,
                                  @RequestParam("firstName") String firstName,
                                  String departmentId, int salaryOfEmployee){
        Employees employee = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        return employeesService.findEmployee(employee);
    }

    @GetMapping
    public List<Employees> getAll(){
        return employeesService.getAll();
    }
}
