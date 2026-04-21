package io.github.pramodkuth.intervai.graph.edges;

import io.github.pramodkuth.intervai.domain.InterviewState;
import org.apache.logging.log4j.util.Strings;
import org.bsc.langgraph4j.GraphDefinition;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
@Component
public class InterviewGuard {
    public CompletableFuture<String> checkProgress(InterviewState state) {
        return CompletableFuture.supplyAsync(() -> {
            if (!Strings.isBlank(state.feedback())) {
                return GraphDefinition.END;
            }
            return InterviewState.CONTINUE;
        });
    }
}
