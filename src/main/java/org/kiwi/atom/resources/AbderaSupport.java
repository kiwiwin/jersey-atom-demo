package org.kiwi.atom.resources;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class AbderaSupport implements MessageBodyWriter<Object> {

    private final static Abdera abdera = new Abdera();

    public static Abdera getAbdera() {
        return abdera;
    }

//    public long getSize(Object arg0) {
//        return -1;
//    }

//    public boolean isWriteable(Class<?> type) {
//        return (Feed.class.isAssignableFrom(type) || Entry.class.isAssignableFrom(type));
//    }

//    public void writeTo(Object feedOrEntry, MediaType mediaType,
//                        MultivaluedMap<String, Object> headers,
//                        OutputStream outputStream) throws IOException {
//        if (feedOrEntry instanceof Feed) {
//            Feed feed = (Feed) feedOrEntry;
//            Document<Feed> doc = feed.getDocument();
//            doc.writeTo(outputStream);
//        } else {
//            Entry entry = (Entry) feedOrEntry;
//            Document<Entry> doc = entry.getDocument();
//            doc.writeTo(outputStream);
//        }
//    }

    public boolean isReadable(Class<?> type) {
        return (Feed.class.isAssignableFrom(type) || Entry.class.isAssignableFrom(type));
    }

    public Object readFrom(Class<Object> feedOrEntry, MediaType mediaType,
                           MultivaluedMap<String, String> headers,
                           InputStream inputStream) throws IOException {
        Document<Element> doc = getAbdera().getParser().parse(inputStream);
        Element el = doc.getRoot();
        if (feedOrEntry.isAssignableFrom(el.getClass())) {
            return el;
        } else {
            throw new IOException("Unexpected payload, expected " + feedOrEntry.getName() +
                    ", received " + el.getClass().getName());
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return (Feed.class.isAssignableFrom(type) || Entry.class.isAssignableFrom(type));
    }

    @Override
    public long getSize(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object feedOrEntry, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream outputStream) throws IOException, WebApplicationException {
        if (feedOrEntry instanceof Feed) {
            Feed feed = (Feed) feedOrEntry;
            Document<Feed> doc = feed.getDocument();
            doc.writeTo(outputStream);
        } else {
            Entry entry = (Entry) feedOrEntry;
            Document<Entry> doc = entry.getDocument();
            doc.writeTo(outputStream);
        }
    }
}