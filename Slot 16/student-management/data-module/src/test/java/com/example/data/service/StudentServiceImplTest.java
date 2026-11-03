package com.example.data.service;

import com.example.data.exception.StudentNotFoundException;
import com.example.data.exception.ValidationException;
import com.example.data.pojo.Student;
import com.example.data.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(StudentServiceImpl.class)
class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @Test
    void create_valid_normalizesAndSaves() {
        Student student = studentService.create(new Student("  Nguyen An  ", "  AN@FPT.EDU.VN ", 20));
        assertNotNull(student.getId());
        assertEquals("Nguyen An", student.getName());
        assertEquals("an@fpt.edu.vn", student.getEmail());
    }

    @Test
    void create_blankName_throws() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> studentService.create(new Student("   ", "a@x.com", 20)));
        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    void create_nameTooLong_throws() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> studentService.create(new Student("x".repeat(101), "a@x.com", 20)));
        assertEquals("Name must be at most 100 characters", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "a@b", "@x.com", "a b@x.com"})
    void create_invalidEmail_throws(String email) {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> studentService.create(new Student("An", email, 20)));
        assertEquals("Invalid email", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {17, 61})
    void create_ageOutOfRange_throws(int age) {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> studentService.create(new Student("An", "an@x.com", age)));
        assertEquals("Age must be between 18 and 60", exception.getMessage());
    }

    @Test
    void create_boundaryAges_ok() {
        assertNotNull(studentService.create(new Student("A", "a18@x.com", 18)).getId());
        assertNotNull(studentService.create(new Student("B", "b60@x.com", 60)).getId());
    }

    @Test
    void create_duplicateEmail_ignoreCase_throws() {
        studentService.create(new Student("An", "an@x.com", 20));
        ValidationException exception = assertThrows(ValidationException.class,
                () -> studentService.create(new Student("An 2", "AN@X.COM", 21)));
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void update_sameEmailOfItself_ok() {
        Student student = studentService.create(new Student("An", "an@x.com", 20));
        Student edit = new Student(student.getId(), "An Updated", "an@x.com", 25);
        Student updated = studentService.update(edit);
        assertEquals("An Updated", updated.getName());
        assertEquals(25, studentService.findById(student.getId()).getAge());
    }

    @Test
    void update_emailOfAnotherStudent_throws() {
        studentService.create(new Student("An", "an@x.com", 20));
        Student binh = studentService.create(new Student("Binh", "binh@x.com", 21));
        Student edit = new Student(binh.getId(), "Binh", "an@x.com", 21);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> studentService.update(edit));
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void findById_notFound_throws() {
        StudentNotFoundException exception = assertThrows(StudentNotFoundException.class,
                () -> studentService.findById(999L));
        assertEquals("Student not found: 999", exception.getMessage());
    }

    @Test
    void delete_existing_and_notFound() {
        Student student = studentService.create(new Student("An", "an@x.com", 20));
        studentService.delete(student.getId());
        assertThrows(StudentNotFoundException.class, () -> studentService.findById(student.getId()));
        assertThrows(StudentNotFoundException.class, () -> studentService.delete(999L));
    }

    @Test
    void search_blank_returnsAll_and_trimsKeyword() {
        studentService.create(new Student("An", "an@x.com", 20));
        studentService.create(new Student("Binh", "binh@x.com", 21));
        assertEquals(2, studentService.search("   ").size());
        assertEquals(2, studentService.search(null).size());
        assertEquals(1, studentService.search(" binh ").size());
    }
}
