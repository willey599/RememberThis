package main.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "node")

public class node {
    @Id
    @Column(name="node_id")
    private int node_id;
    
    @Column(name="answer")
    private String answer;    
}
