package io.github.pramodkuth.intervai.graph.nodes;

import io.github.pramodkuth.intervai.domain.InterviewState;
import io.github.pramodkuth.intervai.service.CandidateService;
import org.apache.logging.log4j.util.Strings;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class Candidate implements AsyncNodeAction<InterviewState> {
    private final CandidateService candidateService;

    public Candidate(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @Override
    public CompletableFuture<Map<String, Object>> apply(InterviewState interviewState) {
        Flux<String> answer = candidateService.answer(interviewState.question());
        return answer.collectList().map(token -> String.join(Strings.EMPTY,token))
                .map(ans -> Map.of(InterviewState.QUESTION_KEY, Strings.EMPTY,InterviewState.ANS_KEY,(Object) ans,InterviewState.ANSWERED_KEY,true)).toFuture();
    }
}
