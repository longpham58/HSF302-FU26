USE StudentManagementDB;
GO

IF OBJECT_ID(N'dbo.students', N'U') IS NULL
BEGIN
    THROW 50001, 'Run the JavaFX application once so Hibernate can create dbo.students.', 1;
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.students)
BEGIN
    INSERT INTO dbo.students(name, email, age) VALUES
        (N'Nguyễn Văn An', 'an.nv@fpt.edu.vn', 20),
        (N'Trần Thị Bình', 'binh.tt@fpt.edu.vn', 21),
        (N'Lê Văn Cường', 'cuong.lv@gmail.com', 22),
        (N'Phạm Thị Dung', 'dung.pt@fpt.edu.vn', 19),
        (N'Hoàng Văn Em', 'em.hv@gmail.com', 23);
END;
GO
