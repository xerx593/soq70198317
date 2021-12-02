package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  @Autowired
  private StudentRepository studentRepo;

  public void removeStudentByID(Long studentId) throws StudentExistsException {
    boolean exists = studentRepo.existsById(studentId);
    if (!exists) {
      throw new StudentExistsException("Student doesn't exist");
    }
    studentRepo.deleteById(studentId);
  }
}
