package net.ion.nradon;

public interface WebSocket extends Endpoint<WebSocket> {
    WebSocket reconnectEvery(long millis);
}