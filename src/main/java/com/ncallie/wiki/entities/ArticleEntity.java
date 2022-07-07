package com.ncallie.wiki.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ncallie.wiki.utils.ConverterLocalDateTimeToLong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String wiki;
    private LocalDateTime create_timestamp;
    private LocalDateTime timestamp;
    @Column(columnDefinition = "text")
    private String title;
    @Column(columnDefinition = "text")
    private String text;
    private String language;

    public void setCreate_timestamp(LocalDateTime create_timestamp) {
        this.create_timestamp = create_timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @JsonSerialize(converter = ConverterLocalDateTimeToLong.class)
    public LocalDateTime getCreate_timestamp() {
        return create_timestamp;
    }

    @JsonSerialize(converter = ConverterLocalDateTimeToLong.class)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "categories", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnoreProperties("articles")
    private List<Category> category = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(columnDefinition = "text")
    @CollectionTable(name = "auxiliary_text")
    private List<String> auxiliary_text;
}
