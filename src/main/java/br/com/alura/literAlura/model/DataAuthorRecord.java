package br.com.alura.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataAuthorRecord(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Long birthYear,
        @JsonAlias("death_year") Long deathYear
) {}
