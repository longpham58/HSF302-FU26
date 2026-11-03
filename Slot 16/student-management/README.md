# Student Management System

Ứng dụng quản lý sinh viên dùng JavaFX, Spring Boot và Spring Data JPA.

## Yêu cầu

- JDK 21
- Maven 3.9+
- SQL Server chạy trên `localhost:1433`
- Database `StudentManagementDB`

## Cấu hình database

Project dùng database riêng `StudentManagementDB` để tránh đụng bảng `students` của các bài khác. Tạo database:

```sql
IF DB_ID('StudentManagementDB') IS NULL CREATE DATABASE StudentManagementDB;
```

Có thể chạy script `docs/sql/01-create-database.sql`. Sau đó kiểm tra `ui-module/src/main/resources/application.properties`, cập nhật server, username và password SQL Server theo máy của bạn. Không commit mật khẩu thật vào Git.

Chạy ứng dụng một lần để Hibernate tạo bảng `students`, rồi chạy `docs/sql/02-seed-sample-data.sql` để thêm dữ liệu demo. Các script không xóa hoặc sửa database `StudentDB` hiện có.

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

## Ảnh chụp màn hình

Đặt bốn ảnh kiểm thử vào `docs/screenshots/`: `01-list.png`, `02-add.png`, `03-validation.png` và `04-delete-confirm.png`.

## Cấu trúc

`StudentController` → `StudentService` → `StudentServiceImpl` → `StudentRepository` → SQL Server

| Module | Nội dung |
|---|---|
| `data-module` | Student entity, repository, service và validation |
| `ui-module` | JavaFX, FXML, CSS và Spring Boot bootstrap |
