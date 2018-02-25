package com.ef.Parser.model;

import lombok.Data;

import javax.persistence.*;

import java.util.Date;

@Data
@Entity(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String ip;
    private String request;
    private String status;
    private String userAgent;
}
