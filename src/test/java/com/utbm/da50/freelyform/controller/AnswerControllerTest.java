package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.service.AnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AnswerControllerTest {
    @Mock
    private AnswerService answerService;

    @InjectMocks
    private AnswerController answerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


    }

    @Test
    void submitAnswer() {
    }

    @Test
    void getAnswer() {
    }
}