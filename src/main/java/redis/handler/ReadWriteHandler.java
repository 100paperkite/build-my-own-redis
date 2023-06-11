package redis.handler;

import redis.protocol.RESP;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ReadWriteHandler implements Handler {
    private final ByteBuffer buffer = ByteBuffer.allocate(256);
    private final SelectionKey selectionKey;
    private final SocketChannel socketChannel;

    public ReadWriteHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, SelectionKey.OP_READ, this);
    }

    @Override
    public void handle() {
        try {
            if (selectionKey.isReadable()) {
                read();
            } else if (selectionKey.isWritable()) {
                write();
            }
        } catch (IOException e) {
            // TODO: exception handling
            e.printStackTrace();
        }
    }

    private void read() throws IOException {
        int amount = socketChannel.read(buffer);
        if (amount > 0) {
            buffer.flip();
            String message = new String(buffer.array(), 0, amount);
            // TODO: parse message
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void write() throws IOException {
        // TODO: message handling
        // TEMP: always send "PONG"
        var bytes = RESP.SIMPLE_STRING.message("PONG").getBytes();
        socketChannel.write(ByteBuffer.wrap(bytes));
        buffer.clear();

        selectionKey.interestOps(SelectionKey.OP_READ);
    }
}
