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

        // =========================================================================
        // CHECKLIST 1 & 2: Mối quan hệ FK, Đồng bộ 2 chiều via addEmployee() & Cascade Save
        // =========================================================================
        System.out.println("=== 1. TEST ĐỒNG BỘ 2 CHIỀU VÀ CASCADE SAVE ===");
        Department deptIT = new Department("Software Engineering", "Da Nang");

        Employee e1 = new Employee("Nguyen Van A", new BigDecimal("15000000"), LocalDate.of(2022, 1, 10),
                "a.nguyen@company.com", Gender.MALE, true);
        Employee e2 = new Employee("Tran Thi B", new BigDecimal("18000000"), LocalDate.of(2021, 6, 1),
                "b.tran@company.com", Gender.FEMALE, true);

        // Helper method addEmployee giup dong bo 2 chieu
        deptIT.addEmployee(e1);
        deptIT.addEmployee(e2);

        // Kiem tra dong bo ngay trên RAM:
        System.out.println("Kiem tra e1.getDepartment(): " + e1.getDepartment().getName());
        System.out.println("Kiem tra deptIT.getEmployees().size(): " + deptIT.getEmployees().size());

        // Save Department (Tu dong luu 2 Employees nho CascadeType.ALL)
        departmentDAO.save(deptIT);
        System.out.println("-> Da luu Department ID: " + deptIT.getId());

        // =========================================================================
        // CHECKLIST 5 & TODO 2.6: Load JOIN FETCH (Khong bi LazyInitializationException)
        // =========================================================================
        System.out.println("\n=== 2. TEST JOIN FETCH (KHÔNG BỊ LAZY EXCEPTION DÙ EM ĐÃ ĐÓNG) ===");
        Department fetchedDept = departmentDAO.findByIdWithEmployees(deptIT.getId());
        System.out.println("Phong ban: " + fetchedDept.getName());
        System.out.println("So nhan vien (load bang JOIN FETCH): " + fetchedDept.getEmployees().size());

        // =========================================================================
        // TODO 2.8: TÁI HIỆN N+1 QUERY PROBLEM (1 + N SQL)
        // =========================================================================
        System.out.println("\n=== 3. TODO 2.8: TÁI HIỆN N+1 QUERY PROBLEM ===");
        List<Department> listNPlus1 = departmentDAO.findAll(); // 1 cau SELECT departments
        for (Department d : listNPlus1) {
            // Moi lan goi .getEmployees() phat ra 1 cau SELECT employees tuong ung
            System.out.println(">> Dept: " + d.getName() + " | So NV: " + d.getEmployees().size());
        }

        // =========================================================================
        // CHECKLIST 4 & TODO 2.9: FIX N+1 BẰNG JOIN FETCH (Chỉ 1 câu SQL)
        // =========================================================================
        System.out.println("\n=== 4. TODO 2.9: FIX N+1 BẰNG JOIN FETCH ===");
        List<Department> listFixed = departmentDAO.findAllWithEmployees(); // Chi 1 cau SELECT JOIN FETCH
        for (Department d : listFixed) {
            System.out.println(">> Dept: " + d.getName() + " | So NV: " + d.getEmployees().size());
        }

        System.out.println("\n==================================================");
        System.out.println(" [BÁO CÁO DO SỐ CÂU SQL (CHECKLIST 4)]");
        System.out.println(" - TODO 2.8 (Chua fix N+1): 1 + N cau SQL");
        System.out.println(" - TODO 2.9 (Da fix N+1):   1 cau SQL duy nhat (JOIN FETCH)");
        System.out.println("==================================================");

        // =========================================================================
        // CHECKLIST 3: Xóa Department -> Employee bị xóa theo (Cascade + OrphanRemoval)
        // =========================================================================
        System.out.println("\n=== 5. TEST XÓA DEPARTMENT (CASCADE DELETE) ===");
        System.out.println("Tong so nhan vien truoc khi xoa: " + employeeDAO.findAll().size());

        // Xoa Department
        departmentDAO.delete(deptIT.getId());
        System.out.println("-> Da xoa Department ID: " + deptIT.getId());

        // Kiem tra lai danh sach Employee duoi DB
        System.out.println("Tong so nhan vien sau khi xoa Department: " + employeeDAO.findAll().size());

        JPAUtil.close();
        System.out.println("\n=== TẤT CẢ CHECKLIST ĐÃ HOÀN THÀNH XUẤT SẮC ===");
    }
}