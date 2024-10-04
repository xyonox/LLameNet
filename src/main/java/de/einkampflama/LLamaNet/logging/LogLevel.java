package de.einkampflama.LLamaNet.logging;

/**
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
public enum LogLevel {
    /**
     * Info level - Color output: Yellow
     */
    INFO("\u001b[33;1m"),
    /**
     * Error level - Color output: Red
     */
    ERROR("\u001b[31;1m"),
    /**
     * Exception level - Color output: Orange
     */
    EXCEPTIONS("\u001b[38;5;214m"),
    /**
     * NotAllowed level - Color output: Red
     */
    NOTALLOWED("\u001b[35;1mDEBUG\u001b[0m");

    private final String prefix;

    /**
     * @param prefix Color of the level
     */
    LogLevel(String prefix){
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
