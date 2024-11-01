package me.skypro.Homework_2._3.service;

import me.skypro.Homework_2._3.exception.EmployeeNotFoundException;
import me.skypro.Homework_2._3.repository.Employee;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmentService {
    private EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public double getSumSalaryEmployeesByDepartment(int departmentId) {
        return this.getEmployeeStreamByDepartment(departmentId)
                .mapToInt(employee -> (int) employee.getSalary())
                .sum();
    }

    public double getMaxSalaryEmployee(int departmentId) {
        return this.getEmployeeStreamByDepartment(departmentId)
                .mapToInt(employee -> (int) employee.getSalary())
                .max()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public double getMinSalaryEmployee(int departmentId) {
        return this.getEmployeeStreamByDepartment(departmentId)
                .mapToInt(employee -> (int) employee.getSalary())
                .min()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> getAllEmployeeByDepartment(int departmentId) {
        return this.getEmployeeStreamByDepartment(departmentId)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Employee>> getAllEmployees() {
        return employeeService.getAllEmployees().stream().collect(Collectors.groupingBy(Employee::getDepartmentId));
    }

    private Stream<Employee> getEmployeeStreamByDepartment(int departmentId) {
        return employeeService.getAllEmployees()
                .stream()
                .filter(Objects::nonNull)
                .filter(emp -> emp.getDepartmentId() == departmentId);
    }
}
