package io.github.pramodkuth.intervai.domain;

import io.github.pramodkuth.intervai.domain.model.Interview;
import org.apache.logging.log4j.util.Strings;
import org.bsc.langgraph4j.state.AgentState;
import org.bsc.langgraph4j.state.Channel;
import org.bsc.langgraph4j.state.Channels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterviewState extends AgentState {
    public static final String QUESTION_KEY = "question";
    public static final String ANS_KEY = "ans";
    public static final String FEEDBACK_KEY = "feedback";
    public static final String INTERACTION_KEY = "interaction";
    public static final String CONTINUE = "__CONTINUE__";
    public static final String ANSWERED_KEY = "answered";
    public static final Map<String, Channel<?>> SCHEMA =
            Map.of(INTERACTION_KEY, Channels.appender(ArrayList::new),
                    QUESTION_KEY, Channels.base(() -> Strings.EMPTY),
                    ANS_KEY, Channels.base(() -> Strings.EMPTY),
                    FEEDBACK_KEY, Channels.base(() -> Strings.EMPTY),
                    ANSWERED_KEY,Channels.base(()->false));

    public InterviewState(Map<String, Object> initData) {
        super(initData);
    }

    public String question() {
        return this.<String>value(QUESTION_KEY).orElse(null);
    }

    public String answer() {
        return this.<String>value(ANS_KEY).orElse(null);
    }

    public Interview interview() {
        var interactions = this.<List<Interview.Interaction>>value(INTERACTION_KEY).orElse(List.of());
        return Interview.of(interactions);
    }

    public String feedback() {
        return this.<String>value(FEEDBACK_KEY).orElse(Strings.EMPTY);
    }
    public boolean answered(){
        return this.<Boolean>value(ANSWERED_KEY).orElse(false);
    }
}
