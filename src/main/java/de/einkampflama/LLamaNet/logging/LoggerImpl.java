package de.einkampflama.LLamaNet.logging;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerImpl implements Logger{


    @Override
    public void info(@Nullable String content) {
        log(LogLevel.INFO, content);
    }

    @Override
    public void error(@Nullable String content) {
        log(LogLevel.ERROR, content);
    }

    @Override
    public void exception(@Nullable String content) {
        log(LogLevel.EXCEPTIONS, content);
    }

    @Override
    public void notAllowed(@Nullable String content) {
        log(LogLevel.NOTALLOWED, content);
    }

    public synchronized void log(@NotNull LogLevel level, @Nullable String content) {
        System.out.println(
                level.getPrefix() +
                        OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " -> " + content + "\u001b[0m"
        );
    }
}
