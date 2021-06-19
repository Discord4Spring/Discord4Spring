package com.discord4spring.discord4spring.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.discord4spring.discord4spring.command.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Getter
@Configuration
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

}
