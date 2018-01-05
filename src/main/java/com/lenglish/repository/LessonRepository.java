package com.lenglish.repository;

import com.lenglish.domain.Lesson;
import com.lenglish.service.dto.LessonDTO;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lesson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

	Page<Lesson> findAllByActivated(Pageable pageable, boolean activated);

	List<Lesson> findAllByLevel(int level);

}
