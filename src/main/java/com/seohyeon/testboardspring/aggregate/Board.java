package com.seohyeon.testboardspring.aggregate;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Board {
    private String id;
    private String title;
    private String context;
    private long postTime;

    public Board(){
        this.id = UUID.randomUUID().toString();
        this.postTime = System.currentTimeMillis();
    }

    public Board(String title, String context){
        this();
        this.title = title;
        this.context = context;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", postTime=" + postTime +
                '}';
    }
}