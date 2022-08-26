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


    List<Employees> employees = new ArrayList<>(List.of(

            new Employees("Иванов", "Иван", "1", 105_000),
            new Employees("Иванов", "Евгений", "2", 100_000),
            new Employees("Сидоров", "Иван", "1", 84_000),
            new Employees("Зверев", "Михаил", "3", 300_000),
            new Employees("Клюева",  "Людмила", "5", 250_000),
            new Employees("Иванов", "Егор", "3", 150_000),
            new Employees("Кузнецов","Сергей", "3", 100_000),
            new Employees("Клоков", "Сергей", "4", 90_000),
            new Employees("Куркова", "Светлана", "2", 95_000),
            new Employees("Михалева", "Елена", "1", 100_000)
    ));

    public Employees addEmployee(Employees employee) {
        if (employees.contains(employee)){
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() < 11){
            employees.add(employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }
    public Employees removeEmployee(Employees employee) {
        if (!employees.contains(employee)){
            throw new EmployeeNotFoundException();
        }
        employees.remove(employee);
        return employee;
    }
    public Employees findEmployee(Employees employee) {
        if (!employees.contains(employee)){
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public List<Employees> getAll() {
        return new ArrayList<>(employees);
    }
}
