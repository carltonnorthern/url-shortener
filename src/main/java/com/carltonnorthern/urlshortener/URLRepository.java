package com.carltonnorthern.urlshortener;

import org.springframework.data.jpa.repository.JpaRepository;

interface URLRepository extends JpaRepository<URL, Long> {

}