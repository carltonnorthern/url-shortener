package com.carltonnorthern.urlshortener;

class URLNotFoundException extends RuntimeException {

    URLNotFoundException(Long id) {
        super("Could not find URL " + id);
    }
}