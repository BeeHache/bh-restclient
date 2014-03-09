package net.blackhacker.restclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

/**
 *
 * @author ben
 */
public class RestClient {
    final private HttpClient httpClient;
    private int statusCode;
    private byte[] content;
    
    static final Logger LOG = Logger.getLogger(RestClient.class.getName());
    
    public RestClient(){
        httpClient = HttpClientBuilder.create().build();
    }
    
    public RestClient(HttpClient httpClient) { 
        this.httpClient = httpClient;
    }
    
    public byte[] getContent() {
        return content;
    }
    
    public int getStatusCode() {
        return statusCode;
    }

    public int doGet(String url) {
        return execute(new HttpGet(url));
    }    
    
    public int doGet(String url, Map<String,String> headers) {
        return execute(buildRequest(new HttpGet(url), headers,null,null));
    }
    
    public int doPut(String url, byte[] payload, String mimeType){
        return doPut(url, payload, mimeType, null);
    }
    
    public int doPut(String url, byte[] payload, String mimeType, Map<String,String> headers) {
        return execute(buildRequest(new HttpPut(url), headers,payload,mimeType));
    }
    
    public int doDelete(String url){
        return doDelete(url,null);
    }
    
    public int doDelete(String url, Map<String,String> headers) {
        return execute(buildRequest(new HttpDelete(url), headers, null, null));
    }
    
    public int doPost(String url, byte[] payload, String mimeType){
        return doPost(url,payload,mimeType,null);
    }
    
    public int doPost(String url, byte[] payload, String mimeType, Map<String,String> headers) {
        return execute(buildRequest(new HttpPost(url), headers, payload, mimeType));
    }
    
    static private HttpRequestBase buildRequest(HttpRequestBase request, Map<String,String> headers,
            byte[] entity, String mimeType) {
        if (headers!=null) {
            Header[] headerArray = new Header[headers.size()];
            for(Map.Entry<String,String> entry : headers.entrySet()) {
                Header header = new BasicHeader(
                    entry.getKey(),
                    entry.getValue());
            }

            request.setHeaders(headerArray);
        }
        
        if ((entity!=null) && (request instanceof HttpEntityEnclosingRequestBase)) {
            HttpEntityEnclosingRequestBase heerb = (HttpEntityEnclosingRequestBase)request;
            ByteArrayEntity bae = new ByteArrayEntity(entity);
            bae.setContentType(mimeType);
            heerb.setEntity(bae);
        }
        
        return request;
    }

    private int execute(HttpRequestBase request) {
        int retval = 0;
        InputStream is=null;
        content = null;
        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = httpClient.execute(request).getEntity();

            if (entity != null) {
                is = entity.getContent();
                content = new byte[ (int)entity.getContentLength()];
                is.read(content);
            }
            
            retval = response.getStatusLine().getStatusCode();
            
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            if(is!=null) {
              try { is.close(); } catch (IOException ex) { }
            }
        }
        return retval;
    }
}
