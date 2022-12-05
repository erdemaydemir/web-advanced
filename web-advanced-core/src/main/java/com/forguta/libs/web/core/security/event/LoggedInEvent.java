package com.forguta.libs.web.core.security.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class LoggedInEvent extends ApplicationEvent {
    public LoggedInEvent(Object source) {
        super(source);
    }

    public LoggedInEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
