package net.hypixel.floppybot.model;

import lombok.Data;

@Data
public class DiscordTag {
    private String username;
    private String discriminator;
    private String tag;

    public DiscordTag(String tag) {
        if (tag == null) {
            this.tag = "";
            this.username = "";
            this.discriminator = "";
        } else {
            String[] parts = tag.split("#");
            this.tag = tag;
            this.username = parts[0];
            this.discriminator = parts[1];
        }
    }

    public DiscordTag(String username, String discriminator) {
        this.username = username;
        this.discriminator = discriminator;
        this.tag = username + "#" + discriminator;
    }

    public String toString() {
        return tag;
    }
}
