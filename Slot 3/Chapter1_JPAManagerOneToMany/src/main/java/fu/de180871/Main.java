package fu.de180871;

import fu.de180871.dao.DepartmentDAO;
import fu.de180871.dao.EmployeeDAO;
import fu.de180871.pojo.Department;
import fu.de180871.pojo.Employee;
import fu.de180871.pojo.Gender;
import fu.de180871.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        System.out.println("=== 1. TAO PHONG BAN VA THEM NHAN VIEN (CASCADE SAVE) ===");
        Department dept = new Department("Software Engineering", "Da Nang");
        Employee emp1 = new Employee("Nguyen Van A", new BigDecimal("1500.00"), LocalDate.now(), "a.nguyen@company.com", Gender.MALE, true);
        Employee emp2 = new Employee("Tran Thi B", new BigDecimal("1800.00"), LocalDate.now(), "b.tran@company.com", Gender.FEMALE, true);

        dept.addEmployee(emp1);
        dept.addEmployee(emp2);

        departmentDAO.save(dept);
        System.out.println("-> Luu thanh cong Department ID: " + dept.getId());

        System.out.println("\n=== 2. TEST JOIN FETCH (TODO 2.6) ===");
        Department fetchedDept = departmentDAO.findByIdWithEmployees(dept.getId());

        if (fetchedDept != null) {
            System.out.println("Phong ban: " + fetchedDept.getName());
            // EntityManager da dong trong DAO, nhung va truy cap duoc nhan vien nho JOIN FETCH:
            System.out.println("So luong nhan vien loaded: " + fetchedDept.getEmployees().size());
            for (Employee e : fetchedDept.getEmployees()) {
                System.out.println(" - " + e.getFullName() + " (" + e.getEmail() + ")");
            }
        }

        JPAUtil.close();
        System.out.println("\n=== CHUONG TRINH HOAN THANH ===");
    }
}