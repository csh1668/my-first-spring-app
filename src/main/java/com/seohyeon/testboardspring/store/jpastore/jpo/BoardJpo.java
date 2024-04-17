package com.seohyeon.testboardspring.store.jpastore.jpo;

import com.seohyeon.testboardspring.aggregate.Board;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TEST_BOARD")
public class BoardJpo {
    @Id
    private String id;
    private String title;
    private String context;
    private long postTime;


    public BoardJpo(Board original){
        this.id = original.getId();
        this.title = original.getTitle();
        this.context = original.getContext();
        this.postTime = original.getPostTime();
    }

    public Board toDomain(){
        Board board = new Board();
        board.setId(id);
        board.setTitle(title);
        board.setContext(context);
        board.setPostTime(postTime);
        return board;
    }

}
