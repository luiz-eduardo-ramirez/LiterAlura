package br.com.alura.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBookRecord(@JsonAlias("id") Long id,
                            @JsonAlias("title") String title,
                            @JsonAlias("authors") List<DataAuthorRecord> authors,
                            @JsonAlias("download_count") Integer download_count,
                            @JsonProperty("languages") List<String> languages,
                            @JsonAlias("formats") Map<String, String> formats) {
    public String getLanguage() {
        return languages != null && !languages.isEmpty() ? languages.get(0) : null;
    }
}
