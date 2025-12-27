package com.giftsandbows.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CmsBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // home, about, footer
    @Column(nullable = false)
    private String page;

    // owner_thoughts, footer_text, etc.
    @Column(nullable = false)
    private String blockKey;

    @Column(nullable = false, length = 2000)
    private String content;

    private int posX;
    private int posY;
    private int fontSize;

    private String color;
}
