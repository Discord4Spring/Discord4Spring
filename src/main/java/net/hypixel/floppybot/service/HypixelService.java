package net.hypixel.floppybot.service;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import net.hypixel.api.HypixelAPI;
import net.hypixel.floppybot.model.DiscordTag;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class HypixelService {

    private final HypixelAPI hypixelAPI;

    public DiscordTag getDiscordTag(String uuid) throws ExecutionException, InterruptedException {
        JsonObject player = hypixelAPI.getPlayerByUuid(uuid).get().getPlayer();
        if (player != null && player.has("socialMedia")) {
            var socialMedia = player.get("socialMedia").getAsJsonObject();
            if (socialMedia.has("links")) {
                var links = socialMedia.get("links").getAsJsonObject();
                if (links.has("DISCORD")) {
                    return new DiscordTag(links.get("DISCORD").getAsString());
                }
            }
        }
        return new DiscordTag(null);
    }
}
