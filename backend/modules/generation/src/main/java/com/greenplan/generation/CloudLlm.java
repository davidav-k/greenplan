package com.greenplan.generation;

import java.util.Map;

public interface CloudLlm {
    String generateStrictJson(String system, String user, Map<String, Object> jsonSchema);
}