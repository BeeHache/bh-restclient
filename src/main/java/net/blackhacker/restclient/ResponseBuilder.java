/*
 * The MIT License
 *
 * Copyright 2019 ben.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.blackhacker.restclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ben
 */
public class ResponseBuilder {
    
    private int statusCode=-1;
    private InputStream inputStream=null;
    private long contentLength=-1;
    private Map<String,String> headers;
    
    public ResponseBuilder(){
        
    }
    
    public ResponseBuilder stausCode(int statusCode){
        this.statusCode = statusCode;
        return this;
    }
    
    /**
     * 
     * @param content
     * @return 
     */
    public ResponseBuilder content(InputStream content){
        inputStream = content;
        return this;
    }
    
    /**
     * 
     * @param contentLength
     * @return 
     */
    public ResponseBuilder contentLength(long contentLength){
        this.contentLength = contentLength;
        return this;
    }
    
    /**
     * 
     * @param headers
     * @return 
     */
    public ResponseBuilder headers(Map<String,String> headers){
        this.headers = headers;
        return this;
    }
    
    /**
     * 
     * @return 
     */
    public Response build(){
        return new Response() {
            final int statusCode = ResponseBuilder.this.statusCode;
            final InputStream inputStream = ResponseBuilder.this.inputStream;
            final long contentLength = ResponseBuilder.this.contentLength;
            
            @Override
            public int getStatusCode() {
                return statusCode;
            }

            @Override
            public byte[] getContent() throws IOException {
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 *4);
                byte[] buffer = new byte[1024 * 4];        
                int r;

                if (inputStream!=null) {
                    if (contentLength < 0) {                    
                        for(r = inputStream.read(buffer); r > 0; r = inputStream.read(buffer)) {
                            baos.write(buffer, 0, r);
                        }
                    } else {
                        long bytesToRead = contentLength;

                        while(bytesToRead >0) {
                            r=inputStream.read(buffer);
                            baos.write(buffer, 0, r);
                            bytesToRead-=r;
                        }
                    }
                }

                return baos.toByteArray();                

            }

            @Override
            public Map<String, String> getHeaders() {
                return new HashMap<>(headers);
            }
        };
    }
}
