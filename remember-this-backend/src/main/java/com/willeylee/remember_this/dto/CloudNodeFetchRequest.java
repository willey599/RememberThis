package com.willeylee.remember_this.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloudNodeFetchRequest{
    private int nodeId;
    private int xPosition;
    private int yPosition;
    private String nodeText;
}