package dev.syatimwaraph.quencallerie_mngt_v1.model;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "users")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private Integer age;

    private String email;
}
