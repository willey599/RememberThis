package com.willeylee.remember_this.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CloudNodePositionRequest{
    private int nodeId;
    private int nodeXPosition;
    private int nodeYPosition;
}