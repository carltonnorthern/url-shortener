package com.carltonnorthern.urlshortener;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Url {

    private @Id @GeneratedValue Long id;
    private String longurl;

    Url() {}

    Url(String longurl) {

        this.longurl = longurl;
    }

    public Long getId() {
        return this.id;
    }

    public String getLongurl() {
        return this.longurl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLongurl(String longurl) {
        this.longurl = longurl;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Url))
            return false;
        Url URL = (Url) o;
        return Objects.equals(this.id, URL.id) && Objects.equals(this.longurl, URL.longurl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.longurl);
    }

    @Override
    public String toString() {
        return "URL{" + "id=" + this.id + ", longurl='" + this.longurl + '}';
    }
}