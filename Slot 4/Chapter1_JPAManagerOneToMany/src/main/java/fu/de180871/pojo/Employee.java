package fu.de180871.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private BigDecimal salary;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "is_active")
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;


    public Employee() {
    }

    public Employee(String fullName, BigDecimal salary, LocalDate hireDate, String email, Gender gender, Boolean active) {
        this.fullName = fullName;
        this.salary = salary;
        this.hireDate = hireDate;
        this.email = email;
        this.gender = gender;
        this.active = active;
    }

    // Helper methods cho quan hệ N-N


    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", active=" + active +
                ", department=" + department +
                '}';
    }
}