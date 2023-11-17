package com.quinton.discord.plv.event;

import com.quinton.discord.plv.event.listener.EventListener;
import com.quinton.discord.plv.event.listener.impl.*;
import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.guild.MemberUpdateEvent;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * EventDispatcher class responsible for dispatching events to registered listeners.
 */
public class EventDispatcher {

    /**
     * Dispatches an event to its registered listener.
     *
     * @param event The event to dispatch.
     * @param <E>   The type of event.
     */
    public <E extends Event> Flux<E> dispatch(E event) {
        Logger.getGlobal().info("Dispatching event " + event.getClass());
        EventListener<E> eventListener = getEventListener(event);

        if (eventListener != null) {
            Logger.getGlobal().info("Dispatching event " + event.getClass() + " to " + eventListener);
            return on(eventListener, event);
        } else {
            Logger.getGlobal().warning("No listener found for event " + event.getClass());
        }
        return Flux.empty();
    }

    /**
     * Initializes default event listeners.
     */
    private void initializeEventListeners() {
        addListener(ReadyEvent.class, new ReadyEventListener());
        addListener(GuildCreateEvent.class,new GuildCreateEventListener());
//        addListener(ChatInputInteractionEvent.class,new ChatInputInteractionEventListener());
        addListener(MemberUpdateEvent.class,new MemberUpdateEventListener());
        addListener(ApplicationCommandInteractionEvent.class,new ApplicationCommandInteractionEventListener());
        // Add other default listeners if needed
    }

    /**
     * Registers an event listener for a specific event class.
     *
     * @param eventClass    The class of the event to listen for.
     * @param eventListener The listener to handle the specified event.
     * @param <E>           The type of event.
     */
    private <E extends Event> void addListener(Class<E> eventClass, EventListener<E> eventListener) {
        eventListenerMap.put(eventClass, eventListener);
    }

    /**
     * Gets the event listener for the specified event class.
     *
     * @param event The event to get the listener for.
     * @param <E>   The type of event.
     * @return The event listener for the specified event class.
     */
    @SuppressWarnings("unchecked")
    private <E extends Event> EventListener<E> getEventListener(E event) {
        return (EventListener<E>) eventListenerMap.get(event.getClass());
    }

    /**
     * Handles the event by invoking the corresponding listener's onEvent method.
     *
     * @param eventListener The listener to handle the event.
     * @param event         The event to handle.
     * @param <E>           The type of event.
     */
    private <E extends Event> Flux<E> on(EventListener<E> eventListener, E event) {
        try {
           return eventListener.on(event);
        } catch (Exception e) {
            Logger.getGlobal().severe("Error handling event " + event.getClass() + ": " + e.getMessage());
            // Handle the error or throw a specific exception based on your needs.
        }
        return Flux.empty();
    }

    /**
     * Constructor for EventDispatcher. Initializes default event listeners.
     */
    public EventDispatcher() {
        initializeEventListeners();
    }

    // Map to store event listeners based on event class
    private final Map<Class<? extends Event>, EventListener<? extends Event>> eventListenerMap = new HashMap<>();
}

