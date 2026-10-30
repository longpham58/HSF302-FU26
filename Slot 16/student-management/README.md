# Student Management System

Ứng dụng quản lý sinh viên dùng JavaFX, Spring Boot và Spring Data JPA.

## Yêu cầu

- JDK 21
- Maven 3.9+
- SQL Server chạy trên `localhost:1433`
- Database `StudentDB`

## Cấu hình database

Tạo database nếu chưa có:

```sql
IF DB_ID('StudentDB') IS NULL CREATE DATABASE StudentDB;
```

Ứng dụng mặc định dùng SQL Server user `sa`. Đặt mật khẩu qua biến môi trường `DB_PASSWORD` trước khi chạy:

```powershell
$env:DB_PASSWORD = "your-sql-server-password"
```

URL và các thiết lập JPA nằm trong `ui-module/src/main/resources/application.properties`.

## Build

Chạy từ thư mục `student-management`:

```bash
mvn clean install
```

## Chạy ứng dụng

Từ thư mục `student-management`:

```bash
mvn -pl ui-module spring-boot:run
```

Hoặc chạy `com.example.ui.Launcher` từ IDE.

## Cấu trúc

`StudentController` → `StudentService` → `StudentServiceImpl` → `StudentRepository` → SQL Server

| Module | Nội dung |
|---|---|
| `data-module` | Student entity, repository, service và validation |
| `ui-module` | JavaFX, FXML, CSS và Spring Boot bootstrap |
