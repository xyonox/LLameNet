package de.einkampflama.LLamaNet.api.webserver.answer;

/**
 * @author Xyonox
 * @version 1.0.0
 * @since alpha-1.0.0
 */
public class Cookie {
    private final String name;
    private final String value;
    private final String path;
    private final int maxAge;

    public Cookie(String name, String value) {
        this(name, value, "/", 86400);
    }

    public Cookie(String name, String value, String path, int maxAge) {
        this.name = name;
        this.value = value;
        this.path = path;
        this.maxAge = maxAge;
    }

    public String toSetCookieHeader() {
        StringBuilder cookieHeader = new StringBuilder();
        cookieHeader.append(name).append("=").append(value);
        if (maxAge >= 0) {
            cookieHeader.append("; Max-Age=").append(maxAge);
        }
        cookieHeader.append("; Path=").append(path);
        cookieHeader.append("; HttpOnly");
        cookieHeader.append("; Secure");

        return cookieHeader.toString();
    }
}