package com.capo.sub_agent_extracting_order_command.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capo.sub_agent_extracting_order_command.response.DataMessage;
import com.capo.sub_agent_extracting_order_command.response.DataToolCall;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ExecutingExtractingOrderCommandService {
	
	private final ChatClient chatClient;
	private final String systemPrompt;
	private final ObjectMapper objectMapper;
	private final static String SHOW_VS_CODE="recursive";
	
	public ExecutingExtractingOrderCommandService(@Qualifier("chatClientGeneral") ChatClient chatClient,
			@Qualifier("systemPrompt") String systemPrompt,
			ObjectMapper objectMapper) {
		this.chatClient = chatClient;
		this.systemPrompt = systemPrompt;
		this.objectMapper = objectMapper;
	}
	
	public CompletableFuture<String> generateExtractingOrderCommandAsync(String prompt) {
	
		return CompletableFuture.supplyAsync(() -> {
			String content = this.chatClient.prompt()
					.messages(new SystemMessage(systemPrompt))
					.user(prompt)
					.call()
					.content();

			DataMessage dataMessage = new DataMessage();
			dataMessage.setType(SHOW_VS_CODE);
			try {
				String json = content.trim();
				if (json.startsWith("```")) {
					json = json.replaceAll("```[a-zA-Z]*\\n?", "").replace("```", "").trim();
				}
				DataToolCall toolCall = objectMapper.readValue(json, DataToolCall.class);
				dataMessage.setToolCall(toolCall);
			} catch (Exception e) {
				dataMessage.setMessage(content);
			}
			try {
				return objectMapper.writeValueAsString(dataMessage);
			} catch (Exception e) {
				return content;
			}
		});
	} 
}
