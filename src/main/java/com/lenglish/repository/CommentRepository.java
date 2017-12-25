package com.lenglish.repository;

import com.lenglish.domain.Comment;
import com.lenglish.domain.Post;
import com.lenglish.service.dto.CommentDTO;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select comment from Comment comment where comment.user.login = ?#{principal.username}")
    List<Comment> findByUserIsCurrentUser();

	Page<Comment> findAllByPost(Pageable pageable, Post post);

}
