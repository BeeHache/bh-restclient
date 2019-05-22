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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.head;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;

/**
 *
 * @author ben
 */
public class RestClientTest {
    
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());

    private String httpBaseUrl;
    private String httpsBaseUrl;
    private RestClient restClient;
    private final String happyURI = "/happy";
    private final String sadURI = "/sad";
    
    public RestClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        restClient = new RestClient();
        httpBaseUrl = "http://localhost:" + wireMockRule.port();
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of doHead method, of class RestClient.
     */
    @Test
    public void testDoHead() {        
        givenThat(head(urlEqualTo(happyURI)).willReturn(aResponse().withStatus(204)));
        givenThat(head(urlEqualTo(sadURI)).willReturn(aResponse().withStatus(404)));
        
        assertThat(restClient.doHead(httpBaseUrl + happyURI).getStatusCode(), is(204));
        assertThat(restClient.doHead(httpBaseUrl + sadURI).getStatusCode(), is(404));
    }  
    
    /**
     * Test of doGet method, of class RestClient.
     */
    @Test
    public void testDoGet_String() {
        System.out.println("doGet");
        String url = "";
        
        Response expResult = null;
        Response result = restClient.doGet(url);
        assertThat(result, is(expResult));
    }

    /**
     * Test of doGet method, of class RestClient.
     */
    @Test
    public void testDoGet_String_Map() {
        System.out.println("doGet");
        String url = "";
        Map<String, String> headers = null;
        
        Response expResult = null;
        Response result = restClient.doGet(url, headers);
        assertThat(result, is(expResult));
    }

    /**
     * Test of doPut method, of class RestClient.
     */
    @Test
    public void testDoPut_3args() {
        System.out.println("doPut");
        String url = "";
        byte[] payload = null;
        String mimeType = "";
        
        Response expResult = null;
        Response result = restClient.doPut(url, payload, mimeType);
        assertThat(result, is(expResult));
    }

    /**
     * Test of doPut method, of class RestClient.
     */
    @Test
    public void testDoPut_4args() {
        System.out.println("doPut");
        String url = "";
        byte[] payload = null;
        String mimeType = "";
        Map<String, String> headers = null;
        
        Response expResult = null;
        Response result = restClient.doPut(url, payload, mimeType, headers);
        assertThat(result, is(expResult));
    }

    /**
     * Test of doDelete method, of class RestClient.
     */
    @Test
    public void testDoDelete_String() {
        System.out.println("doDelete");
        String url = "";
        
        Response expResult = null;
        Response result = restClient.doDelete(url);
        assertThat(result, is(expResult));
    }

    /**
     * Test of doDelete method, of class RestClient.
     */
    @Test
    public void testDoDelete_String_Map() {
        System.out.println("doDelete");
        String url = "";
        Map<String, String> headers = null;
        
        Response expResult = null;
        Response result = restClient.doDelete(url, headers);
        assertThat(result, is(expResult));
    }

    /**
     * Test of doPost method, of class RestClient.
     */
    @Test
    public void testDoPost_3args() {
        System.out.println("doPost");
        String url = "";
        byte[] payload = null;
        String mimeType = "";
        
        Response expResult = null;
        Response result = restClient.doPost(url, payload, mimeType);
       assertThat(result, is(expResult));
    }

    /**
     * Test of doPost method, of class RestClient.
     */
    @Test
    public void testDoPost_4args() {
        System.out.println("doPost");
        String url = "";
        byte[] payload = null;
        String mimeType = "";
        Map<String, String> headers = null;
        
        Response expResult = null;
        Response result = restClient.doPost(url, payload, mimeType, headers);
        assertThat(result, is(expResult));
    }    
}
