package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Question {

    private final int id;
    private final String content;
    private final String answer;
}
