package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.Application;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ApiTest {
    private final static String LOCALHOST = "127.0.0.1";
    private final static int PORT = 6379;
    private static JedisPool pool;

    @BeforeAll
    private static void setUp() {
        String[] args = {};
        new Thread(() -> Application.main(args)).start(); // run redis server background
        pool = new JedisPool(LOCALHOST, PORT);

    }

    @Test
    public void ping() throws Exception {
        try (var jedis = pool.getResource()) {
            String result = jedis.ping();
            assertEquals(result, "PONG");
        }
    }
}
