package redis;

import redis.core.RedisServer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        new RedisServer(6379).run();
    }
}
