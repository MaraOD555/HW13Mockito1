package pro.sky.HW13Mockito.EmployeesController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.HW13Mockito.EmployeesService.DepartmentService;


@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private static DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping("/all")
    public String getEmployeesByDepartment(){
        return departmentService.getEmployeesByDepartment();
    }

    @GetMapping(value = "/all", params = "departmentId")
    public String getEmployeesInDepartment(@RequestParam ("departmentId") String departmentId){
        return departmentService.getEmployeesInDepartment(departmentId);
    }
    @GetMapping("/min-salary")
    public String lowestSalaryInDepartment(@RequestParam ("departmentId") String departmentId){
        return departmentService.lowestSalaryInDepartment(departmentId);
    }
    @GetMapping("/max-salary")
    public String highestSalaryInDepartment(@RequestParam ("departmentId") String departmentId){
        return departmentService.highestSalaryInDepartment(departmentId);
    }
}
