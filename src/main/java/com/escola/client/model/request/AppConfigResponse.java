package com.escola.client.model.request;

import java.time.OffsetDateTime;

public record AppConfigResponse(
    String name,
    String description,
    String version,
    OffsetDateTime time
) {
}
