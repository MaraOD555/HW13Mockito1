package pro.sky.HW13Mockito.EmployeesService;

import org.springframework.stereotype.Service;
import pro.sky.HW13Mockito.Employees.Employees;
import pro.sky.HW13Mockito.Exceptions.EmployeeAlreadyAddedException;
import pro.sky.HW13Mockito.Exceptions.EmployeeNotFoundException;
import pro.sky.HW13Mockito.Exceptions.EmployeeStorageIsFullException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeesService {


    static List<Employees> employees = new ArrayList<>(List.of(

            new Employees("Иванов", "Иван", 1, 105_000),
            new Employees("Сидоров", "Иван", 1, 84_000),
            new Employees("Клюева",  "Людмила", 2, 250_000),
            new Employees("Иванов", "Евгений", 2, 100_000)
    ));

    public static Employees addEmployee(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        Employees employee = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        if (employees.contains(employee)){
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < 11){
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }
    public Employees removeEmployee(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        Employees employee = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        if (!employees.contains(employee)){
            throw new EmployeeNotFoundException();
        }
        employees.remove(employee);
        return employee;
    }
    public Employees findEmployee(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        Employees employee = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        if (!employees.contains(employee)){
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public List<Employees> getAll() {
        return new ArrayList<>(employees);
    }
}
