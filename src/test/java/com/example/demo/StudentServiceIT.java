package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class StudentServiceIT {

  @Autowired //!
  private StudentRepository studentRepository;
  @Autowired //!!
  private StudentService studentService;

  @Test
  @Rollback //(assuming there is no student with id==1 in data base, otherwise: prepare/use fresh/see links)
  void removeStudentByIdExists() throws StudentExistsException {
    // Given:
    Student student = Student.of(1L, "Drake", "drake@gmail.com");
    studentRepository.save(student);

    // When:
    studentService.removeStudentByID(1L);

    // Then:
    assertFalse(studentRepository.findById(1L).isPresent());
  }

  @Test
  @Rollback // in any case
  void removeStudentByIdNotExists() throws StudentExistsException {
    // Given:
    // nothing, we assume "empty db"

    // When:
    StudentExistsException thrown = assertThrows(
            StudentExistsException.class,
            () -> studentService.removeStudentByID(1L),
            "Expected testee.removeStudentByID to throw, but it didn't");
    // Then:
    assertNotNull(thrown);
    assertTrue(thrown.getMessage().contains("Student doesn't exist"));
  }
}
