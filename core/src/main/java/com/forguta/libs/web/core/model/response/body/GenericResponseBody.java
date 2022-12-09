package com.forguta.libs.web.core.model.response.body;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class GenericResponseBody<T> {
    @Builder.Default
    private boolean success = true;
    private T content;
}
