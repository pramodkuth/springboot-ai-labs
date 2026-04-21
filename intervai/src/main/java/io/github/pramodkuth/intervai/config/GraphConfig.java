package io.github.pramodkuth.intervai.config;

import io.github.pramodkuth.intervai.domain.InterviewState;
import io.github.pramodkuth.intervai.graph.edges.InterviewGuard;
import io.github.pramodkuth.intervai.graph.nodes.Candidate;
import io.github.pramodkuth.intervai.graph.nodes.Interviewer;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.GraphDefinition;
import org.bsc.langgraph4j.GraphStateException;
import org.bsc.langgraph4j.StateGraph;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class GraphConfig {
    private final Candidate candidate;
    private final Interviewer interviewer;
    private final InterviewGuard interviewGuard;
    private static final String CANDIDATE_ID = "candidate";
    private static final String INTERVIEWER_ID = "interviewer";

    public GraphConfig(Candidate candidate, Interviewer interviewer, InterviewGuard interviewGuard) {
        this.candidate = candidate;
        this.interviewer = interviewer;
        this.interviewGuard = interviewGuard;
    }

    @Bean
    public CompiledGraph<InterviewState> interviewGraph() throws GraphStateException {
        return new StateGraph<>(InterviewState.SCHEMA, InterviewState::new)
                .addNode(CANDIDATE_ID, candidate)
                .addNode(INTERVIEWER_ID, interviewer)
                .addEdge(GraphDefinition.START, INTERVIEWER_ID)
                .addEdge(CANDIDATE_ID, INTERVIEWER_ID)
                .addConditionalEdges(INTERVIEWER_ID, interviewGuard::checkProgress,
                        Map.of(GraphDefinition.END, GraphDefinition.END, InterviewState.CONTINUE, CANDIDATE_ID))
                .compile();
    }
}
