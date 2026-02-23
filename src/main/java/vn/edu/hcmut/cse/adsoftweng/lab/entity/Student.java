package vn.edu.hcmut.cse.adsoftweng.lab.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private int age;

}