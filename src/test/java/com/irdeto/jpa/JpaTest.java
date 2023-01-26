package com.irdeto.jpa;

import com.irdeto.firstproject.services.PaymentService;
import com.irdeto.firstproject.services.PaymentServiceImpl;
import com.irdeto.jpa.entities.Student;
import com.irdeto.jpa.repositories.StudentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testSaveStudent(){
        Student student = new Student();
        student.setId(1l);
        student.setName("irdeto");
        student.setTestScore(99);
        studentRepository.save(student);

        Student studentFound = studentRepository.findById(1l).get();
        Assert.assertNotNull(studentFound);
    }
}
