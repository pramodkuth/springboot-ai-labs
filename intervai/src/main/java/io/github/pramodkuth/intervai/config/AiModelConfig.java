package io.github.pramodkuth.intervai.config;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiModelConfig {
    @Bean("qwenModel")
    public StreamingChatModel qwenModel() {
        return OllamaStreamingChatModel.builder().baseUrl("http://localhost:11434").modelName("qwen2.5-coder:7b").build();
    }

    @Bean("llamaModel")
    public StreamingChatModel llamaModel() {
        return OllamaStreamingChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.1").build();
    }
}
