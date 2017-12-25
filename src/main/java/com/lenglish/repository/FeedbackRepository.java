package com.lenglish.repository;

import com.lenglish.domain.Feedback;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select feedback from Feedback feedback where feedback.user.login = ?#{principal.username}")
    List<Feedback> findByUserIsCurrentUser();

}
