package com.lenglish.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lenglish.domain.Lesson;
import com.lenglish.domain.LessonLog;
import com.lenglish.domain.User;

/**
 * Spring Data JPA repository for the LessonLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonLogRepository extends JpaRepository<LessonLog, Long> {

    @Query("select lesson_log from LessonLog lesson_log where lesson_log.user.login = ?#{principal.username}")
    List<LessonLog> findByUserIsCurrentUser();

    Page<LessonLog> findAllByLessonAndUser(Pageable pageable, Lesson lesson, User user);

	LessonLog findTopByUserAndLessonOrderByCreateDateDesc(User userWithAuthorities, Lesson lesson);
	
	List<LessonLog> findByCreateDateBetween(ZonedDateTime start, ZonedDateTime end);

	List<LessonLog> findByUserAndCreateDateBetween(User user, ZonedDateTime start, ZonedDateTime end);
}
