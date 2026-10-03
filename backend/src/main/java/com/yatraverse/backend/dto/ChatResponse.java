package com.yatraverse.backend.dto;

import java.util.List;

public record ChatResponse(
        String answer,
        boolean grounded,
        List<Source> sources
) {
    public record Source(
            String destination,
            String city,
            String section,
            double score
    ) {}
}