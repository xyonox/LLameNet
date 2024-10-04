package de.einkampflama.LLamaNet.logging;

import org.jetbrains.annotations.Nullable;

/**
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
public interface Logger {
    /**
     * Log info stuff
     *
     * @param content Log output. can´t be null {func(null) ≠ runtime}
     */
    public void info(@Nullable String content);
    /**
     * Log error stuff
     *
     * @param content Log output. can´t be null {func(null) ≠ runtime}
     */
    public void error(@Nullable String content);
    /**
     * Log exception stuff
     *
     * @param content Log output. can´t be null {func(null) ≠ runtime}
     */
    public void exception(@Nullable String content);
    public void notAllowed(@Nullable String content);
}
