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

        // 1) Tạo Department + 3 Employee (đúng thứ tự tham số constructor)
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

        // 2) Chỉ persist(department) — cascade = ALL tự lo phần Employee
        departmentDAO.save(it);
        System.out.println("Da luu Department, id = " + it.getId());

        // 3) Tìm lại kèm employees bằng JOIN FETCH
        Department found = departmentDAO.findByIdWithEmployees(it.getId());
        if (found != null) {
            System.out.println("Phong ban: " + found.getName());
            for (Employee e : found.getEmployees()) {
                System.out.println(" - " + e.getFullName() + " | Email: " + e.getEmail());
            }
        }

        // 4) Thử save() thêm 1 Employee trùng email đã tồn tại để kiểm tra Unique Constraint
        System.out.println("\n=== TEST TRÙNG EMAIL (UNIQUE CONSTRAINT) ===");
        try {
            Employee duplicateEmailEmp = new Employee("Nguyen Van Trung", new BigDecimal("20000000"), LocalDate.now(),
                    "aa.nguyen@company.com", Gender.MALE, true);
            duplicateEmailEmp.setDepartment(it);

            System.out.println("Thử lưu nhân viên có email đã tồn tại: aa.nguyen@company.com...");
            employeeDAO.save(duplicateEmailEmp);
        } catch (Exception e) {
            System.out.println("-> Bắt được Exception thành công do vi phạm UNIQUE constraint!");
        }

        JPAUtil.close();
    }
}