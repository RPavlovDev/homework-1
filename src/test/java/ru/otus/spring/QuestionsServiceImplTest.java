package ru.otus.spring;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.QuestionServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class QuestionsServiceImplTest {

    private static List<Question> questionList;
    private static QuestionService questionService;

    @BeforeAll
    static void beforeAll() {
        questionList = new ArrayList<>();
        questionList.add(new Question(1, "Test question 1", "Test answer 1"));
        questionList.add(new Question(2, "Test question 2", "Test answer 2"));
        QuestionDao questionDao;
        questionDao = Mockito.mock(QuestionDao.class);
        when(questionDao.getAll()).thenReturn(new ArrayList<>(questionList));
        when(questionDao.findById(1)).thenReturn(questionList.get(0));
        when(questionDao.findById(2)).thenReturn(questionList.get(1));
        questionService = new QuestionServiceImpl(questionDao);
    }

    @Test
    void getAllQuestions() {
        List<Question> allQuestions = questionService.getAllQuestions();
        assertNotNull(allQuestions);
        assertEquals(questionList.size(), allQuestions.size());
        assertEquals(questionList, allQuestions);
    }

    @Test
    void getQuestionById() {
        for (int i = 1; i < 2; i++) {
            Question question = questionService.getQuestionById(i);
            assertNotNull(question);
            assertEquals(i, question.getId());
        }
        Question question = questionService.getQuestionById(5);
        assertNull(question);
    }
}
