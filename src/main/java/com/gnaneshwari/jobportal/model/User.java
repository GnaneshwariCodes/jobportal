package com.gnaneshwari.jobportal.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")   // "user" is a reserved word in some databases
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;   // stores the BCrypt hash, never plain text

    @Enumerated(EnumType.STRING)
    private Role role;
}