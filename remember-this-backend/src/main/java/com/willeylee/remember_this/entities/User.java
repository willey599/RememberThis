package com.willeylee.remember_this.entities;

import org.springframework.data.annotation.Id;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name="email")
    private String email;

    @OneToMany(mappedBy = "node_id", cascade = CascadeType.ALL)
    @Column(name="node_id")
    private Set<Node> Node = new HashSet<>();

}
