package net.hypixel.floppybot.mapper;

import net.hypixel.floppybot.dto.PlayerNameDTO;
import net.hypixel.floppybot.dto.PlayerNamesListDTO;
import net.hypixel.floppybot.model.Player;
import net.hypixel.floppybot.service.HypixelService;
import net.hypixel.floppybot.service.MojangService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

@Mapper(componentModel = "spring", uses = {MojangService.class, HypixelService.class})
public abstract class PlayerMapper {

    @Autowired
    protected MojangService mojangService;

    @Autowired
    protected HypixelService hypixelService;

    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "discordTag", expression = "java(hypixelService.getDiscordTag(source.getId()))")
    @Mapping(target = "names", expression = "java(mojangService.getPlayerByUUID(source.getId()))")
    public abstract Player sourceToDestination(PlayerNameDTO source) throws ExecutionException, InterruptedException;

    @Mapping(target = "names", source = "source.names")
    @Mapping(target = "name", expression = "java(source.getCurrentName())")
    @Mapping(target = "id", expression = "java(mojangService.getPlayerByName(player.getName()).getId())")
    @Mapping(target = "discordTag", expression = "java(hypixelService.getDiscordTag(player.getId()))")
    public abstract Player sourceToDestination(PlayerNamesListDTO source) throws ExecutionException, InterruptedException;
}
