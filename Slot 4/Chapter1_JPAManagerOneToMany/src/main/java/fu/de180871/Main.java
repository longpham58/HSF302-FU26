package fu.de180871;

import fu.de180871.dao.DepartmentDAO;
import fu.de180871.dao.EmployeeDAO;
import fu.de180871.pojo.Department;
import fu.de180871.pojo.Employee;
import fu.de180871.pojo.Gender;
import fu.de180871.util.JPAUtil;
import org.hibernate.LazyInitializationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        // 1. TỰ ĐỘNG ĐIỀN DỮ LIỆU MẪU (DATA SEEDING)
        seedData(departmentDAO);

        // 2. TEST JOIN FETCH (TODO 2.6)
        System.out.println("\n=== TEST JOIN FETCH ===");
        Department fetchedDept = departmentDAO.findByIdWithEmployees(1L);
        if (fetchedDept != null) {
            System.out.println(fetchedDept);
            System.out.println("Danh sách nhân viên (Loaded bằng JOIN FETCH):");
            for (Employee e : fetchedDept.getEmployees()) {
                System.out.println(" - " + e);
            }
        }

        // 3. MINH HỌA LAZY INITIALIZATION EXCEPTION (TODO 2.8)
        System.out.println("\n===  TÁI HIỆN BẮT BẢO VỆ LAZY INITIALIZATION EXCEPTION ===");
        List<Department> listNPlus1 = departmentDAO.findAll();
        for (Department d : listNPlus1) {
            System.out.println(">> " + d);
            try {
                // Sẽ ném Exception do EntityManager đã đóng trong DAO
                System.out.println("   Số lượng nhân viên: " + d.getEmployees().size());
            } catch (LazyInitializationException e) {
                System.out.println("   [BẮT LỖI] Không thể lấy danh sách nhân viên do LazyInitializationException (Session đã bị đóng)!");
            }
        }

        // 4. FIX N+1/LAZY BẰNG JOIN FETCH (TODO 2.9)
        System.out.println("\n===  FIX N+1 BẰNG JOIN FETCH (LẤY ĐẦY ĐỦ DỮ LIỆU) ===");
        List<Department> listFixed = departmentDAO.findAllWithEmployees();
        for (Department d : listFixed) {
            System.out.println(">> " + d);
            System.out.println("   Số lượng nhân viên: " + d.getEmployees().size());
            for (Employee e : d.getEmployees()) {
                System.out.println("     + " + e);
            }
        }

        System.out.println("\n==================================================");
        System.out.println(" [BÁO CÁO KẾT QUẢ]");
        System.out.println(" -  Lazy Exception do FetchType.LAZY + EntityManager đóng");
        System.out.println(" -  Đã giải quyết bằng JOIN FETCH trong JPQL");
        System.out.println("==================================================");

        // 5. TEST XÓA DEPARTMENT (CASCADE DELETE)
        System.out.println("\n=== TEST XÓA DEPARTMENT (CASCADE DELETE) ===");
        System.out.println("Tổng số nhân viên trước khi xóa: " + employeeDAO.findAll().size());

        departmentDAO.delete(2L);
        System.out.println("-> Đã xóa Phòng ban có ID: 2 (Marketing)");

        System.out.println("Tổng số nhân viên còn lại sau khi xóa Phòng ban: " + employeeDAO.findAll().size());

        JPAUtil.close();
        System.out.println("\n=== CHƯƠNG TRÌNH HOÀN THÀNH ===");
    }

    private static void seedData(DepartmentDAO departmentDAO) {
        if (!departmentDAO.findAll().isEmpty()) {
            System.out.println("Dữ liệu đã có sẵn trong cơ sở dữ liệu, bỏ qua bước nhập dữ liệu mẫu.");
            return;
        }

        System.out.println("=== BẮT ĐẦU TỰ ĐỘNG ĐIỀN DỮ LIỆU MẪU (DATA SEEDING) ===");

        // Phòng 1: Software Engineering
        Department dev = new Department("Software Engineering", "Đà Nẵng");
        Employee e1 = new Employee("Nguyễn Văn A", new BigDecimal("15000000"), LocalDate.of(2022, 1, 10),
                "a.nguyen@company.com", Gender.MALE, true);
        Employee e2 = new Employee("Trần Thị B", new BigDecimal("18000000"), LocalDate.of(2021, 6, 1),
                "b.tran@company.com", Gender.FEMALE, true);
        dev.addEmployee(e1);
        dev.addEmployee(e2);

        // Phòng 2: Marketing
        Department mkt = new Department("Marketing", "Hà Nội");
        Employee e3 = new Employee("Lê Văn C", new BigDecimal("12000000"), LocalDate.of(2023, 3, 15),
                "c.le@company.com", Gender.OTHER, true);
        mkt.addEmployee(e3);

        // Phòng 3: Human Resources
        Department hr = new Department("Human Resources", "Hồ Chí Minh");
        Employee e4 = new Employee("Phạm Minh D", new BigDecimal("20000000"), LocalDate.of(2020, 9, 20),
                "d.pham@company.com", Gender.MALE, true);
        hr.addEmployee(e4);

        departmentDAO.save(dev);
        departmentDAO.save(mkt);
        departmentDAO.save(hr);

        System.out.println("-> Đã thêm 3 Phòng ban và 4 Nhân viên thành công!\n");
    }
}