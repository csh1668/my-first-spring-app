package com.seohyeon.testboardspring.store;

import com.seohyeon.testboardspring.aggregate.Board;

import java.util.List;

public interface BoardStore {
    String create(Board board);
    Board retrieve(String id);
    List<Board> all();
    void update(Board board);
    void delete(String id);
    boolean exists(String id);
}
