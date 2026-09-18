package fu.de180871.pojo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    private BigDecimal budget;

    @Column(name = "start_date")
    private LocalDate startDate;



    public Project() {
    }

    public Project(String name, BigDecimal budget, LocalDate startDate) {
        this.name = name;
        this.budget = budget;
        this.startDate = startDate;
    }
    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();



    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getBudget() { return budget; }
    public void setBudget(BigDecimal budget) { this.budget = budget; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Dự án [Mã: " + id + " | Tên dự án: " + name + " | Ngân sách: " + budget + " VNĐ | Ngày bắt đầu: " + startDate + "]";
    }
}