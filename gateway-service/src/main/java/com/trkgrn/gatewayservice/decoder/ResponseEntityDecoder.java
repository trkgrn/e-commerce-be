package com.trkgrn.gatewayservice.decoder;

import feign.Response;
import feign.codec.Decoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ResponseEntityDecoder implements Decoder {
    private final Decoder delegate;

    public ResponseEntityDecoder(Decoder delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (type instanceof ParameterizedType parameterizedType &&
                ((ParameterizedType) type).getRawType().equals(ResponseEntity.class)) {
            Type actualType = parameterizedType.getActualTypeArguments()[0];
            Object decodedObject = delegate.decode(response, actualType);
            HttpHeaders httpHeaders = new HttpHeaders();
            response.headers().forEach((key, value) -> httpHeaders.put(key, new ArrayList<>(value)));

            return ResponseEntity.status(response.status())
                    .headers(httpHeaders)
                    .body(decodedObject);
        }
        return delegate.decode(response, type);
    }
}

