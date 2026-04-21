package io.github.pramodkuth.intervai.graph.nodes;

import io.github.pramodkuth.intervai.common.utils.QuestionCategory;
import io.github.pramodkuth.intervai.domain.InterviewState;
import io.github.pramodkuth.intervai.domain.model.Interview;
import io.github.pramodkuth.intervai.service.InterviewerService;
import org.apache.logging.log4j.util.Strings;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class Interviewer implements AsyncNodeAction<InterviewState> {
    private final InterviewerService interviewerService;

    public Interviewer(InterviewerService interviewerService) {
        this.interviewerService = interviewerService;
    }

    @Override
    public CompletableFuture<Map<String, Object>> apply(InterviewState interviewState) {
        if (!QuestionCategory.isEmpty()) {
            return askQuestion(interviewState);
        } else {
            return eval(interviewState);
        }

    }

    private Object newInteraction(InterviewState interviewState) {
        return new Interview.Interaction(interviewState.question(), interviewState.answer());
    }

    private CompletableFuture<Map<String, Object>> eval(InterviewState state) {
        state.interview().addInteraction(new Interview.Interaction(state.question(),state.answer()));//last interaction
        Flux<String> feedback = interviewerService.eval(state.interview().getInteractions());
        return feedback.collectList().map(token -> String.join(Strings.EMPTY, token))
                .map(fb ->
                        Map.of(InterviewState.FEEDBACK_KEY, (Object) fb, InterviewState.ANS_KEY, Strings.EMPTY,
                                InterviewState.QUESTION_KEY, Strings.EMPTY)
                ).toFuture();
    }

    private CompletableFuture<Map<String, Object>> askQuestion(InterviewState state) {
        String category = QuestionCategory.nextCategory();
        Flux<String> question = interviewerService.question(category);
        if (state.answered()) {
            var interaction = newInteraction(state);
            return question.collectList().map(token -> String.join(Strings.EMPTY, token))
                    .map(ques -> Map.of(InterviewState.QUESTION_KEY, ques,
                            InterviewState.INTERACTION_KEY, interaction, InterviewState.ANSWERED_KEY, false,
                            InterviewState.ANS_KEY, Strings.EMPTY)).toFuture();

        }
        return question.collectList().map(token -> String.join(Strings.EMPTY, token))
                .log().map(ques ->
                        Map.of(InterviewState.QUESTION_KEY, (Object) ques, InterviewState.ANSWERED_KEY, false,
                                InterviewState.ANS_KEY, Strings.EMPTY)
                ).toFuture();

    }
}
