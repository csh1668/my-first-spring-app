package com.seohyeon.testboardspring.store.jpastore;

import com.seohyeon.testboardspring.aggregate.Board;
import com.seohyeon.testboardspring.store.BoardStore;
import com.seohyeon.testboardspring.store.jpastore.jpo.BoardJpo;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BoardJpaStore implements BoardStore {

    private BoardJpaRepository repository;

    public BoardJpaStore(BoardJpaRepository repository){
        this.repository = repository;
    }
    @Override
    public String create(Board board) {
        repository.save(new BoardJpo(board));
        return board.getId();
    }

    @Override
    public Board retrieve(String id) {
        Optional<BoardJpo> board = repository.findById(id);
        if (board.isPresent()){
            return board.get().toDomain();
        }
        throw new NoSuchElementException("No such board with id: " + id);
    }

    @Override
    public List<Board> all() {
        return repository.findAll().stream().map(BoardJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void update(Board board) {
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public boolean exists(String id) {
        return repository.existsById(id);
    }
}
