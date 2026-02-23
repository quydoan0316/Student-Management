package vn.edu.hcmut.cse.adsoftweng.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;
import vn.edu.hcmut.cse.adsoftweng.lab.repository.StudentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    // GET ALL + SEARCH
    public List<Student> getAll(String keyword) {
        List<Student> students = repository.findAll();

        if (keyword != null && !keyword.isEmpty()) {
            students = students.stream()
                    .filter(s -> s.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return students;
    }

    public Student getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void save(Student student) {
        if (student.getId() == null || student.getId().isEmpty()) {
            student.setId(UUID.randomUUID().toString());
        }
        repository.save(student);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}