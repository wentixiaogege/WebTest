package com.itu.dataserveraction;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;

//@Provider
//@Consumes("application/x-protobuf")
//public class ProtobufMessageBodyReader implements MessageBodyReader<Object> {
//	
//	@Override
//	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations,
//			MediaType mediaType) {
//		return mediaType.toString().equals("application/x-protobuf");
//	}
//
//	@Override
//	public Object readFrom(Class<Object> type, Type genericType,
//			Annotation[] annotations, MediaType mediaType,
//			MultivaluedMap<String, String> httpHeaders, InputStream inputStream)
//			throws IOException, WebApplicationException {
//    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    	byte[] buffer = new byte[4096];
//    	int read;
//    	long total = 0L;
//    	do {
//    		read = inputStream.read(buffer, 0, buffer.length);
//    		if (read > 0) {
//    			baos.write(buffer, 0, read);
//    			total += read;
//    		}
//    	} while (read > 0);
// 
//		return CloudCommand.parseFrom(baos.toByteArray());
//	}
//}

@Provider
@Consumes("application/x-protobuf")
public class ProtobufMessageBodyReader implements MessageBodyReader<Message> {
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Message.class.isAssignableFrom(type);
    }

    public Message readFrom(Class<Message> type, Type genericType, Annotation[] annotations,
                MediaType mediaType, MultivaluedMap<String, String> httpHeaders, 
                InputStream entityStream) throws IOException, WebApplicationException {
        try {
            Method newBuilder = type.getMethod("newBuilder");
            GeneratedMessage.Builder builder = (GeneratedMessage.Builder) newBuilder.invoke(type);
            return builder.mergeFrom(entityStream).build();
        } catch (Exception e) {
            throw new WebApplicationException(e);
        }
    }
}

