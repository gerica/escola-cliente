package com.escola.client.controller.help;

public record SortInput(
        String property,
        SortOrder direction) {
}