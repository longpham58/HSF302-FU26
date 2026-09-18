package fu.de180871;

import fu.de180871.dao.DepartmentDAO;
import fu.de180871.dao.EmployeeDAO;
import fu.de180871.pojo.Department;
import fu.de180871.pojo.Employee;
import fu.de180871.pojo.Gender;
import fu.de180871.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        System.out.println("=== 1. TẠO DEPARTMENT VÀ 3 EMPLOYEES (TODO 2.7) ===");
        Department it = new Department("Marketing", "Ha Noi");

        Employee e1 = new Employee("Nguyen Van A", new BigDecimal("15000000"), LocalDate.of(2022, 1, 10),
                "aa.nguyen@company.com", Gender.MALE, true);
        Employee e2 = new Employee("Tran Thi B", new BigDecimal("18000000"), LocalDate.of(2021, 6, 1),
                "bb.tran@company.com", Gender.FEMALE, true);
        Employee e3 = new Employee("Le Van C", new BigDecimal("12000000"), LocalDate.of(2023, 3, 15),
                "cc.le@company.com", Gender.OTHER, true);


        it.addEmployee(e1);
        it.addEmployee(e2);
        it.addEmployee(e3);

        // Chi persist(department) - cascade = ALL tu lo phan Employee
        departmentDAO.save(it);
        System.out.println("-> Da luu Department, id = " + it.getId());

        System.out.println("\n=== 2. TÌM LẠI KÈM EMPLOYEES BẰNG JOIN FETCH (TODO 2.6) ===");
        Department found = departmentDAO.findByIdWithEmployees(it.getId());
        if (found != null) {
            System.out.println("Phong ban: " + found.getName());
            for (Employee e : found.getEmployees()) {
                System.out.println(" - " + e.getFullName() + " | Email: " + e.getEmail());
            }
        }


        System.out.println("\n=== 3. TÁI HIỆN N+1 QUERY PROBLEM (TODO 2.8) ===");

        // 1. Goi findAll() lay danh sach Department (KHONG JOIN FETCH)
        // Hibernate se phat ra 1 cau SQL SELECT dau tien
        List<Department> departmentList = departmentDAO.findAll();

        System.out.println("\n--- BAT DAU DUYET DANH SACH EMPLOYEES CUA TUNG DEPARTMENT ---");
        // 2. Loop qua tung Department va truy cap .getEmployees()
        // Do @OneToMany la LAZY, moi vong lap Hibernate se ban thêm 1 cau SELECT (Tong: 1 + N SQL)
        for (Department d : departmentList) {
            System.out.println(">> Dang doc danh sach nhan vien thuoc phong: " + d.getName());
            int employeeCount = d.getEmployees().size(); // Dòng này kich hoat câu SELECT thu N
            System.out.println("   So luong nhan vien: " + employeeCount);
        }

        // =========================================================================
        // Test Unique Constraint (Trùng Email)
        // =========================================================================
        System.out.println("\n=== 4. TEST TRÙNG EMAIL (UNIQUE CONSTRAINT) ===");
        try {
            Employee duplicateEmailEmp = new Employee("Nguyen Van Trung", new BigDecimal("20000000"), LocalDate.now(),
                    "aa.nguyen@company.com", Gender.MALE, true);
            duplicateEmailEmp.setDepartment(it);

            System.out.println("Thử lưu nhân viên có email đã tồn tại: aa.nguyen@company.com...");
            employeeDAO.save(duplicateEmailEmp);
        } catch (Exception e) {
            System.out.println("-> Bắt được Exception thành công do vi phạm UNIQUE constraint!");
        }

        // Dong EntityManagerFactory
        JPAUtil.close();
        System.out.println("\n=== CHƯƠNG TRÌNH HOÀN THÀNH ===");
    }
}