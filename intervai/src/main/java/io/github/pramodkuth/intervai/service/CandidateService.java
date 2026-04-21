package io.github.pramodkuth.intervai.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(streamingChatModel = "qwenModel",wiringMode = AiServiceWiringMode.EXPLICIT)
public interface CandidateService {
    @SystemMessage("You are interview candidate.Try to answer precisely")
    @UserMessage("Answer this question:{{question}}.")
    Flux<String> answer(@V("question") String question);
}
