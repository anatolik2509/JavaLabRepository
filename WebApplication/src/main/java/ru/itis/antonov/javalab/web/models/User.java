package ru.itis.antonov.javalab.web.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
}
