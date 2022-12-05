package com.forguta.libs.web.core.security.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class LogoutEvent extends ApplicationEvent {
    public LogoutEvent(Object source) {
        super(source);
    }

    public LogoutEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
