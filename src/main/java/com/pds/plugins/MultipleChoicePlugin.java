package com.pds.plugins;

import com.pds.core.Question;
import com.pds.core.QuestionPlugin;
import java.util.List;
import java.util.Map;

public class MultipleChoicePlugin implements QuestionPlugin {
    @Override
    public String getType() {
        return "multiple_choice";
    }
    
    @Override
    public Question createQuestion(String id, String text, Object data) {
        if (!(data instanceof Map)) {
            throw new IllegalArgumentException("Los datos deben ser un Map");
        }
        
        Map<String, Object> questionData = (Map<String, Object>) data;
        List<String> options = (List<String>) questionData.get("options");
        Integer correctOption = (Integer) questionData.get("correctOption");
        
        if (options == null || correctOption == null) {
            throw new IllegalArgumentException("Faltan datos requeridos: options o correctOption");
        }
        
        return new MultipleChoiceQuestion(id, text, options, correctOption);
    }
    
    @Override
    public boolean validateData(Object data) {
        if (!(data instanceof Map)) {
            return false;
        }
        
        Map<String, Object> questionData = (Map<String, Object>) data;
        List<String> options = (List<String>) questionData.get("options");
        Integer correctOption = (Integer) questionData.get("correctOption");
        
        return options != null && 
               !options.isEmpty() && 
               correctOption != null && 
               correctOption >= 0 && 
               correctOption < options.size();
    }
} 