package pro.sky.HW13Mockito.EmployeesService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.HW13Mockito.Employees.Employees;
import pro.sky.HW13Mockito.Exceptions.EmployeeNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    List<Employees> employees = new ArrayList<>(List.of(

            new Employees("Иванов", "Иван", 1, 105_000),
            new Employees("Иванов", "Евгений", 2, 100_000),
            new Employees("Сидоров", "Иван", 1, 84_000)
    ));
    @Mock
    private EmployeesService employeesService; // это сам мок

    @InjectMocks
    private DepartmentService departmentService; // это куда мы мок инжектим

    @BeforeEach
    public void setUp() { // в данном случае мокаем getAll вначале,
        // т.е. когда будет вызван метод getAll, то будет вызван данный список (как инициализация базы данных).
        List<Employees> employees = new ArrayList<>(List.of(

                new Employees("Иванов", "Иван", 1, 105_000),
                new Employees("Сидоров", "Иван", 1, 84_000),
                new Employees("Клюева",  "Людмила", 2, 250_000),
                new Employees("Иванов", "Евгений", 2, 100_000)

        ));
        when(employeesService.getAll()).thenReturn(employees);
        }

        @ParameterizedTest
        @MethodSource("dataForMinSalary") // метод сравнивает мин зп по департаменту из всего листа заданных, а ожидаемые сотрудники с минимальной зп в файле ресурсе
        public void shouldShowLowestSalaryInDepartment (int departmentId, Employees expected){
            assertThat(departmentService.lowestSalaryInDepartment(departmentId)).isEqualTo(expected);
        }
        @Test
        public void shouldNotShowLowestSalaryInDepartment (){
            EmployeeNotFoundException thrown1 = Assertions.assertThrows(EmployeeNotFoundException.class,
                    () -> departmentService.lowestSalaryInDepartment(3));
        }
        @ParameterizedTest
        @MethodSource("dataForMaxSalary")
        public void shouldShowHighestSalaryInDepartment (int departmentId, Employees expected){
        assertThat(departmentService.highestSalaryInDepartment(departmentId)).isEqualTo(expected);
        }
        @Test
        public void shouldNotShowHighestSalaryInDepartment (){
        EmployeeNotFoundException thrown1 = Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> departmentService.highestSalaryInDepartment(8));
        }

        @ParameterizedTest
        @MethodSource("dataForEmployeesInDepartment") // сравниваем список с данными ожидаемыми в ресурсном файле,
          //если запрашиваем departmentId ыне из списка, то пустой список?
        public void shouldGetEmployeesInDepartment (int departmentId, List<Employees> expected){
        assertThat(departmentService.getEmployeesInDepartment(departmentId)).containsExactlyElementsOf(expected);
        }

        @Test
        public void shouldGetEmployeesByDepartment () {
            assertThat(departmentService.getEmployeesByDepartment()).containsAllEntriesOf(
                    Map.of(1, List.of(new Employees("Иванов", "Иван", 1, 105_000),new Employees ("Сидоров", "Иван", 1, 84_000)),
                           2, List.of(new Employees("Клюева",  "Людмила", 2, 250_000), new Employees("Иванов", "Евгений", 2, 100_000)))
            );

        }
        private static Stream<Arguments> dataForMinSalary () { // ожидаемые сотрудники с мин зап
            return Stream.of(
                    Arguments.of(1, new Employees("Сидоров", "Иван", 1, 84_000)),
                    Arguments.of(2, new Employees("Иванов", "Евгений", 2, 100_000))
            );
        }
        private static Stream<Arguments> dataForMaxSalary () { // ожидаемые сотрудники с макс зп
        return Stream.of(
                Arguments.of(2, new Employees("Клюева",  "Людмила", 2, 250_000)),
                Arguments.of(1, new Employees("Иванов", "Иван", 1, 105_000))
        );
        }
        private static Stream<Arguments> dataForEmployeesInDepartment () { // ожидаемые сотрудники с разбивкой по отделам
        return Stream.of(
                Arguments.of(2, List.of(new Employees("Клюева",  "Людмила", 2, 250_000), new Employees("Иванов", "Евгений", 2, 100_000))),
                Arguments.of(1, List.of(new Employees("Иванов", "Иван", 1, 105_000),new Employees ("Сидоров", "Иван", 1, 84_000))),
                Arguments.of(3, Collections.emptyList())
        );
        }
}
