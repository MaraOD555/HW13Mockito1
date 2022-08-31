package pro.sky.HW13Mockito.EmployeesService;

import org.springframework.stereotype.Service;
import pro.sky.HW13Mockito.Employees.Employees;
import pro.sky.HW13Mockito.Exceptions.EmployeeNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class DepartmentService {
    private static EmployeesService employeesService;

    public DepartmentService(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    public Map<Integer, List<Employees>> getEmployeesByDepartment() {
        return employeesService.getAll().stream() // и создаем поток (stream)
                .collect(Collectors.groupingBy(Employees::getDepartmentId));
        }
    public List<Employees> getEmployeesInDepartment(int departmentId) {
        return employeesService.getAll().stream() // и создаем поток (stream)
            .filter(employee -> employee.getDepartmentId()==departmentId)// фильтр,
                 // который перебирает все "departmentId" и сравнивает у них отдел с искомым
            .collect(Collectors.toList());

      //  return "Сотрудники отдела " + departmentId + ":\n" + getEmployeesInDepartment;
    }
    public Employees lowestSalaryInDepartment(int departmentId) {
      return employeesService.getAll().stream()
                .filter(employee -> employee.getDepartmentId()== departmentId)
                .min((Comparator.comparingDouble(Employees::getSalaryOfEmployee)))// минимальное значение в отделе
                // через компаратор в лямбда выражении
                .orElseThrow(EmployeeNotFoundException::new);
    }
    public Employees highestSalaryInDepartment(int departmentId) {
        return employeesService.getAll().stream()//String highestSalaryInDepartment = employeesService.getAll().stream()
                //оригинал переменной, идея преобразовала в тот вид, который сейчас
                .filter(employee -> employee.getDepartmentId()== departmentId)
                .max((Comparator.comparingDouble(Employees :: getSalaryOfEmployee)))// минимальное значение в отделе
                // через компаратор в цикле
                .orElseThrow(EmployeeNotFoundException::new);
    }
}
