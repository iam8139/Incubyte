package com.incubyte.calculator.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ValidationError {
    private String msg;
}
