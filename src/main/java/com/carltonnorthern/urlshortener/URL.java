package com.carltonnorthern.urlshortener;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class URL {

    private @Id @GeneratedValue Long id;
    private String LongURL;
    private String role;

    URL() {}

    URL(String LongURL, String role) {

        this.LongURL = LongURL;
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    public String getLongURL() {
        return this.LongURL;
    }

    public String getRole() {
        return this.role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLongURL(String longURL) {
        this.LongURL = longURL;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof URL))
            return false;
        URL URL = (URL) o;
        return Objects.equals(this.id, URL.id) && Objects.equals(this.LongURL, URL.LongURL)
                && Objects.equals(this.role, URL.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.LongURL, this.role);
    }

    @Override
    public String toString() {
        return "URL{" + "id=" + this.id + ", name='" + this.LongURL + '\'' + ", role='" + this.role + '\'' + '}';
    }
}