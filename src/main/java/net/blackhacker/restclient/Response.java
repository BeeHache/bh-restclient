package net.blackhacker.restclient;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author bh@blackhacker.net
 */
public interface Response {

    public int getStatusCode();

    public byte[] getContent() throws IOException;
    
    public Map<String,String> getHeaders();
}
