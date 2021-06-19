package net.hypixel.floppybot.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.hypixel.floppybot.command.Command;
import net.hypixel.floppybot.mapper.PlayerMapper;
import net.hypixel.floppybot.model.Player;
import net.hypixel.floppybot.service.MojangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@Slf4j
@Getter
@Configuration
@SuppressWarnings("SpringConfigurationProxyMethods")
public class CommandConfiguration {

    @Bean(autowireCandidate = false)
    @Command(prefix = "!lower")
    public Function<String, String> lower() {
        return String::toLowerCase;
    }

    @Bean(autowireCandidate = false)
    @Command(prefix = "!upper")
    public Function<String, String> upper() {
        return String::toUpperCase;
    }

    @Bean(autowireCandidate = false)
    @Command(prefix = "!reverse")
    public Function<String, String> reverse() {
        return s -> new StringBuilder(s).reverse().toString();
    }

    @Bean(autowireCandidate = false)
    @Command(prefix = "!ureverse")
    public Function<String, String> ureverse() {
        return upper().andThen(reverse());
    }

    @Bean
    @Command(prefix = "!embed", hasEmbed = true, inline = false)
    public Function<String, String> embed() {
        return String::toUpperCase;
    }

    @Bean
    @Command(prefix = "!getPlayer")
    public Function<String, String> getPlayer(PlayerMapper playerMapper, MojangService mojangService) {
        return s -> {
            log.info("Getting player: " + s);
            try {
                var player = playerMapper.sourceToDestination(mojangService.getPlayerByName(s));
                log.info("Got Player");
                return player.toString();
            } catch (ExecutionException | InterruptedException e) {
                log.info("Failed to get player: " + s);
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
            return "Could not find player: " + s;
        };
    }
}
