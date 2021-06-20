/*
 * MIT License
 *
 * Copyright (c) 2021 Owen Hayes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.discord4spring.discord4spring.embed;

import discord4j.core.spec.EmbedCreateSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmbedBuilderTest {

    @Mock
    private Consumer<EmbedCreateSpec> embedCreateSpecConsumer;
    @InjectMocks
    private EmbedBuilder underTest;
    @Captor
    private ArgumentCaptor<Consumer<EmbedCreateSpec>> captor;


    @Test
    void testAddFieldCallsEmbedCreateSpecConsumerAndThen() {
        underTest.addField(
                "TEST",
                "TEST",
                true
        );

        verify(embedCreateSpecConsumer).andThen(ArgumentMatchers.<Consumer<EmbedCreateSpec>>any());
    }

    @Test
    void testAddFieldReturnsEmbedBuilder() {
        var out = underTest.addField(
                "TEST",
                "TEST",
                true
        );

        assertThat(out)
                .as("out should not be null.")
                .isNotNull()
                .as("out should be an instance of EmbedBuilder")
                .isInstanceOf(EmbedBuilder.class);
    }

    @Test
    void testBuild() {
        Consumer<EmbedCreateSpec> out = underTest.build();

        assertThat(out)
                .as("out should not be null.")
                .isNotNull()
                .as("out should be an instance of Consumer.")
                .isInstanceOf(Consumer.class)
                .as("out should be equal to underTest.getEmbedCreateSpecConsumer()")
                .isEqualTo(underTest.getEmbedCreateSpecConsumer());
    }
}