package com.lenglish.repository;

import com.lenglish.domain.Lesson;
import com.lenglish.domain.LessonLog;
import com.lenglish.domain.User;
import com.lenglish.service.dto.LessonLogDTO;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the LessonLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonLogRepository extends JpaRepository<LessonLog, Long> {

    @Query("select lesson_log from LessonLog lesson_log where lesson_log.user.login = ?#{principal.username}")
    List<LessonLog> findByUserIsCurrentUser();

	Page<LessonLog> findAllByLessonAndUser(Pageable pageable, Lesson lesson, User user);

}
