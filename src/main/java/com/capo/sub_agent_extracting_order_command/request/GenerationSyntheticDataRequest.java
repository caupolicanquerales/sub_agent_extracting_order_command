package com.capo.sub_agent_extracting_order_command.request;

public class GenerationSyntheticDataRequest {
	
	private String prompt;
	private String conversationId;
	private ToolIformation toolRequest;

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public ToolIformation getToolRequest() {
		return toolRequest;
	}

	public void setToolRequest(ToolIformation toolRequest) {
		this.toolRequest = toolRequest;
	}

}
