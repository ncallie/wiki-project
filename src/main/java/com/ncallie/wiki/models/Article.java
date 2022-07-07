package com.ncallie.wiki.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    private String wiki;
    private LocalDateTime create_timestamp;
    private LocalDateTime timestamp;
    private String title;
    private String text;
    private String language;
    private List<String> category;
    private List<String> auxiliary_text;

    @JsonIgnoreProperties
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public Article() {
    }

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public LocalDateTime getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(String create_timestamp) {
        this.create_timestamp = LocalDateTime.parse(create_timestamp, formatter);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = LocalDateTime.parse(timestamp, formatter);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getAuxiliary_text() {
        return auxiliary_text;
    }

    public void setAuxiliary_text(List<String> auxiliary_text) {
        this.auxiliary_text = auxiliary_text;
    }

    public String getTrimmedText() {
        List<String> lines = new ArrayList<>();
        try (final InputStream inputStream = IOUtils.toInputStream(this.getText(), StandardCharsets.UTF_8)) {
            final LineIterator lineIterator = IOUtils.lineIterator(inputStream, StandardCharsets.UTF_8);
            lineIterator.next();
            while (lineIterator.hasNext()) {
                lines.add(lineIterator.next());
            }
            lineIterator.close();
            String trimmed = lines.stream().collect(Collectors.joining()).trim();
            return trimmed;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("wiki", wiki).append("title", title)
                .append("text", text).append("create_timestamp", create_timestamp)
                .append("timestamp", timestamp).append("language", language)
                .append("category", category)
                .append("auxiliary_text", auxiliary_text).toString();
    }
}
