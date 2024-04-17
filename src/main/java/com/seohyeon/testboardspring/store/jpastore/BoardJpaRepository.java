package com.seohyeon.testboardspring.store.jpastore;


import com.seohyeon.testboardspring.store.jpastore.jpo.BoardJpo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<BoardJpo, String> {

}
