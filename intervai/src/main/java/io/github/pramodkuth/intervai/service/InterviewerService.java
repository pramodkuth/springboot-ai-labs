package io.github.pramodkuth.intervai.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import io.github.pramodkuth.intervai.domain.model.Interview;
import reactor.core.publisher.Flux;

import java.util.List;

@AiService(streamingChatModel = "llamaModel",wiringMode = AiServiceWiringMode.EXPLICIT)
public interface InterviewerService {
    @SystemMessage("You are an interviewer.Don't ask lengthy question")
    @UserMessage("Ask a question related to :{{category}}.")
    Flux<String> question(@V("category") String category);

    @SystemMessage("You are an interview evaluator.")
    @UserMessage("Go thru the question and answer in the given list of {{interactions}}.Evaluate the question and given answer and share a overall short feedback.")
    Flux<String> eval(@V("interactions") List<Interview.Interaction> interactions);
}
