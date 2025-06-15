package com.pds.plugins;

import com.pds.core.Question;
import java.util.List;
import java.util.ArrayList;

public class MultipleChoiceQuestion implements Question {
    private final String id;
    private final String text;
    private final List<String> options;
    private final int correctOption;
    
    public MultipleChoiceQuestion(String id, String text, List<String> options, int correctOption) {
        this.id = id;
        this.text = text;
        this.options = new ArrayList<>(options);
        this.correctOption = correctOption;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public String getType() {
        return "multiple_choice";
    }
    
    @Override
    public boolean checkAnswer(String answer) {
        try {
            int selectedOption = Integer.parseInt(answer);
            return selectedOption == correctOption;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public String getCorrectAnswer() {
        return String.valueOf(correctOption);
    }
    
    public List<String> getOptions() {
        return new ArrayList<>(options);
    }
} 