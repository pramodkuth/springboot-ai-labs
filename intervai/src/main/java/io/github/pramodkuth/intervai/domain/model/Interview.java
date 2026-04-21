package io.github.pramodkuth.intervai.domain.model;

import java.io.Serializable;
import java.util.List;

public class Interview implements Serializable{
    private List<Interaction> interactions;

    private Interview(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    public static Interview of(List<Interaction> interactions) {
        return new Interview(interactions);
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void addInteraction(Interaction interaction) {
        interactions.add(interaction);
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    public static class Interaction implements Serializable {
        private String question;
        private String answer;

        public Interaction(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        @Override
        public String toString() {
            return String.join("\n","question:",question,"answer:",answer);
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }
}
