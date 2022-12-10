package com.forguta.libs.web.common.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class ProfileEditedEvent extends ApplicationEvent {
    public ProfileEditedEvent(Object source) {
        super(source);
    }

    public ProfileEditedEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
