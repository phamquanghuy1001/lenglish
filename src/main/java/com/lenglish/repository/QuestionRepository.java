package com.lenglish.repository;

import com.lenglish.domain.Lesson;
import com.lenglish.domain.Question;
import com.lenglish.service.dto.QuestionDTO;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Query("select distinct question from Question question left join fetch question.tests")
	List<Question> findAllWithEagerRelationships();

	@Query("select question from Question question left join fetch question.tests where question.id =:id")
	Question findOneWithEagerRelationships(@Param("id") Long id);

	Page<Question> findAllByLesson(Pageable pageable, Lesson lesson);

}
