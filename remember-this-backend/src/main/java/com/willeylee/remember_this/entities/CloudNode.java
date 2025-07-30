package com.willeylee.remember_this.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name="cloud_node")
public class CloudNode {
    @Id
    @Column(name="node_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int nodeId;

    @Column(name="node_text")
    private String nodeText;

    @Column(name="node_x_position")
    private int nodeXPosition;

    @Column(name="node_y_position")
    private int nodeYPosition;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


}
