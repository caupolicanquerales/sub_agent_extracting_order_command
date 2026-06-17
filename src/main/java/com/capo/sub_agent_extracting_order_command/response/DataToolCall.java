package com.capo.sub_agent_extracting_order_command.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataToolCall(String name, @JsonAlias("argument") List<ProjectName> arguments) {
	
}
