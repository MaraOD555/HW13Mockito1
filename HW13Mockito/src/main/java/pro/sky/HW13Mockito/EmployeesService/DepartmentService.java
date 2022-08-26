package pro.sky.HW13Mockito.EmployeesService;

import org.springframework.stereotype.Service;
import pro.sky.HW13Mockito.Employees.Employees;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class DepartmentService {
    private static EmployeesService employeesService;

    public DepartmentService(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    public String getEmployeesByDepartment() {
        List<Employees> getEmployeesByDepartment = employeesService.getAll().stream()
                .sorted(Comparator.comparing(Employees::getDepartmentId))//сортировка в прямом направлении
                //перебирая каждого сотрудника и номер отдела
                .toList(); //новый лист

       return getEmployeesByDepartment.toString();
    }
    public String getEmployeesInDepartment(String departmentId) {
         List<Employees> getEmployeesInDepartment = employeesService.getAll().stream() // объявляем лист сотрудников
                 // и создаем поток (stream)
            .filter(employee -> Objects.equals(employee.getDepartmentId(), departmentId))// фильтр,
                 // который перебирает все "departmentId" и сравнивает у них отдел с искомым
            .toList();

        return "Сотрудники отдела " + departmentId + ":\n" + getEmployeesInDepartment;
    }
    public String lowestSalaryInDepartment(String departmentId) {
      return employeesService.getAll().stream()//String lowestSalaryInDepartment = employeesService.getAll().stream()
                //оригинал переменной, идея преобразовала в тот вид, который сейчас
                .filter(employee -> {
                return Objects.equals(employee.getDepartmentId(), departmentId); // сначала перебор по отделам,
                    // сравнивает его с искомым (задан в качестве аргумента)
                })
                .min(Comparator.comparingInt(employees -> employees.getSalaryOfEmployee()))// минимальное значение в отделе
                // через компаратор в лямбда выражении
                .get().toString();
    }
    public String highestSalaryInDepartment(String departmentId) {
        return employeesService.getAll().stream()//String highestSalaryInDepartment = employeesService.getAll().stream()
                //оригинал переменной, идея преобразовала в тот вид, который сейчас
                .filter(employee -> {
                    return Objects.equals(employee.getDepartmentId(), departmentId); // сначала перебор по отделам
                })
                .max(Comparator.comparingInt(Employees :: getSalaryOfEmployee))// минимальное значение в отделе
                // через компаратор в цикле
                .get().toString(); // возвращаем одного че6ловекапосле фильтра и сравнения з/п
    }
}
