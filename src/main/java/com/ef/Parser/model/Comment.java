package com.ef.Parser.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public String ip;
    public String comment;
}
