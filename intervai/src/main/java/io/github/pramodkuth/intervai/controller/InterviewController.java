package io.github.pramodkuth.intervai.controller;

import io.github.pramodkuth.intervai.common.utils.QuestionCategory;
import io.github.pramodkuth.intervai.domain.InterviewState;
import org.apache.logging.log4j.util.Strings;
import org.bsc.async.FlowGenerator;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.NodeOutput;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.adapter.JdkFlowAdapter;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping(value = "/intervai", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public class InterviewController {
    private final CompiledGraph<InterviewState> compiledGraph;

    public InterviewController(CompiledGraph<InterviewState> compiledGraph) {
        this.compiledGraph = compiledGraph;
    }

    @GetMapping("/interview")
    public Flux<String> streamWorkflow() {
        QuestionCategory.reset();
        var publisher = FlowGenerator.toPublisher(compiledGraph.stream(Map.of()));
        return Flux.from(JdkFlowAdapter.flowPublisherToFlux(publisher)
                        .filter(node -> !node.isEND()))
                .map(NodeOutput::state).map(this::response);
    }

    private String response(InterviewState state) {
        if (StringUtils.hasText(state.question())) {
            return String.join("\n\n", "Interviewer:", state.question());
        } else if (StringUtils.hasText(state.answer())) {
            return String.join("\n\n", "Candidate:", state.answer());
        } else if (StringUtils.hasText(state.feedback())) {
            return String.join("\n\n", "Feedback:", state.feedback());
        }
        return Strings.EMPTY;

    }
}
