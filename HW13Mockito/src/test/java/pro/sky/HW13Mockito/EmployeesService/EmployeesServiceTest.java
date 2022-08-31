package pro.sky.HW13Mockito.EmployeesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.HW13Mockito.Employees.Employees;
import pro.sky.HW13Mockito.Exceptions.EmployeeAlreadyAddedException;
import pro.sky.HW13Mockito.Exceptions.EmployeeNotFoundException;
import pro.sky.HW13Mockito.Exceptions.EmployeeStorageIsFullException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class EmployeesServiceTest {
    private final EmployeesService employeesUnderTest = new EmployeesService();
/*
    @ParameterizedTest
    @MethodSource("testData")
    void shouldAddNewEmployee(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        Employees expected = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        // создаем сотрудника
        Employees result = EmployeesService.addEmployee(lastName, firstName, departmentId, salaryOfEmployee);
        Assertions.assertEquals(expected, result);
    }
*/
    @ParameterizedTest
    @MethodSource("testData")
    void shouldNotAddEmployee(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        Employees expected = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        assertThat(employeesUnderTest.addEmployee(lastName, firstName, departmentId, salaryOfEmployee)).isEqualTo(expected);

        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeesUnderTest.addEmployee(lastName, firstName, departmentId, salaryOfEmployee));
        //ожидаемое исключение
    }

    @ParameterizedTest
    @MethodSource("testData")
    void shouldNotAddEmployee1(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        List<Employees> employees = generateEmployees(5);
        employees.forEach(employee ->
                assertThat(employeesUnderTest.addEmployee(employee.getLastName(), employee.getFirstName(), employee.getDepartmentId(), employee.getSalaryOfEmployee())).isEqualTo(employee));

        EmployeeStorageIsFullException thrown = Assertions.assertThrows(EmployeeStorageIsFullException.class,
                () -> employeesUnderTest.addEmployee(lastName, firstName, departmentId, salaryOfEmployee));//ожидаемое исключение
    }

    @ParameterizedTest
    @MethodSource("testData")
    void shouldRemoveEmployee(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        Employees expected = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        assertThat(employeesUnderTest.addEmployee(lastName, firstName, departmentId, salaryOfEmployee)).isEqualTo(expected);

        assertThat(employeesUnderTest.removeEmployee(lastName, firstName, departmentId, salaryOfEmployee)).isEqualTo(expected);
        assertThat(employeesUnderTest.getAll()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("testData")
    void shouldNotRemoveEmployee(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        EmployeeNotFoundException thrown = Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeesUnderTest.removeEmployee("Климов", "Павел", 1, 30000));//ожидаемое исключение
        Employees expected = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        assertThat(employeesUnderTest.addEmployee(lastName, firstName, departmentId, salaryOfEmployee)).isEqualTo(expected);
        EmployeeNotFoundException thrown1 = Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeesUnderTest.removeEmployee("Климов", "Павел", 1, 30000));
    }

    @ParameterizedTest
    @MethodSource("testData")
    void shouldFindEmployee(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        Employees expected = new Employees(lastName, firstName, departmentId, salaryOfEmployee); // добавляем одного сотрудника
        assertThat(employeesUnderTest.addEmployee(lastName, firstName, departmentId, salaryOfEmployee)).isEqualTo(expected);

        assertThat(employeesUnderTest.findEmployee(lastName, firstName, departmentId, salaryOfEmployee)).isEqualTo(expected);
        assertThat(employeesUnderTest.getAll()).hasSize(1); // ожидается один в проверочном списке, т.к. мы добавили только 1-го
    }

    @ParameterizedTest
    @MethodSource("testData")
    void shouldNoFindEmployee(String lastName, String firstName, int departmentId, int salaryOfEmployee) {
        EmployeeNotFoundException thrown = Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeesUnderTest.findEmployee("Климов", "Павел", 1, 30000));//ожидаемое исключение

        Employees expected = new Employees(lastName, firstName, departmentId, salaryOfEmployee);
        assertThat(employeesUnderTest.addEmployee(lastName, firstName, departmentId, salaryOfEmployee)).isEqualTo(expected);
        EmployeeNotFoundException thrown1 = Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeesUnderTest.findEmployee("Климов", "Павел", 1, 30000));
    }

    /*@Test
    void getAllEmployees() {

        Employees employees1 = new Employees("Иванов", "Иван", 1, 105_000);
        Employees employees2 = new Employees("Иванов", "Евгений", 2, 100_000);
        Employees employees3 = new Employees("Сидоров", "Иван", 1, 84_000);
        List<Employees> result = new ArrayList<>();
        List<Employees> expected = employeesUnderTest.getAll();
        result.add(employees1);
        result.add(employees2);
        result.add(employees3);
        Assertions.assertEquals(expected, result);
    } */

    private List<Employees> generateEmployees(int size) {
        return Stream.iterate(1, i -> i + 1)
                .limit(size)
                .map(i -> new Employees("lastName" + (char) ((int) 'a' + i),
                        "firstname" + (char) ((int) 'a' + i), i, 50000 + i))
                .collect(Collectors.toList());
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("Иванов", "Иван", 1, 105_000),
                Arguments.of("Иванов", "Евгений", 2, 100_000),
                Arguments.of("Сидоров", "Иван", 1, 84_000));
    }
}
