package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GroqService groqService;

    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = groqService.getAnswer(prompt);
        log.info("RESPONSE FROM AI : {}", aiResponse);

        return aiResponse;
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
        You are an expert fitness coach and sports scientist.
        Analyze the following fitness activity and respond with ONLY a valid JSON object.
        Do NOT include any text, explanation, or markdown before or after the JSON.
        Do NOT wrap it in ```json``` blocks.
        
        Activity Details:
        - Activity Type: %s
        - Duration: %d minutes
        - Calories Burned: %d kcal
        - Additional Metrics: %s
        
        Respond in this EXACT JSON format only:
        {
            "analysis": {
                "overall": "Overall performance summary",
                "pace": "Pace analysis based on activity type and duration",
                "heartRate": "Heart rate zone analysis if available, else general guidance",
                "caloriesBurned": "Calorie burn evaluation and what it means"
            },
            "improvements": [
                {
                    "area": "Specific area e.g. Endurance / Form / Intensity",
                    "recommendation": "Concrete actionable recommendation"
                }
            ],
            "suggestions": [
                {
                    "workout": "Next workout name",
                    "description": "Why this workout and how to perform it"
                }
            ],
            "safety": [
                "Specific safety guideline 1",
                "Specific safety guideline 2"
            ]
        }
        """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}
