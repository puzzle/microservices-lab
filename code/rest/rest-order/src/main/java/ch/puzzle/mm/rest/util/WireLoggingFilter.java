package ch.puzzle.mm.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Provider
public class WireLoggingFilter implements ClientRequestFilter, ClientResponseFilter, WriterInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WireLoggingFilter.class);

    private static final String ENTITY_STREAM_PROPERTY = "EntityLoggingFilter.entityStream";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final String HAS_MORE = "\n...truncated...";
    private static final String LOG_FORMAT = "%30s: %s%n";

    private final Boolean logBody = false;
    private final Integer maxBodySize = 4096;

    public WireLoggingFilter() {  }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        StringBuilder sb = new StringBuilder();
        MultivaluedMap<String, Object> headers = requestContext.getHeaders();

        sb.append("\n---------------------------- REQUEST ---------------------------\n");
        sb.append(String.format(LOG_FORMAT, requestContext.getMethod(), requestContext.getUri()));
        for (Map.Entry<String, List<Object>> stringListEntry : headers.entrySet()) {
            for (Object value : stringListEntry.getValue()) {
                sb.append(String.format(LOG_FORMAT, stringListEntry.getKey(), value.toString()));
            }
        }

        Map<String, Cookie> cookies = requestContext.getCookies();
        if (cookies != null) {
            for (Map.Entry<String, Cookie> entry : cookies.entrySet()) {
                Cookie cookie = entry.getValue();
                sb.append(String.format(LOG_FORMAT, cookie.getName(), cookie.getValue()));
            }
        }

        if(requestContext.getEntity() != null) {
            sb.append("\n").append(requestContext.getEntity().toString()).append("\n");
        }

        if (logBody && requestContext.hasEntity()) {
            final OutputStream stream = new LoggingStream(requestContext.getEntityStream(), sb);
            requestContext.setEntityStream(stream);
            requestContext.setProperty(ENTITY_STREAM_PROPERTY, stream);
        }

        logger.info(sb.toString());
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        StringBuilder sb = new StringBuilder();
        MultivaluedMap<String, String> headers = clientResponseContext.getHeaders();

        sb.append("\n--------------------------- RESPONSE ---------------------------\n");
        sb.append(String.format(LOG_FORMAT, "HTTP "+clientResponseContext.getStatus(), clientRequestContext.getUri()));
        for (Map.Entry<String, List<String>> stringListEntry : headers.entrySet()) {
            for (Object value : stringListEntry.getValue()) {
                sb.append(String.format(LOG_FORMAT, stringListEntry.getKey(), value));
            }
        }

        Map<String, NewCookie> cookies = clientResponseContext.getCookies();
        if (cookies != null) {
            for (Map.Entry<String, NewCookie> entry : cookies.entrySet()) {
                Cookie cookie = entry.getValue();
                sb.append(String.format(LOG_FORMAT, cookie.getName(), cookie.getValue()));
            }
        }

        if (logBody && clientResponseContext.hasEntity()) {
            clientResponseContext.setEntityStream(logInboundEntity(sb, clientResponseContext.getEntityStream(), DEFAULT_CHARSET));
        }

        logger.info(sb.toString());
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        final LoggingStream stream = (LoggingStream) context.getProperty(ENTITY_STREAM_PROPERTY);
        context.proceed();
        if (stream != null) {
            logger.info(stream.getStringBuilder(DEFAULT_CHARSET).toString());
        }
    }

    private InputStream logInboundEntity(final StringBuilder b, InputStream stream, final Charset charset) throws IOException {
        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }
        b.append("\n");
        stream.mark(maxBodySize + 1);
        final byte[] entity = new byte[maxBodySize + 1];
        final int entitySize = stream.read(entity);
        b.append(new String(entity, 0, Math.min(entitySize, maxBodySize), charset));
        if (entitySize > maxBodySize) {
            b.append(HAS_MORE);
        }
        b.append('\n');
        stream.reset();
        return stream;
    }

    private class LoggingStream extends FilterOutputStream {

        private StringBuilder sb = null;
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        LoggingStream(OutputStream out, StringBuilder sb) {
            super(out);
            this.sb = sb;
            sb.append("\n");
        }

        StringBuilder getStringBuilder(Charset charset) {
            // write entity to the builder
            final byte[] entity = baos.toByteArray();

            sb.append(new String(entity, 0, entity.length, charset));
            if (entity.length > maxBodySize) {
                sb.append(HAS_MORE);
            }
            sb.append('\n');

            return sb;
        }

        @Override
        public void write(final int i) throws IOException {
            if (baos.size() <= maxBodySize) {
                baos.write(i);
            }
            out.write(i);
        }
    }
}