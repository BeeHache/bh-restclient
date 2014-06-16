package net.blackhacker.restclient;

import org.apache.http.entity.ContentType;

/**
 *
 * @author ben
 */
public class MimeType {
    final static public String APPLICATION_ATOM_XML        = ContentType.APPLICATION_ATOM_XML.getMimeType();
    final static public String APPLICATION_FORM_URLENCODED = ContentType.APPLICATION_FORM_URLENCODED.getMimeType();
    final static public String APPLICATION_JSON            = ContentType.APPLICATION_JSON.getMimeType();
    final static public String APPLICATION_OCTET_STREAM    = ContentType.APPLICATION_OCTET_STREAM.getMimeType();
    final static public String APPLICATION_SVG_XML         = ContentType.APPLICATION_SVG_XML.getMimeType();
    final static public String APPLICATION_XHTML_XML       = ContentType.APPLICATION_XHTML_XML.getMimeType();
    final static public String APPLICATION_XML             = ContentType.APPLICATION_XML.getMimeType();
    final static public String DEFAULT_BINARY              = ContentType.DEFAULT_BINARY.getMimeType();
    final static public String DEFAULT_TEXT                = ContentType.DEFAULT_TEXT.getMimeType();
    final static public String MULTIPART_FORM_DATA         = ContentType.MULTIPART_FORM_DATA.getMimeType();
    final static public String TEXT_HTML                   = ContentType.TEXT_HTML.getMimeType();
    final static public String TEXT_PLAIN                  = ContentType.TEXT_PLAIN.getMimeType();
    final static public String TEXT_XML                    = ContentType.TEXT_XML.getMimeType();
    final static public String WILDCARD                    = ContentType.WILDCARD.getMimeType();
}
