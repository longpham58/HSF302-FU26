package com.hsf302.chapter6;

import com.hsf302.chapter6.entity.Student;
import com.hsf302.chapter6.repository.StudentRepository;
import com.hsf302.chapter6.service.StudentService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Chapter6ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Order(1)
    void contextLoads() {
        assertThat(studentService).isNotNull();
        assertThat(studentRepository).isNotNull();
    }

    @Test
    @Order(2)
    void testHomeRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"));
    }

    @Test
    @Order(3)
    void testListStudents() throws Exception {
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/list"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    @Order(4)
    void testStudentDetailFound() throws Exception {
        List<Student> students = studentService.findAll();
        assertThat(students).isNotEmpty();
        Long firstId = students.get(0).getId();

        mockMvc.perform(get("/students/" + firstId))
                .andExpect(status().isOk())
                .andExpect(view().name("students/detail"))
                .andExpect(model().attributeExists("student"));
    }

    @Test
    @Order(5)
    void testStudentDetailNotFound() throws Exception {
        mockMvc.perform(get("/students/999999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attributeExists("errorMsg"));
    }

    @Test
    @Order(6)
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/students/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attributeExists("majors"));
    }

    @Test
    @Order(7)
    void testCreateValidationFailEmpty() throws Exception {
        long countBefore = studentRepository.count();

        mockMvc.perform(post("/students/create")
                        .param("name", "")
                        .param("email", "")
                        .param("age", "")
                        .param("major", "")
                        .param("gpa", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().hasErrors());

        assertThat(studentRepository.count()).isEqualTo(countBefore);
    }

    @Test
    @Order(8)
    void testCreateDuplicateEmail() throws Exception {
        long countBefore = studentRepository.count();

        mockMvc.perform(post("/students/create")
                        .param("name", "Nguyễn An Mới")
                        .param("email", "AN@fpt.edu.vn")
                        .param("age", "20")
                        .param("major", "CNTT")
                        .param("gpa", "3.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeHasFieldErrorCode("student", "email", "duplicate"));

        assertThat(studentRepository.count()).isEqualTo(countBefore);
    }

    @Test
    @Order(9)
    void testCreateSuccess() throws Exception {
        mockMvc.perform(post("/students/create")
                        .param("name", "Võ Thị Én")
                        .param("email", "en@fpt.edu.vn")
                        .param("age", "20")
                        .param("major", "MMT")
                        .param("gpa", "3.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attribute("successMsg", "Thêm sinh viên thành công!"));

        assertThat(studentService.isEmailTaken("en@fpt.edu.vn", null)).isTrue();
    }

    @Test
    @Order(10)
    void testShowEditForm() throws Exception {
        List<Student> students = studentService.findAll();
        Long id = students.get(0).getId();

        mockMvc.perform(get("/students/" + id + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("isEdit", true));
    }

    @Test
    @Order(11)
    void testUpdateStudentSuccess() throws Exception {
        List<Student> students = studentService.findAll();
        Student first = students.get(0);

        mockMvc.perform(post("/students/" + first.getId() + "/edit")
                        .param("name", first.getName())
                        .param("email", first.getEmail())
                        .param("age", String.valueOf(first.getAge()))
                        .param("major", first.getMajor())
                        .param("gpa", "3.9"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attribute("successMsg", "Cập nhật thành công!"));

        Student updated = studentService.findById(first.getId()).orElseThrow();
        assertThat(updated.getGpa()).isEqualTo(3.9);
    }

    @Test
    @Order(12)
    void testUpdateDuplicateEmailOtherStudent() throws Exception {
        List<Student> students = studentService.findAll();
        assertThat(students.size()).isGreaterThanOrEqualTo(2);
        Student first = students.get(0);
        Student second = students.get(1);

        mockMvc.perform(post("/students/" + first.getId() + "/edit")
                        .param("name", first.getName())
                        .param("email", second.getEmail())
                        .param("age", String.valueOf(first.getAge()))
                        .param("major", first.getMajor())
                        .param("gpa", "3.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("students/form"))
                .andExpect(model().attributeHasFieldErrorCode("student", "email", "duplicate"));
    }

    @Test
    @Order(13)
    void testDeleteStudent() throws Exception {
        Student toDelete = studentRepository.save(new Student("Tạm Thời", "tamthoi@fpt.edu.vn", 20, "CNTT", 3.0));
        Long delId = toDelete.getId();

        mockMvc.perform(post("/students/" + delId + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attribute("successMsg", "Xóa sinh viên thành công!"));

        assertThat(studentService.findById(delId)).isEmpty();
    }

    @Test
    @Order(14)
    void testDeleteNonExistentStudent() throws Exception {
        mockMvc.perform(post("/students/999999/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students"))
                .andExpect(flash().attribute("errorMsg", "Không tìm thấy sinh viên để xóa!"));
    }
}
