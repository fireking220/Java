package server;

import exceptions.InValidPortException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ServerTest {

    @Test
    void main() {
    }

    @Test
    void runServer() {
    }

    @Test
    void serverChecks() throws IOException {
        Server serverRef;
        serverRef = new Server();
        assertThrows(NumberFormatException.class, ()->serverRef.serverChecks("foo"));
        assertThrows(NumberFormatException.class, ()->serverRef.serverChecks("3.14"));
        assertThrows(InValidPortException.class, ()->serverRef.serverChecks("-1"));
        assertThrows(InValidPortException.class, ()->serverRef.serverChecks("65536"));

    }
}