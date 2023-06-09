package redis.protocol;

/**
 * {@see <a href="https://redis.io/docs/reference/protocol-spec"> protocl spec </a>}
 */
public enum RESP {
    SIMPLE_STRING("+"),
    ERRORS("-"),
    INTEGERS(":"),
    BULK_STRING("$"),
    ARRAYS("*");
    private static final String TERMINATION = "\r\n";
    private final String prefix;

    RESP(String prefix) {
        this.prefix = prefix;
    }

    public String prefix() {
        return prefix;
    }

    public String message(String content) {
        return prefix + content + TERMINATION;
    }

}

