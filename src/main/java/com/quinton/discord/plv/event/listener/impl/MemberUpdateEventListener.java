package com.quinton.discord.plv.event.listener.impl;

import com.quinton.discord.plv.event.listener.EventListener;
import discord4j.core.event.domain.guild.MemberUpdateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import reactor.core.publisher.Flux;

import java.util.List;

public class MemberUpdateEventListener implements EventListener<MemberUpdateEvent> {

    @Override
    public Flux<MemberUpdateEvent> on(MemberUpdateEvent event) {
        System.out.println("Member update");
        Member member = event.getMember().block();
        List<Role> roles = member.getRoles().collectList().block();

        roles.forEach(role -> System.out.println(role.getName()));
        return Flux.empty();
    }
}
