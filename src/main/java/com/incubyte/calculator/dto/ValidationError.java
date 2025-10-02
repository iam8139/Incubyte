package com.incubyte.calculator.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ValidationError {
    private String msg;
}