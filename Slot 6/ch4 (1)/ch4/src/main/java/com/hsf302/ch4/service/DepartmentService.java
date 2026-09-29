package com.hsf302.ch4.service;

import java.util.List;
import com.hsf302.ch4.pojo.Department;

public interface DepartmentService {
    long count();                                   // TODO 6
    boolean existsById(Long id);                    // TODO 6
    List<Department> findDepartmentsWithoutStudents();  // TODO 11d
}