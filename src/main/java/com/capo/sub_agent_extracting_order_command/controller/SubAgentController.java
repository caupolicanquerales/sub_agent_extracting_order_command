package com.capo.sub_agent_extracting_order_command.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.capo.sub_agent_extracting_order_command.request.GenerationSyntheticDataRequest;
import com.capo.sub_agent_extracting_order_command.service.ExecutingExtractingOrderCommandService;
import com.capo.sub_agent_extracting_order_command.utils.SseStreamUtil;


@RestController
@RequestMapping("sub-agent-extracting-order-command")
public class SubAgentController {
	
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private final ExecutingExtractingOrderCommandService executingActionLayout;
	
	@Value(value="${event.name.chat}")
	private String eventName;
	
	public SubAgentController(ExecutingExtractingOrderCommandService executingActionLayout) {
		this.executingActionLayout= executingActionLayout;
	}
	
	@PostMapping(path = "/chat-stream-extracting", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamBasicTemplateGeneration(@RequestBody GenerationSyntheticDataRequest request) {
		return SseStreamUtil.stream(executor, eventName, "Extracting order command is starting for prompt",
                () -> executingActionLayout.generateExtractingOrderCommandAsync(
                        request.getPrompt()),
                result -> {
                    return result;
                });
	}
}
