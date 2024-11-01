package me.skypro.Homework_2._3.service;

import me.skypro.Homework_2._3.exception.EmployeeAlreadyAddedException;
import me.skypro.Homework_2._3.exception.EmployeeNotFoundException;
import me.skypro.Homework_2._3.exception.EmployeeStorageIsFullException;
import me.skypro.Homework_2._3.repository.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService();
    private static final Random RANDOM = new Random();

    @DisplayName("Положительный тест на добавление сотрудника")
    @Test
    void addEmployeeTest() {
        int sizeBeforeAdding = employeeService.getAllEmployees().size();
        int expectedSize = ++sizeBeforeAdding;
        Employee newEmployee = new Employee("Alexey", "Stepanov", 3, 87_000);

        employeeService.addEmployee(newEmployee);

        int actualSize = employeeService.getAllEmployees().size();
        assertEquals(expectedSize, actualSize);

        boolean isAdded = employeeService.getAllEmployees()
                .stream()
                .anyMatch(newEmployee::equals);
        assertTrue(isAdded);
    }

    @DisplayName("Негативный тест на превышение количества сотрудников")
    @Test
    void addEmployeeIsFullTest() {
        int maxAllowedCount = 20;
        Stream.generate(EmployeeServiceTest::getRandomEmployee)
                .limit(maxAllowedCount - 1)
                .forEach(employeeService::addEmployee);

        assertDoesNotThrow(() ->
                employeeService.addEmployee(getRandomEmployee()));
        assertThrows(EmployeeStorageIsFullException.class, () ->
                employeeService.addEmployee(getRandomEmployee()));

        int actualSize = employeeService.getAllEmployees().size();
        assertEquals(maxAllowedCount, actualSize);

    }

    @DisplayName("Негативный тест на повторное добавление сотрудника")
    @Test
    void addEmployeeAlreadyAddedTest() {
        Employee employee = getRandomEmployee();
        assertDoesNotThrow(() ->
                employeeService.addEmployee(employee));
        assertThrows(EmployeeAlreadyAddedException.class, () ->
                employeeService.addEmployee(employee));

    }

    private static Employee getRandomEmployee() {
        return new Employee(RANDOM.nextInt() + "Alexey" + RANDOM.nextInt(),
                RANDOM.nextInt() + "Stepanov" + RANDOM.nextInt(),
                3, 74_000);
    }

    @DisplayName("Положительный тест на удаление сотрудника")
    @Test
    void removeEmployeeTest() {
        Employee employee = new Employee("Ivan", "Petrov", 5, 80_000);
        employeeService.addEmployee(employee);

        assertNotNull(employeeService.findEmployee("Ivan", "Petrov", 5, 80_000));

        Employee removedEmployee = employeeService.removeEmployee("Ivan", "Petrov", 5, 80_000);

        assertEquals(employee, removedEmployee);
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.findEmployee("Ivan", "Petrov", 5, 80_000));
    }

    @DisplayName("Негативный тест на удаление несуществующего сотрудника")
    @Test
    void removeNotExistenEmployeeTest() {

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.removeEmployee("Nonexistent", "Employee", 3, 65_000));
    }

    @DisplayName("Положительный тест на поиск сотрудника")
    @Test
    void findEmployeeTest() {
        Employee employee = new Employee("Maria", "Ivanova", 4, 68_000);
        employeeService.addEmployee(employee);

        Employee foudEmployee = employeeService.findEmployee("Maria", "Ivanova", 4, 68_000);

        assertEquals(employee, foudEmployee);
    }

    @DisplayName("Негативный тест на поиск несуществующего сотрудника")
    @Test
    void findNotExistenEmployeeTest() {
        assertThrows(EmployeeNotFoundException.class, () ->
                employeeService.findEmployee("Nonexisten", "Employee", 2, 74_000));
    }

}
