package org.backendapi.dto;

import com.fasterxml.jackson.databind.JsonNode;

public record TreeResponse(Long id, String numbers, JsonNode tree) { }
