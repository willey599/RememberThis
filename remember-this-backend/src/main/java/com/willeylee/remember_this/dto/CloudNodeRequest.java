package com.willeylee.remember_this.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloudNodeRequest{
    private String nodeText;
    private String nodeContext1;
    private String nodeContext2;
    private String nodeContext3;
    private int nodeId;
}