package ch.puzzle.mm.kafka.order.util;

import io.opentracing.propagation.TextMap;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HeadersMapInjectAdapter implements TextMap {

    private final Map<String, String> map = new HashMap<>();

    public HeadersMapInjectAdapter() {

    }

    public HeadersMapInjectAdapter(Headers headers) {
        for (Header header : headers) {
            byte[] headerValue = header.value();
            map.put(header.key(),
                    headerValue == null ? null : new String(headerValue, StandardCharsets.UTF_8));
        }
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        throw new UnsupportedOperationException(
                "HeadersMapExtractAdapter should only be used with Tracer.extract()");
    }

    @Override
    public void put(String key, String value) {
        map.put(key, value);
    }

    public RecordHeaders getRecordHeaders() {
        List<RecordHeader> recordHeaders = map.entrySet().stream().map(stringStringEntry ->
                new RecordHeader(stringStringEntry.getKey(), stringStringEntry.getValue().getBytes())).collect(Collectors.toList());
        RecordHeaders headers = new RecordHeaders();
        recordHeaders.forEach(headers::add);
        return headers;
    }
}