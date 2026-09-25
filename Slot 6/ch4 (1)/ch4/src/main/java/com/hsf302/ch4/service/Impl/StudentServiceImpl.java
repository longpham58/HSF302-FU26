package com.hsf302.ch4.service.Impl;

import com.hsf302.ch4.repository.StudentRepository;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)          // mặc định: mọi method chỉ ĐỌC
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
}