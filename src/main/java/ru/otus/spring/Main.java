package ru.otus.spring;

import lombok.val;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        val context = new ClassPathXmlApplicationContext("/spring-context.xml");
        val questionService = context.getBean(QuestionService.class);
        val questions = questionService.getAllQuestions();
        try (val br = new BufferedReader(new InputStreamReader(System.in))) {
            for (Question question : questions) {
                System.out.println(question.getContent());
                System.out.print("Please, enter your answer:");
                String answer = br.readLine();
                System.out.print("Your answer is: " + answer + ". ");
                if (answer.equalsIgnoreCase(question.getAnswer()))
                    System.out.println("Yeah, you're right!");
                else
                    System.out.println("No, it's wrong!");
                System.out.println("-----------------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
