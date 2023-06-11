package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPool;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static redis.Application.main;


public class ApiTest {
    private final static String LOCALHOST = "127.0.0.1";
    private final static int PORT = 6379;
    private static JedisPool pool;

    @BeforeAll
    private static void setUp() {
        String[] args = {};
        runServerBackground(args); // run redis server background
        pool = new JedisPool(LOCALHOST, PORT);

    }

    private static void runServerBackground(String[] args) {
        new Thread(() -> {
            try {
                main(args);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Test
    public void ping() throws Exception {
        try (var jedis = pool.getResource()) {
            String result1 = jedis.ping();
            String result2 = jedis.ping();

            assertEquals(result1, "PONG");
            assertEquals(result2, "PONG");
        }
    }
}
