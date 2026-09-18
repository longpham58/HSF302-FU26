package fu.de180871;

import fu.de180871.dao.EmployeeDAO;
import fu.de180871.pojo.Employee;
import fu.de180871.pojo.Gender;
import fu.de180871.pojo.Project;
import fu.de180871.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Tắt log thừa để console sạch sẽ
        java.util.logging.LogManager.getLogManager().reset();

        EmployeeDAO employeeDAO = new EmployeeDAO();

        System.out.println("=== 1. TẠO THỰC THỂ EMPLOYEE VÀ PROJECT ===");

        // Tạo 3 Employee với đầy đủ thuộc tính
        Employee e1 = new Employee("Nguyễn Văn A", new BigDecimal("15000000"), LocalDate.of(2023, 1, 15), "anv@gmail.com", Gender.MALE, true);
        Employee e2 = new Employee("Trần Thị B", new BigDecimal("18000000"), LocalDate.of(2022, 5, 20), "btt@gmail.com", Gender.FEMALE, true);
        Employee e3 = new Employee("Lê Văn C", new BigDecimal("12000000"), LocalDate.of(2024, 3, 10), "cle@gmail.com", Gender.MALE, true);
    }
}