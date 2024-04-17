package com.seohyeon.testboardspring.controllers;

import com.seohyeon.testboardspring.aggregate.Board;
import com.seohyeon.testboardspring.service.cdo.BoardCdo;
import com.seohyeon.testboardspring.store.BoardStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {

    private BoardStore boardStore;

    public BoardController(BoardStore boardStore){
        this.boardStore = boardStore;
    }

    @PostMapping
    public String create(@RequestBody BoardCdo boardCdo){
        Board board = new Board(boardCdo.getTitle(), boardCdo.getContext());
        return boardStore.create(board);
    }
}
