package com.quinton.discord.plv.event.listener;

import discord4j.core.event.domain.Event;
import reactor.core.publisher.Flux;

/**
 * An interface for handling events of a specific type.
 *
 * @param <E> The type of events to be handled, must extend the {@link Event} interface.
 */
public interface EventListener<E extends Event> {

    /**
     * Callback method invoked when an event occurs.
     *
     * @param event The event to be handled.
     */
    Flux<E> on(E event);
}

