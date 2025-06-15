package com.willeylee.remember_this.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "node")

public class Node {
    @Id
    @Column(name="node_id")
    private int node_id;
    
    @Column(name="answer")
    private String answer;    

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
