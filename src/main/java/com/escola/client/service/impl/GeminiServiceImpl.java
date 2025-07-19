package com.escola.client.service.impl;

import com.escola.client.service.ArtificalInteligenceService;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.ThinkingConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("gemini")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class GeminiServiceImpl implements ArtificalInteligenceService {

    @Override // Certifique-se de que o método implementa a interface
    public String generateText(String prompt) {
        Client client = new Client();

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .thinkingConfig(
                                ThinkingConfig.builder()
                                        .thinkingBudget(0)
                                        .build())
                        .build();


        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        config);

        return response.text();
    }
}