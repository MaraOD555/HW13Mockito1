package pro.sky.HW13Mockito.EmployeesController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.HW13Mockito.Employees.Employees;
import pro.sky.HW13Mockito.EmployeesService.DepartmentService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private static DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping("/all")
    public Map<Integer, List<Employees>> getEmployeesByDepartment(){
        return departmentService.getEmployeesByDepartment();
    }

    @GetMapping(value = "/all", params = "departmentId")
    public List<Employees> getEmployeesInDepartment(@RequestParam ("departmentId") int departmentId){
        return departmentService.getEmployeesInDepartment(departmentId);
    }
    @GetMapping("/min-salary")
    public Employees lowestSalaryInDepartment(@RequestParam ("departmentId") int departmentId){
        return departmentService.lowestSalaryInDepartment(departmentId);
    }
    @GetMapping("/max-salary")
    public Employees highestSalaryInDepartment(@RequestParam ("departmentId") int departmentId){
        return departmentService.highestSalaryInDepartment(departmentId);
    }
}
