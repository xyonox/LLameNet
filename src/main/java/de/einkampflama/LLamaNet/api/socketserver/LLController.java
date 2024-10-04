package de.einkampflama.LLamaNet.api.socketserver;

public interface LLController {
    public void start(String time);
    public void stop(String time);
    public void restart(String time);
}
