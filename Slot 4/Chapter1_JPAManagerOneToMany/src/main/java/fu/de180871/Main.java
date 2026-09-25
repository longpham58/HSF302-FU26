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

        // Tạo 2 Project
        Project p1 = new Project("PRJ_01", "Hệ thống Quản lý Đào tạo", new BigDecimal("500000000"), LocalDate.of(2026, 1, 1));
        Project p2 = new Project("PRJ_02", "Website Tin tức Tổng hợp", new BigDecimal("200000000"), LocalDate.of(2026, 4, 1));

        System.out.println("\n=== 2. PHÂN CÔNG DỰ ÁN CHÉO QUA HELPER METHOD ===");

        e1.assignToProject(p1);
        e1.assignToProject(p2);


        e2.assignToProject(p2);

        e3.assignToProject(p1);

        System.out.println("\n=== 3. LƯU VÀO CSDL (CASCADE TỰ ĐỘNG LƯU PROJECT) ===");
        employeeDAO.save(e1);
        employeeDAO.save(e2);
        employeeDAO.save(e3);

        System.out.println("\n=== 4. IN DANH SÁCH PROJECT CỦA TỪNG NHÂN VIÊN ===");
        List<Employee> employees = employeeDAO.findAll();
        for (Employee emp : employees) {
            System.out.println("\n" + emp);
            System.out.println("  -> Các dự án tham gia:");
            if (emp.getProjects().isEmpty()) {
                System.out.println("     (Chưa tham gia dự án nào)");
            } else {
                for (Project prj : emp.getProjects()) {
                    System.out.println("     + " + prj);
                }
            }
        }

        JPAUtil.close();
    }
}
