package com.lecsures.section2.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j // use for log
public class AuthenticationEvent {

    @EventListener // ทำให้ method คอยดูเหตุุการที่เกิดขึ้น โดยในกรณีนี้จะคอยดูเหตุการที่เกิดขึ้นกับ AuthenticationSuccessEvent
    public void onSuccess(AuthenticationSuccessEvent successEvent) {

        // log info
        log.info("Login successful for the user: {}", successEvent.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
        log.error("Login failed for the user {} due to {}", failureEvent.getAuthentication().getName(), failureEvent.getException().getMessage());
    }

}
