package net.blackhacker.restclient;

/**
 *
 * @author bh@blackhacker.net
 */
public class Response {
    final private int statusCode;
    final private byte[] content;

    public Response(int statusCode, byte[] content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public byte[] getContent() {
        return content;
    }
}
