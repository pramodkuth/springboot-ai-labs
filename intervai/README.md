# <center> IntervAI</center>
This is a Spring Boot application demonstrating a stateful multi-agent workflow for an automated interview process. It uses LangGraph4j to orchestrate two specialized agents—an Interviewer and a Candidate—running on local LLMs (Llama and Qwen) via Ollama.

----
### 🚀 Key Features
- Multi-Agent Orchestration: Built with LangGraph4j to manage cyclical workflows where agents interact and share state.
- Specialized Agent Roles:
  - Interviewer (llama): Orchestrates the session, asks predefined category-specific questions, and tracks progress.
  - Candidate (qwen): Provides context-aware answers to the interviewer's question.
- Stateful Guardrails: Implements a check to ensure all question categories are covered and the interview is evaluated by the Interviewer agent.
-  Local LLM Integration: Powered by LangChain4j and Ollama for private, local execution.
- Automated Evaluation: Once the interview ends, the Interviewer generates a comprehensive performance feedback.


----
### 🛠 Tech Stack
- Java 21+
- Spring Boot: Framework for the core application.
- LangGraph4j: For building the stateful directed graph of agent nodes.
- LangChain4j: The bridge between Java and Large Language Models.
- Ollama: Local model hosting for Llama and Qwen models.

----
### ⚙️ Setup & Configuration
### Prerequisites
- Ollama installed and running
- pull and run the required models (You can use any two models of your choice)

### Configuration
```java
 @Bean("qwenModel")
    public StreamingChatModel qwenModel() {
        return OllamaStreamingChatModel.builder().baseUrl("http://localhost:11434").modelName("qwen2.5-coder:7b").build();
    }

    @Bean("llamaModel")
    public StreamingChatModel llamaModel() {
        return OllamaStreamingChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.1").build();
    }
```

----
### Running the application
- Run the main spring boot application : [IntervaiApplication](src/main/java/io/github/pramodkuth/intervai/IntervaiApplication.java)
- Invoke this endpoint to start the interview : [/interview](http://localhost:8080/intervai/interview) 
