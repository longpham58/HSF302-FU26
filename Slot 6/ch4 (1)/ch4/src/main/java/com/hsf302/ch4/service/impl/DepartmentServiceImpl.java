package com.hsf302.ch4.service.impl;

import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.repository.DepartmentRepository;
import com.hsf302.ch4.repository.StudentRepository;
import com.hsf302.ch4.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;      // dùng ở TODO 22 (chuyển sinh viên)

    // TODO 6
    @Override
    public long count() {
        return departmentRepository.count();
    }

    @Override
    public boolean existsById(Long id) {
        return departmentRepository.existsById(id);
    }

    // TODO 11
    @Override
    public java.util.List<com.hsf302.ch4.pojo.Department> findDepartmentsWithoutStudents() {
        return departmentRepository.findByStudentsIsEmpty();
    }

    // TODO 14
    @Override
    public java.util.List<com.hsf302.ch4.dto.DepartmentStatDTO> getStatistics() {
        return departmentRepository.getDepartmentStats();
    }

    // TODO 16
    @Override
    public java.util.Optional<com.hsf302.ch4.pojo.Department> findByCode(String code) {
        return departmentRepository.findByCode(code);
    }

    @Override
    public com.hsf302.ch4.pojo.Department getWithStudents(String code) {
        return departmentRepository.findByCodeWithStudents(code)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + code));
    }

    @Override
    @Transactional
    public int transferStudentsAndDelete(String fromCode, String toCode) {
        if (fromCode.equals(toCode)) {
            throw new IllegalArgumentException("Khoa nguồn và khoa đích phải khác nhau");
        }
        Department from = departmentRepository.findByCode(fromCode)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + fromCode));
        Department to = departmentRepository.findByCode(toCode)
                .orElseThrow(() -> new IllegalArgumentException("Department not found: " + toCode));

        int moved = studentRepository.transferStudents(from, to);   // 1. chuyển FK sang khoa mới
        departmentRepository.deleteById(from.getId());              // 2. khoa cũ đã rỗng → xoá được
        return moved;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll(Sort.by("id"));
    }
}
