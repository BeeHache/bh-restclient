package net.blackhacker.restclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

/**
 * The Simplest REST Client anyone could think of.
 * 
 * @author bh@blackhacker.net
 */
public class RestClient {
    final private HttpClient httpClient;
    static final Logger LOG = Logger.getLogger(RestClient.class.getName());

    public RestClient() {
        httpClient = HttpClientBuilder.create().build();
    }

    public RestClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
    
    /**
     * Performs HEAD request to the given url.
     * @param url
     * @return 
     * @see Response
     */
    public Response doHead(String url){
        return execute(new HttpHead(url));
    }
    
    /**
     * 
     * @param url
     * @param headers
     * @return 
     */
    public Response doHead(String url, Map<String,String>headers) {
        return execute(buildRequest(new HttpHead(url), headers, null, null));
    }

    /**
     * Performs a GET request with given url. Content from successful request
     * is accessed from the getContent method.
     * @param url
     * @return status code from the performed request
     */
    public Response doGet(String url) {
        return execute(new HttpGet(url));
    }

    /**
     * Performs a GET request with given url. Content from successful request
     * @param url
     * @param headers
     * @return status code from the performed request
     */
    public Response doGet(String url, Map<String, String> headers) {
        return execute(buildRequest(new HttpGet(url), headers, null, null));
    }

    /**
     * Performs a PUT request with given url.
     * @param url
     * @param payload
     * @param mimeType
     * @return status code from the performed request
     */
    public Response doPut(String url, byte[] payload, String mimeType) {
        return doPut(url, payload, mimeType, null);
    }

    /**
     * Performs a PUT request with given url.
     * @param url
     * @param payload
     * @param mimeType
     * @param headers
     * @return status code from the performed request
     */
    public Response doPut(String url, byte[] payload, String mimeType, Map<String, String> headers) {
        return execute(buildRequest(new HttpPut(url), headers, payload, mimeType));
    }

    /**
     * Performs a DELETE request with given url.
     * @param url
     * @return status code from the performed request
     */
    public Response doDelete(String url) {
        return doDelete(url, null);
    }

    /**
     * 
     * @param url
     * @param headers
     * @return status code from the performed request
     */
    public Response doDelete(String url, Map<String, String> headers) {
        return execute(buildRequest(new HttpDelete(url), headers, null, null));
    }

    /**
     * 
     * @param url
     * @param payload
     * @param mimeType
     * @return status code from the performed request
     */
    public Response doPost(String url, byte[] payload, String mimeType) {
        return doPost(url, payload, mimeType, null);
    }

    /**
     * 
     * @param url
     * @param payload
     * @param mimeType
     * @param headers
     * @return status code from the performed request
     */
    public Response doPost(String url, byte[] payload, String mimeType, Map<String, String> headers) {
        return execute(buildRequest(new HttpPost(url), headers, payload, mimeType));
    }

    /**
     * Adds Headers and Entity to HttpRequest object
     * @param request
     * @param headers
     * @param entity
     * @param mimeType
     * @return status code from the performed request
     */
    static private HttpRequestBase buildRequest(HttpRequestBase request, Map<String, String> headers,
            byte[] entity, String mimeType) {
        if (headers != null) {
            Header[] headerArray = new Header[headers.size()];
            int i=0;
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headerArray[i++] = new BasicHeader(
                    entry.getKey(),
                    entry.getValue() );
            }

            request.setHeaders(headerArray);
        }

        if ((request instanceof HttpEntityEnclosingRequestBase) && (entity != null)) {
            HttpEntityEnclosingRequestBase heerb = (HttpEntityEnclosingRequestBase) request;
            ByteArrayEntity bae = new ByteArrayEntity(entity);
            bae.setContentType(mimeType);
            heerb.setEntity(bae);
        }

        return request;
    }

    /**
     * 
     * @param request
     * @return Status code of of request
     * @see HttpRequestBase
     */
     private Response execute(HttpRequestBase request) {
        
        try {
            HttpResponse response = httpClient.execute(request);
            
            HashMap<String,String> headers = new HashMap<>();
            for(Header header : response.getAllHeaders()) {
                headers.put(header.getName(), header.getValue());
            }        
                    
            return new ResponseBuilder()
                    .content(response.getEntity() == null? null: response.getEntity().getContent())
                    .stausCode(response.getStatusLine().getStatusCode())
                    .contentLength(response.getEntity() ==null ? -1 : response.getEntity().getContentLength())
                    .headers(headers)
                    .build();

        } catch (IOException ex) { 
            LOG.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
