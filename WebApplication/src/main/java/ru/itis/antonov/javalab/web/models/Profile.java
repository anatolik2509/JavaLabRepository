package ru.itis.antonov.javalab.web.models;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Profile {
    private int id;
    private String login;
    private String password;
}
