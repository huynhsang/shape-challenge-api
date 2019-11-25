package com.sanght.shapechallenge.common.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;

import java.util.Optional;

public final class ResponseUtil {
    private ResponseUtil() {
    }

    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, (HttpHeaders)null);
    }

    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return (ResponseEntity<X>)maybeResponse.map((response) -> {
            return ((BodyBuilder)ResponseEntity.ok().headers(header)).body(response);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public static <X> ResponseEntity<X> createdOrNot(Optional<X> maybeResponse) {
        return createdOrNot(maybeResponse, null);
    }

    public static <X> ResponseEntity<X> createdOrNot(Optional<X> maybeResponse, HttpHeaders header) {
        return (ResponseEntity<X>)maybeResponse.map((response) -> {
            return ((BodyBuilder)ResponseEntity.status(HttpStatus.CREATED).headers(header)).body(response);
        }).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}