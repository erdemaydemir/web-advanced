package com.forguta.libs.web.core.security.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class SingupEvent extends ApplicationEvent {
    public SingupEvent(Object source) {
        super(source);
    }

    public SingupEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
