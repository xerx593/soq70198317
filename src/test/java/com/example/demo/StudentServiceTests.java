package com.example.demo;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudenServiceTests {

  @Mock
  private StudentRepository studentRepoMock;
  @InjectMocks
  private StudentService testee;

  @Test
  void removeStudentByIdExists() {
    // Given:
    Student student = Student.of(1L, "Drake", "drake@gmail.com");
    when(studentRepoMock.existsById(1L)).thenReturn(true);
    doNothing().when(studentRepoMock).delete(student);

    // When:
    testee.removeStudentByID(1L);

    // Then:
    verify(studentRepoMock).existsById(1L);
    verify(studentRepoMock).deleteById(1L);
  }

  @Test
  void removeStudentByIdNotExists() {
    // Given:
    when(studentRepoMock.existsById(1L)).thenReturn(false);

    // When:
    StudentExistsException thrown = assertThrows(
            StudentExistsException.class,
            () -> testee.removeStudentByID(1L),
            "Expected testee.removeStudentByID to throw, but it didn't");
    // Then:
    assertNotNull(thrown);
    assertTrue(thrown.getMessage().contains("Student doesn't exist"));
  }
}
