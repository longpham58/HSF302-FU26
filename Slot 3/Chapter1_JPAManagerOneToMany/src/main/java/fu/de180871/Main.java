package fu.de180871;

import fu.de180871.pojo.Department;
import fu.de180871.pojo.Employee;
import fu.de180871.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Department dept = new Department("IT", "Ha Noi");
        Employee emp = new Employee("Test", new BigDecimal("1000"), LocalDate.now(), "test@company.com", Gender.OTHER, true);

        // Gọi helper method
        dept.addEmployee(emp);

        // Kiểm tra đồng bộ 2 chiều
        System.out.println(dept.getEmployees().contains(emp));
        System.out.println(emp.getDepartment() == dept);
    }
}