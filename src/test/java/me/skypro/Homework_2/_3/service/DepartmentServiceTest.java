package me.skypro.Homework_2._3.service;

import me.skypro.Homework_2._3.exception.EmployeeNotFoundException;
import me.skypro.Homework_2._3.repository.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    private static final Random RANDOM = new Random();
    private static final Collection<Employee> employeeList = List.of(
            new Employee("Sergey", "Ivanov", 1, 63_000),
            new Employee("Anna", "Petrova", 3, 82_000),
            new Employee("Valentin", "Stepanov", 2, 68_000),
            new Employee("Anastasia", "Petrova", 4, 72_000),
            new Employee("Petr", "Aleksandrov", 5, 91_000),
            new Employee("Alexander", "Sidorov", 3, 78_000),
            new Employee("Maria", "Khokhlova", 4, 89_000),
            new Employee("Igor", "Karpov", 2, 94_000),
            new Employee("Julia", "Morozova", 1, 75_000),
            new Employee("Maksim", "Tikhonov", 5, 88_000),
            new Employee("Dmitry", "Smirnov", 1, 80_000),
            new Employee("Elena", "Vasileva", 2, 85_000),
            new Employee("Roman", "Petrov", 3, 90_000),
            new Employee("Svetlana", "Fedorova", 4, 76_000),
            new Employee("Oleg", "Nikolaev", 5, 92_000),
            new Employee("Natalia", "Gromova", 4, 87_000),
            new Employee("Andrey", "Borisov", 3, 73_000),
            new Employee("Ekaterina", "Dmitrieva", 2, 79_000),
            new Employee("Viktor", "Zaitsev", 1, 83_000),
            new Employee("Olga", "Lebedeva", 5, 95_000)

    );

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @DisplayName("Положительный тест на получение сотрудника по департаменту")
    @Test
    void addEmployeeByDepartmentTest() {
        int requestDepartment = 1;
        Collection<Employee> expected = employeeList.stream()
                .filter(employee -> employee.getDepartmentId() == requestDepartment)
                .toList();

        when(employeeService.getAllEmployees())
                .thenReturn(employeeList);

        Collection<Employee> actual = departmentService.getAllEmployeeByDepartment(requestDepartment);

        verify(employeeService, times(1)).getAllEmployees();
        assertEquals(expected, actual);
    }

    @DisplayName("Негативный тест на получение сотрудника из несуществующего департамента")
    @Test
    void addEmployeeByDepartmentNegativeTest() {
        int requestDepartment = 6;
        Collection<Employee> expected = Collections.emptyList();

        when(employeeService.getAllEmployees())
                .thenReturn(employeeList);

        Collection<Employee> actual = departmentService.getAllEmployeeByDepartment(requestDepartment);

        verify(employeeService, times(1)).getAllEmployees();
        assertTrue(actual.isEmpty());
    }

    @DisplayName("Негативный тест на получение сотрудников с пустым отделом")
    @Test
    void addEmployeeByEmptyDepartmentNegativeTest() {
        int requestDepartment = 1;

        when(employeeService.getAllEmployees())
                .thenReturn(EMPTY_LIST);

        Collection<Employee> actual = departmentService.getAllEmployeeByDepartment(requestDepartment);

        verify(employeeService, times(1)).getAllEmployees();
        assertTrue(actual.isEmpty());
    }

    @DisplayName("Положительный тест на получение суммы зарплат по департаменту")
    @ParameterizedTest
    @MethodSource("provideDataForSum")
    void getSumSalaryByDepartmentTest(int departmentId, double expected) {

        when(employeeService.getAllEmployees())
                .thenReturn(employeeList);

        double actual = departmentService.getSumSalaryEmployeesByDepartment(departmentId);

        verify(employeeService, times(1)).getAllEmployees();
        assertEquals(expected, actual);
    }

    @DisplayName("Положительный тест на получение максимальной зарплаты по департаменту")
    @ParameterizedTest
    @MethodSource("provideDataForMax")
    void getMaxSalaryEmployeeTest(int departmentId, double expected) {

        when(employeeService.getAllEmployees())
                .thenReturn(employeeList);

        double actual = departmentService.getMaxSalaryEmployee(departmentId);

        verify(employeeService, times(1)).getAllEmployees();
        assertEquals(expected, actual);
    }

    @DisplayName("Негативный тест на получение максимальной зарплаты по департаменту")
    @Test
    void getMaxSalaryEmployeeNegativeTest() {
        int requestDepartmentId = 6;
        when(employeeService.getAllEmployees())
                .thenReturn(employeeList);

        assertThrows(EmployeeNotFoundException.class, () ->
                departmentService.getMaxSalaryEmployee(requestDepartmentId));
        verify(employeeService, times(1)).getAllEmployees();
    }

    @DisplayName("Положительный тест на получение минимальной зарплаты по департаменту")
    @ParameterizedTest
    @MethodSource("provideDataForMin")
    void getMinSalaryEmployeeTest(int departmentId, double expected) {

        when(employeeService.getAllEmployees())
                .thenReturn(employeeList);

        double actual = departmentService.getMinSalaryEmployee(departmentId);

        verify(employeeService, times(1)).getAllEmployees();
        assertEquals(expected, actual);
    }

    @DisplayName("Негативный тест на получение минимальной зарплаты по департаменту")
    @Test
    void getMinSalaryEmployeeNegativeTest() {
        int requestDepartmentId = 6;
        when(employeeService.getAllEmployees())
                .thenReturn(employeeList);

        assertThrows(EmployeeNotFoundException.class, () ->
                departmentService.getMinSalaryEmployee(requestDepartmentId));
        verify(employeeService, times(1)).getAllEmployees();
    }

    private static Stream<Arguments> provideDataForSum() {
        return Stream.of(
                Arguments.arguments(1, 301_000),
                Arguments.arguments(6, 0),
                Arguments.arguments(3, 323_000));
    }

    private static Stream<Arguments> provideDataForMax() {
        return Stream.of(
                Arguments.arguments(1, 83_000),
                Arguments.arguments(2, 94_000),
                Arguments.arguments(5, 95_000));
    }

    private static Stream<Arguments> provideDataForMin() {
        return Stream.of(
                Arguments.arguments(1, 63_000),
                Arguments.arguments(3, 73_000),
                Arguments.arguments(4, 72_000));
    }
}
