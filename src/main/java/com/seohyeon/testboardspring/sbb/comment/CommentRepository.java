package com.seohyeon.testboardspring.sbb.comment;

import com.seohyeon.testboardspring.sbb.article.Article;
import com.seohyeon.testboardspring.sbb.article.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByParentTypeAndParentId(ArticleType parentType, Integer id);
}
