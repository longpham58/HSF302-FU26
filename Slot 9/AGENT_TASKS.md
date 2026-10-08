# AGENT_TASKS — HSF302 Chapter 6 CRUD Student

> **Nguồn yêu cầu chính:** `Chapter6.md`
>
> **Repository:** `https://github.com/longpham58/HSF302-FU26.git`
>
> **Mục tiêu:** Hoàn thành bài Chapter 6 — nâng cấp CRUD Student từ lưu `ArrayList` sang SQL Server bằng Spring Data JPA, Spring MVC + Thymeleaf, Bean Validation và `mssql-jdbc`.
>
> **QUAN TRỌNG:** Agent phải đọc file `Chapter6.md` trước khi code. File này là specification chính. Không tự ý thay đổi kiến trúc, package, tên class, route, database schema, logic CRUD hoặc giao diện nếu `Chapter6.md` đã quy định.

---

## 0. LUẬT BẮT BUỘC CHO AGENT

### 0.1. Luật tuần tự TODO

Agent **BẮT BUỘC** thực hiện TODO theo đúng thứ tự:

```text
TODO 0
  ↓
TODO 1
  ↓
TODO 2
  ↓
...
  ↓
TODO 15
  ↓
FINAL VERIFICATION
```

**Không được làm TODO tiếp theo nếu TODO hiện tại chưa hoàn thành 100%.**

Một TODO chỉ được coi là hoàn thành khi:

1. Code/config của TODO đã được thực hiện.
2. Không còn lỗi compile/build liên quan.
3. Đã chạy các kiểm tra được yêu cầu trong TODO.
4. Đã đối chiếu với `Chapter6.md`.
5. Đã kiểm tra `git diff`.
6. Đã commit.
7. Đã push thành công lên GitHub.
8. `git status` xác nhận working tree sạch hoặc chỉ còn những thay đổi được xác định rõ là không thuộc TODO hiện tại.

**Sau khi commit + push thành công mới được bắt đầu TODO tiếp theo.**

---

## 0.2. Repository bắt buộc

Remote chính:

```text
https://github.com/longpham58/HSF302-FU26.git
```

Trước khi bắt đầu:

```bash
git remote -v
git status
git branch --show-current
```

Nếu remote chưa tồn tại, cấu hình:

```bash
git remote add origin https://github.com/longpham58/HSF302-FU26.git
```

Không được tự ý đổi repository sang repository khác.

---

## 0.3. Không được phá cấu trúc dự án hiện có

Trước khi code, agent phải inspect project hiện tại.

Đặc biệt phải xác định:

- project đang ở Chapter nào;
- package hiện tại;
- Maven/Gradle;
- Java version;
- Spring Boot version;
- cấu trúc `src/main/java`;
- cấu trúc `src/main/resources`;
- các class/model/service/controller hiện có;
- các template hiện có;
- có phải project Chapter 5 dùng `ArrayList` hay không.

Nếu project đã có code Chapter 5, **ưu tiên nâng cấp code hiện tại**, không tạo một project độc lập nằm cạnh project cũ.

Không tự ý:

- đổi tên project;
- đổi base package khỏi `com.hsf302.chapter6`;
- đổi route;
- đổi tên entity;
- thêm framework không có trong specification;
- đổi sang REST API;
- đổi Thymeleaf sang frontend framework;
- đổi SQL Server sang database khác;
- đổi JPA sang JDBC thuần;
- thêm Lombok nếu không cần;
- refactor lớn ngoài phạm vi TODO.

---

## 0.4. Specification architecture bắt buộc

Kiến trúc cuối cùng phải tương ứng với:

```text
Browser
   │
   ▼
StudentController
   │
   ▼
StudentService (interface)
   │
   ▼
StudentServiceImpl
   │
   ▼
StudentRepository
   │
   ▼
Hibernate / Spring Data JPA
   │
   ▼
mssql-jdbc
   │
   ▼
SQL Server
   │
   ▼
HSF302_CH6.dbo.students
```

Package bắt buộc:

```text
com.hsf302.chapter6
├── StudentManagementApplication.java
├── config/
│   └── DataInitializer.java
├── entity/
│   └── Student.java
├── repository/
│   └── StudentRepository.java
├── service/
│   ├── StudentService.java
│   └── impl/
│       └── StudentServiceImpl.java
└── controller/
    ├── HomeController.java
    └── StudentController.java
```

Resources:

```text
src/main/resources/
├── templates/
│   ├── fragments/
│   │   └── layout.html
│   └── students/
│       ├── list.html
│       ├── form.html
│       └── detail.html
├── static/
│   └── css/
│       └── style.css
└── application.properties
```

---

## 0.5. Quy tắc commit/push

Mỗi TODO phải có **một commit riêng**.

Không gom nhiều TODO vào một commit.

Quy trình bắt buộc cuối mỗi TODO:

```bash
git status
git diff
git add .
git commit -m "TODO X: <mô tả ngắn>"
git push origin <CURRENT_BRANCH>
git status
```

Trong đó `<CURRENT_BRANCH>` là branch đang làm việc được lấy bằng:

```bash
git branch --show-current
```

Nếu branch hiện tại là `main`, lệnh tương ứng:

```bash
git push origin main
```

Nếu branch khác, push đúng branch hiện tại:

```bash
git push origin "$(git branch --show-current)"
```

### Quy tắc commit message

Format bắt buộc (theo yêu cầu và ảnh người dùng cung cấp):

```text
feat(project): TODO <number> <mô tả>
```

Ví dụ:

```bash
git add .
git commit -m "feat(project): TODO 2 Main Application — StudentManagementApplication.java"
git push origin main
```

Lịch sử TODO theo format này:
- `feat(project): TODO1 Xây dựng project step-by-step` (Đã hoàn thành ở commit 03c8a85)
- `feat(project): TODO 2 Main Application — StudentManagementApplication.java` (Đã hoàn thành ở commit d439a62)
- `feat(project): TODO 3 Entity — Student.java`
- `feat(project): TODO 4 Repository — StudentRepository.java`
- `feat(project): TODO 5 Service — StudentService + StudentServiceImpl`
- `feat(project): TODO 6 Dữ liệu mẫu — DataInitializer.java`
- `feat(project): TODO 7 Controller — StudentController.java`
- `feat(project): TODO 8 Home redirect — HomeController.java`
- `feat(project): TODO 9 CSS + Layout — fragments/layout.html`
- `feat(project): TODO 10 Template — students/list.html`
- `feat(project): TODO 11 Template — students/form.html`
- `feat(project): TODO 12 Template — students/detail.html`
- `feat(project): TODO 13 Kiểm thử tổng thể`

Vẫn giữ nguyên nguyên tắc: **một TODO = một commit = một lần push** và thực hiện hoàn toàn tự động.

---

## 0.6. Nếu TODO bị lỗi

Nếu build/test/ứng dụng lỗi:

1. **Không được commit code lỗi.**
2. Không được chuyển sang TODO tiếp theo.
3. Phải tìm nguyên nhân.
4. Sửa lỗi thuộc phạm vi TODO hiện tại.
5. Chạy lại toàn bộ checklist của TODO.
6. Chỉ commit/push khi TODO đạt.

Nếu lỗi nằm ở môi trường bên ngoài, ví dụ:

- SQL Server chưa chạy;
- không có database;
- port 1433 chưa mở;
- thiếu quyền;
- không thể truy cập remote GitHub;

agent phải ghi rõ blocker và **không giả vờ rằng TODO đã hoàn thành**.

---

## 0.7. Không được làm việc vượt TODO

Ví dụ đang ở TODO 5:

- được sửa `Student.java`;
- được sửa những file cần thiết để TODO 5 build được;
- **không được triển khai Repository, Service, Controller hoặc Template của TODO 6+**.

Nếu phát hiện code của TODO sau cần thiết để compile TODO hiện tại, chỉ tạo phần tối thiểu cần thiết và ghi rõ lý do. Không được triển khai trước toàn bộ TODO sau.

---

# TODO 0 — INSPECT PROJECT + BASELINE

## Mục tiêu

Hiểu project hiện tại trước khi sửa.

## Việc phải làm

1. Clone/open repository nếu project chưa có local.
2. Kiểm tra branch hiện tại.
3. Kiểm tra remote.
4. Đọc `Chapter6.md`.
5. Inspect toàn bộ cấu trúc project.
6. Xác định code hiện tại tương ứng với Chapter 5 hay trạng thái nào.
7. Xác định các file sẽ được tạo/sửa/xóa.
8. Kiểm tra build hiện tại:

```bash
mvn clean test
```

hoặc command tương ứng nếu project không dùng Maven.

## Không được

- Không code tính năng Chapter 6 ở TODO này.
- Không sửa logic chỉ để “tiện” cho TODO sau.

## Checklist

- [ ] Đã đọc `Chapter6.md`.
- [ ] Đã biết base package hiện tại.
- [ ] Đã biết branch hiện tại.
- [ ] Đã kiểm tra remote.
- [ ] Đã kiểm tra build baseline.
- [ ] Đã xác định cấu trúc project.

## Commit

```bash
git add .
git commit -m "TODO 0: Inspect project baseline"
git push origin <CURRENT_BRANCH>
```

Nếu TODO 0 không làm thay đổi file, **không tạo commit giả**. Chỉ chuyển tiếp nếu baseline inspection hoàn tất.

---

# TODO 1 — POM / DEPENDENCIES

## Tham chiếu

`Chapter6.md` — Bước 1.

## Mục tiêu

Project phải dùng:

- Spring Boot 3.3.x;
- Java 17;
- Spring Web;
- Thymeleaf;
- Validation;
- Spring Data JPA;
- Microsoft SQL Server Driver;
- DevTools;
- Spring Boot Test.

Dependency quan trọng:

```xml
spring-boot-starter-data-jpa
mssql-jdbc
```

## Yêu cầu

Nếu project đã có dependency tương ứng thì không thêm duplicate.

Không thêm dependency không cần thiết.

Không bắt buộc Lombok.

## Kiểm tra

```bash
mvn clean test
```

Kiểm tra Maven không có dependency duplicate hoặc lỗi resolve.

## Checklist

- [ ] Java target = 17.
- [ ] Spring Boot = 3.3.x theo specification.
- [ ] Có Spring Web.
- [ ] Có Thymeleaf.
- [ ] Có Validation.
- [ ] Có Spring Data JPA.
- [ ] Có `mssql-jdbc`.
- [ ] Có DevTools.
- [ ] Có test starter.
- [ ] Maven build thành công.

## Commit + Push

```bash
git add .
git commit -m "TODO 1: Configure Maven dependencies"
git push origin <CURRENT_BRANCH>
```

**Chỉ sau khi push thành công mới sang TODO 2.**

---

# TODO 2 — PROJECT STRUCTURE

## Tham chiếu

`Chapter6.md` — Bước 2.

## Mục tiêu

Đưa project về đúng cấu trúc:

```text
src/main/java/com/hsf302/chapter6/
├── StudentManagementApplication.java
├── config/
├── entity/
├── repository/
├── service/
│   └── impl/
└── controller/
```

Resources:

```text
src/main/resources/
├── templates/
│   ├── fragments/
│   └── students/
├── static/css/
└── application.properties
```

## Yêu cầu

Tất cả package phải là package con của:

```text
com.hsf302.chapter6
```

Không để package nằm ngoài component scan.

## Checklist

- [ ] Đúng base package.
- [ ] Đúng package `config`.
- [ ] Đúng package `entity`.
- [ ] Đúng package `repository`.
- [ ] Đúng package `service`.
- [ ] Có `service/impl`.
- [ ] Đúng package `controller`.
- [ ] `templates` nằm trong `src/main/resources`.
- [ ] `static` nằm trong `src/main/resources`.

## Commit + Push

```bash
git add .
git commit -m "TODO 2: Align project structure"
git push origin <CURRENT_BRANCH>
```

---

# TODO 3 — SQL SERVER CONFIG + application.properties

## Tham chiếu

`Chapter6.md` — Phần 1 và Bước 3.

## Mục tiêu

Cấu hình datasource cho:

```text
SQL Server
Database: HSF302_CH6
Port: 1433
User: sa
```

Properties chính:

```properties
spring.application.name=student-management
server.port=8080

spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=HSF302_CH6;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=12345

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.use_nationalized_character_data=true
spring.jpa.open-in-view=false

spring.thymeleaf.cache=false
logging.level.com.hsf302=DEBUG
```

## Bảo mật

Nếu project dùng file local secret:

```text
application-local.properties
```

phải được `.gitignore`.

Không commit secret thật nếu project đã có cơ chế secret/config local.

## SQL Server

Database phải tồn tại:

```sql
IF DB_ID(N'HSF302_CH6') IS NULL
    CREATE DATABASE HSF302_CH6;
```

Nếu agent không thể thao tác SQL Server trên môi trường hiện tại, phải ghi blocker rõ ràng.

## Checklist

- [ ] Database name = `HSF302_CH6`.
- [ ] URL đúng cú pháp SQL Server.
- [ ] Có `trustServerCertificate=true`.
- [ ] Có `use_nationalized_character_data=true`.
- [ ] `ddl-auto=update`.
- [ ] `open-in-view=false`.
- [ ] Không commit secret ngoài ý muốn.
- [ ] File UTF-8.
- [ ] App có thể khởi tạo datasource nếu SQL Server khả dụng.

## Commit + Push

```bash
git add .
git commit -m "TODO 3: Configure SQL Server datasource"
git push origin <CURRENT_BRANCH>
```

---

# TODO 4 — MAIN APPLICATION + DATABASE CONNECTION CHECK

## Tham chiếu

`Chapter6.md` — Bước 4.

## Mục tiêu

Đảm bảo:

```java
package com.hsf302.chapter6;

@SpringBootApplication
public class StudentManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }
}
```

## Kiểm tra

Chạy:

```bash
mvn spring-boot:run
```

Nếu SQL Server hoạt động, cần thấy tương đương:

```text
HikariPool-1 - Start completed.
Tomcat started on port 8080.
```

Chưa có controller thì `/` có thể 404 — điều này bình thường.

## Checklist

- [ ] Main class đúng package.
- [ ] `@SpringBootApplication`.
- [ ] App start được.
- [ ] Hikari connection thành công nếu DB khả dụng.
- [ ] Tomcat chạy port 8080.
- [ ] Không có lỗi datasource.

## Commit + Push

```bash
git add .
git commit -m "TODO 4: Verify application and database startup"
git push origin <CURRENT_BRANCH>
```

---

# TODO 5 — STUDENT ENTITY

## Tham chiếu

`Chapter6.md` — Bước 5.

## Mục tiêu

Tạo:

```text
com.hsf302.chapter6.entity.Student
```

Bắt buộc:

```java
@Entity
@Table(name = "students")
```

ID:

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

Fields:

```text
name   -> String
email  -> String
age    -> Integer
major  -> String
gpa    -> Double
```

Validation phải theo specification:

```text
name:
@NotBlank
@Size(min = 2, max = 50)

email:
@NotBlank
@Email
@Size(max = 100)
@Column(unique = true)

age:
@NotNull
@Min(18)
@Max(30)

major:
@NotBlank

gpa:
@NotNull
@DecimalMin("0.0")
@DecimalMax("4.0")
```

Constructor không tham số bắt buộc cho JPA.

Có constructor seed:

```java
Student(String name, String email, Integer age, String major, Double gpa)
```

Getter/setter thủ công.

## Quan trọng

Không dùng `int` cho age.

Không dùng `double` cho gpa.

Không dùng `javax.persistence`.

Phải dùng:

```java
jakarta.persistence.*
jakarta.validation.constraints.*
```

## Checklist

- [ ] `@Entity`.
- [ ] Table `students`.
- [ ] ID `Long`.
- [ ] IDENTITY.
- [ ] Validation đúng.
- [ ] `Integer age`.
- [ ] `Double gpa`.
- [ ] Constructor rỗng.
- [ ] Constructor seed.
- [ ] Getter/setter.
- [ ] `toString`.
- [ ] Build thành công.
- [ ] Hibernate có thể tạo/validate bảng khi app chạy.

## Commit + Push

```bash
git add .
git commit -m "TODO 5: Implement Student entity"
git push origin <CURRENT_BRANCH>
```

---

# TODO 6 — STUDENT REPOSITORY

## Tham chiếu

`Chapter6.md` — Bước 6.

## Mục tiêu

Tạo:

```text
com.hsf302.chapter6.repository.StudentRepository
```

Kế thừa:

```java
JpaRepository<Student, Long>
```

Bắt buộc có:

```java
boolean existsByEmailIgnoreCase(String email);

boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
```

## Checklist

- [ ] `JpaRepository<Student, Long>`.
- [ ] Derived query dùng đúng field Java.
- [ ] Không dùng tên DB column thay cho field Java.
- [ ] Không có `No property ... found for type Student`.
- [ ] ApplicationContext tạo repository thành công.

## Commit + Push

```bash
git add .
git commit -m "TODO 6: Implement Student repository"
git push origin <CURRENT_BRANCH>
```

---

# TODO 7 — SERVICE LAYER

## Tham chiếu

`Chapter6.md` — Bước 7.

## Mục tiêu

Tạo interface:

```text
com.hsf302.chapter6.service.StudentService
```

và implementation:

```text
com.hsf302.chapter6.service.impl.StudentServiceImpl
```

Interface phải có:

```java
List<Student> findAll();

Optional<Student> findById(Long id);

Student create(Student student);

boolean update(Long id, Student data);

boolean delete(Long id);

boolean isEmailTaken(String email, Long excludeId);

List<String> getMajors();
```

Implementation:

```java
@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService
```

### `findAll`

Sort tăng dần theo `id`.

### `create`

Bắt buộc:

```java
student.setId(null);
```

Sau đó:

```java
studentRepository.save(student);
```

### `update`

Phải:

1. load entity bằng ID;
2. set từng field;
3. không cần `save()` nếu entity managed;
4. để Hibernate dirty checking tạo UPDATE.

Không thay thế toàn bộ entity bằng cách nguy hiểm.

### `delete`

Kiểm tra `existsById` trước.

### `isEmailTaken`

Create:

```java
existsByEmailIgnoreCase
```

Update:

```java
existsByEmailIgnoreCaseAndIdNot
```

### `getMajors`

Phải trả:

```text
CNTT
KTPM
HTTT
ATTT
MMT
```

## Checklist

- [ ] Interface tồn tại.
- [ ] Implementation tồn tại.
- [ ] `@Service` nằm trên implementation.
- [ ] `@Transactional(readOnly = true)` ở class.
- [ ] Create/update/delete có `@Transactional`.
- [ ] Create reset id = null.
- [ ] Update dùng load + set fields + dirty checking.
- [ ] Delete check tồn tại.
- [ ] Email duplicate logic đúng.
- [ ] Majors đúng 5 giá trị.
- [ ] App không báo `No qualifying bean of type StudentService`.

## Commit + Push

```bash
git add .
git commit -m "TODO 7: Implement student service layer"
git push origin <CURRENT_BRANCH>
```

---

# TODO 8 — DATA INITIALIZER

## Tham chiếu

`Chapter6.md` — Bước 8.

## Mục tiêu

Tạo:

```text
config/DataInitializer.java
```

implements:

```java
CommandLineRunner
```

Seed chỉ khi:

```java
studentRepository.count() == 0
```

Dữ liệu mẫu:

```text
Nguyễn Văn An  / an@fpt.edu.vn    / 20 / CNTT / 3.5
Trần Thị Bình  / binh@fpt.edu.vn  / 21 / KTPM / 3.2
Lê Minh Cường  / cuong@fpt.edu.vn / 19 / ATTT / 3.8
Phạm Thị Dung  / dung@fpt.edu.vn  / 22 / HTTT / 2.9
```

## Checklist

- [ ] Seed chạy sau ApplicationContext.
- [ ] Chỉ seed khi bảng rỗng.
- [ ] Lần đầu có 4 dòng.
- [ ] Lần hai không duplicate.
- [ ] Tiếng Việt lưu đúng.
- [ ] ID được SQL Server tự sinh.

## Commit + Push

```bash
git add .
git commit -m "TODO 8: Add initial student seed data"
git push origin <CURRENT_BRANCH>
```

---

# TODO 9 — STUDENT CONTROLLER

## Tham chiếu

`Chapter6.md` — Bước 9.

## Mục tiêu

Tạo:

```text
com.hsf302.chapter6.controller.StudentController
```

với:

```java
@Controller
@RequestMapping("/students")
```

Không dùng `@RestController`.

## Routes bắt buộc

```text
GET  /students
GET  /students/{id}

GET  /students/create
POST /students/create

GET  /students/{id}/edit
POST /students/{id}/edit

POST /students/{id}/delete
```

## Shared model

Có:

```java
@ModelAttribute("majors")
public List<String> majors()
```

## Validation

Create/update phải có:

```java
@Valid @ModelAttribute("student") Student student,
BindingResult bindingResult
```

`BindingResult` phải đứng ngay sau parameter `@Valid`.

## Create

Phải:

1. validate;
2. check duplicate email;
3. nếu lỗi → trả form;
4. save;
5. bắt `DataIntegrityViolationException`;
6. success → flash + redirect `/students`.

## Update

Phải:

```java
student.setId(id);
```

Check duplicate email nhưng exclude current ID.

Nếu validation lỗi:

```text
students/form
```

Nếu thành công:

```text
redirect:/students
```

## Delete

Phải là POST:

```text
POST /students/{id}/delete
```

Không dùng GET để delete.

## Checklist

- [ ] Controller đúng annotation.
- [ ] Route đúng.
- [ ] CRUD đủ.
- [ ] Validation đúng.
- [ ] Duplicate email đúng.
- [ ] `BindingResult` đúng vị trí.
- [ ] Flash message đúng.
- [ ] PRG pattern đúng.
- [ ] Bắt `DataIntegrityViolationException`.
- [ ] Delete dùng POST.

## Commit + Push

```bash
git add .
git commit -m "TODO 9: Implement student controller"
git push origin <CURRENT_BRANCH>
```

---

# TODO 10 — HOME CONTROLLER

## Tham chiếu

`Chapter6.md` — Bước 10.

## Mục tiêu

Tạo:

```text
HomeController.java
```

Route:

```text
GET /
```

Response:

```text
redirect:/students
```

## Checklist

- [ ] `/` redirect `/students`.
- [ ] Không duplicate mapping.

## Commit + Push

```bash
git add .
git commit -m "TODO 10: Add home redirect controller"
git push origin <CURRENT_BRANCH>
```

---

# TODO 11 — CSS + LAYOUT

## Tham chiếu

`Chapter6.md` — Bước 11.

## Mục tiêu

Tạo:

```text
src/main/resources/static/css/style.css
src/main/resources/templates/fragments/layout.html
```

CSS phải có logic GPA:

```text
.gpa-high
.gpa-mid
.gpa-low
```

Layout bắt buộc:

```html
th:fragment="layout(title, content)"
```

Có:

- Bootstrap 5;
- Bootstrap Icons;
- navbar;
- flash success;
- flash error;
- main container;
- footer;
- CSS riêng.

Layout phải nhận:

```text
title
content
```

## Quan trọng

Không sử dụng Layout Dialect nếu không có trong specification.

Không quay lại lỗi cũ:

```text
th:replace="~{fragments/layout :: layout}"
```

nếu fragment không có parameter.

Template con phải dùng:

```html
th:replace="~{fragments/layout :: layout(~{::title}, ~{::section})}"
```

## Checklist

- [ ] CSS đúng path.
- [ ] Layout đúng fragment signature.
- [ ] Navbar hiển thị.
- [ ] Flash message dùng chung.
- [ ] Footer hiển thị.
- [ ] Bootstrap load.
- [ ] CSS load.
- [ ] Không có TemplateInputException do fragment.

## Commit + Push

```bash
git add .
git commit -m "TODO 11: Implement Thymeleaf layout and CSS"
git push origin <CURRENT_BRANCH>
```

---

# TODO 12 — STUDENTS LIST TEMPLATE

## Tham chiếu

`Chapter6.md` — Bước 12.

## Mục tiêu

Tạo:

```text
templates/students/list.html
```

Hiển thị:

- ID;
- Họ tên;
- Email;
- Tuổi;
- Chuyên ngành;
- GPA;
- thao tác detail/edit/delete.

Phải có:

```text
GET /students/{id}
GET /students/{id}/edit
POST /students/{id}/delete
```

## GPA

Logic:

```text
>= 3.5 → gpa-high
>= 2.5 → gpa-mid
< 2.5  → gpa-low
```

## Delete confirmation

Không dùng inline Thymeleaf JavaScript kiểu:

```text
/*[[${student.name}]]*/
```

Phải dùng:

```html
th:data-name="${student.name}"
```

và:

```javascript
this.dataset.name
```

## Checklist

- [ ] Layout hoạt động.
- [ ] 4 seed students hiển thị.
- [ ] ID đúng DB.
- [ ] Tiếng Việt đúng.
- [ ] GPA đúng màu.
- [ ] Delete là POST.
- [ ] Confirmation hiển thị đúng tên.
- [ ] Cancel không xóa.
- [ ] View Source không còn `th:*`.

## Commit + Push

```bash
git add .
git commit -m "TODO 12: Implement student list view"
git push origin <CURRENT_BRANCH>
```

---

# TODO 13 — STUDENT FORM TEMPLATE

## Tham chiếu

`Chapter6.md` — Bước 13.

## Mục tiêu

Tạo:

```text
templates/students/form.html
```

Một form dùng cho:

```text
Create
Edit
```

Phân biệt bằng:

```text
isEdit
pageTitle
```

## Fields

```text
name
email
age
major
gpa
```

Validation errors phải hiển thị dưới field.

Phải dùng:

```html
th:object="${student}"
th:field="*{name}"
th:errorclass="is-invalid"
th:errors="*{name}"
```

Tương tự cho các field còn lại.

## Create action

```text
/students/create
```

## Edit action

```text
/students/{id}/edit
```

## Checklist bắt buộc

- [ ] Create form trống.
- [ ] Edit form điền dữ liệu.
- [ ] Có 5 major.
- [ ] Empty fields có lỗi tiếng Việt.
- [ ] Name validation đúng.
- [ ] Email validation đúng.
- [ ] Age validation 18–30.
- [ ] GPA validation 0–4.
- [ ] Duplicate email báo lỗi dưới email.
- [ ] Dữ liệu form không mất khi validation fail.
- [ ] Create thành công redirect.
- [ ] F5 sau create không duplicate.
- [ ] Edit thành công.
- [ ] Giữ nguyên email của chính mình khi edit vẫn thành công.
- [ ] Không được dùng email của student khác.
- [ ] Form edit lỗi vẫn giữ đúng action `/students/{id}/edit`.

## Commit + Push

```bash
git add .
git commit -m "TODO 13: Implement student create and edit form"
git push origin <CURRENT_BRANCH>
```

---

# TODO 14 — STUDENT DETAIL TEMPLATE

## Tham chiếu

`Chapter6.md` — Bước 14.

## Mục tiêu

Tạo:

```text
templates/students/detail.html
```

Hiển thị:

- name;
- email;
- age;
- major;
- GPA;
- ID.

Có nút:

```text
Sửa
Xóa
Quay lại
```

Delete vẫn là POST.

Confirmation vẫn dùng:

```html
th:data-name
```

## Missing ID

Ví dụ:

```text
/students/999
```

phải:

```text
redirect:/students
```

và flash:

```text
Không tìm thấy sinh viên ID: 999
```

## Checklist

- [ ] Detail hiển thị đúng DB.
- [ ] Email là mailto.
- [ ] GPA format đúng.
- [ ] Delete confirmation đúng.
- [ ] Delete thành công.
- [ ] Delete ID không tồn tại không gây 500.
- [ ] Missing detail redirect đúng.

## Commit + Push

```bash
git add .
git commit -m "TODO 14: Implement student detail view"
git push origin <CURRENT_BRANCH>
```

---

# TODO 15 — FULL CRUD INTEGRATION TEST

## Tham chiếu

`Chapter6.md` — Bước 15.

## Mục tiêu

Đây là TODO cuối cùng.

Không được sửa kiến trúc mới ở TODO này nếu không thật sự cần. Chỉ fix lỗi integration.

## Chạy

```bash
mvn clean test
mvn spring-boot:run
```

## URL bắt buộc kiểm tra

```text
/                       GET
/students               GET
/students/create        GET
/students/create        POST
/students/1             GET
/students/1/edit        GET
/students/1/edit        POST
/students/1/delete      POST
```

## 12 kịch bản bắt buộc

### Test 1

Mở `/students`.

Expected:

```text
4 students seed
```

DB:

```sql
SELECT COUNT(*) FROM students;
```

Expected:

```text
4
```

### Test 2

Thêm:

```text
Võ Thị Én
en@fpt.edu.vn
20
MMT
3.0
```

Expected:

- success message;
- record mới;
- tiếng Việt đúng.

### Test 3

Submit form trống.

Expected:

- validation errors;
- DB count không đổi.

### Test 4

Thêm:

```text
AN@fpt.edu.vn
```

Expected:

```text
Email đã tồn tại
```

Không insert.

### Test 5

F5 sau create thành công.

Expected:

- không duplicate record.

### Test 6

Sửa student id=2:

```text
GPA = 3.9
```

Expected:

- update thành công;
- DB = 3.9.

### Test 7

Sửa student id=2 thành email của id=3.

Expected:

```text
Email đã được sinh viên khác sử dụng
```

Email id=2 không đổi.

### Test 8

Xóa student id=4.

Expected:

- success;
- id=4 không còn.

### Test 9

Mở:

```text
/students/4
```

Expected:

```text
Không tìm thấy sinh viên ID: 4
```

### Test 10

Stop app → start lại.

Expected:

- dữ liệu vẫn còn;
- seed không duplicate.

### Test 11

Sau khi xóa id=4, tạo student mới.

Expected:

- ID không quay lại 4;
- SQL Server IDENTITY tiếp tục tăng.

### Test 12

Update trực tiếp trong SQL Server:

```sql
UPDATE students
SET name = N'Test SSMS'
WHERE id = 1;
```

Refresh browser.

Expected:

```text
Test SSMS
```

Điều này chứng minh UI đọc dữ liệu từ DB.

---

## SQL verification

Agent phải kiểm tra tối thiểu:

```sql
SELECT * FROM students ORDER BY id;

SELECT COUNT(*) FROM students;

SELECT id, name FROM students ORDER BY id;

SELECT gpa FROM students WHERE id = 2;
```

Nếu cần kiểm tra schema:

```sql
SELECT
    COLUMN_NAME,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH,
    IS_NULLABLE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'students';
```

Expected:

```text
name  -> nvarchar
email -> nvarchar
major -> nvarchar
```

Các field đều NOT NULL theo specification.

---

## Final checklist

- [ ] `mvn clean test` pass.
- [ ] Application startup pass.
- [ ] SQL Server connection pass.
- [ ] Table `students` tồn tại.
- [ ] Seed đúng.
- [ ] Create pass.
- [ ] Read all pass.
- [ ] Read one pass.
- [ ] Update pass.
- [ ] Delete pass.
- [ ] Validation pass.
- [ ] Duplicate email pass.
- [ ] PRG pass.
- [ ] Flash messages pass.
- [ ] Vietnamese text pass.
- [ ] Bootstrap/layout pass.
- [ ] No normal-flow Whitelabel Error Page.
- [ ] `open-in-view` warning không xuất hiện.
- [ ] 12/12 integration scenarios pass.

---

# FINAL VERIFICATION — SAU TODO 15

Sau khi TODO 15 hoàn thành:

```bash
git status
git log --oneline --decorate -15
git remote -v
```

Kiểm tra:

1. Mỗi TODO có commit riêng.
2. Các commit đã push lên repository.
3. Không có uncommitted changes ngoài các file không thuộc project.
4. Branch local đang ở commit mới nhất.
5. Remote chứa toàn bộ lịch sử TODO.

Cuối cùng:

```bash
git push origin <CURRENT_BRANCH>
git status
```

Phải xác nhận push thành công.

---

# COMMIT HISTORY MONG MUỐN

Lịch sử lý tưởng:

```text
TODO 0: Inspect project baseline
TODO 1: Configure Maven dependencies
TODO 2: Align project structure
TODO 3: Configure SQL Server datasource
TODO 4: Verify application and database startup
TODO 5: Implement Student entity
TODO 6: Implement Student repository
TODO 7: Implement student service layer
TODO 8: Add initial student seed data
TODO 9: Implement student controller
TODO 10: Add home redirect controller
TODO 11: Implement Thymeleaf layout and CSS
TODO 12: Implement student list view
TODO 13: Implement student create and edit form
TODO 14: Implement student detail view
TODO 15: Complete CRUD integration test
```

**Không được squash các commit này thành một commit duy nhất.**

---

# NGUYÊN TẮC KẾT THÚC

Agent chỉ được tuyên bố hoàn thành toàn bộ bài khi:

```text
TODO 0  ✓
TODO 1  ✓ + commit + push
TODO 2  ✓ + commit + push
TODO 3  ✓ + commit + push
TODO 4  ✓ + commit + push
TODO 5  ✓ + commit + push
TODO 6  ✓ + commit + push
TODO 7  ✓ + commit + push
TODO 8  ✓ + commit + push
TODO 9  ✓ + commit + push
TODO 10 ✓ + commit + push
TODO 11 ✓ + commit + push
TODO 12 ✓ + commit + push
TODO 13 ✓ + commit + push
TODO 14 ✓ + commit + push
TODO 15 ✓ + commit + push
FINAL VERIFICATION ✓
```

**Nếu một TODO chưa commit/push thành công thì tuyệt đối không được thực hiện TODO tiếp theo.**

---

# SOURCE OF TRUTH

Tài liệu này là execution plan cho agent.

`Chapter6.md` là specification về nội dung bài, kiến trúc, code logic, cấu trúc package, database, routes, Thymeleaf, validation và checklist.

Khi có mâu thuẫn:

1. Ưu tiên yêu cầu trực tiếp của người dùng.
2. Sau đó ưu tiên specification trong `Chapter6.md`.
3. Sau đó mới dùng judgement của agent để xử lý chi tiết kỹ thuật còn thiếu.
4. Không tự ý thay đổi logic đã được specification quy định.

Repository mục tiêu:

```text
https://github.com/longpham58/HSF302-FU26.git
```
