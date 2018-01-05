package com.lenglish.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lenglish.domain.Exam;
import com.lenglish.domain.Lesson;
import com.lenglish.domain.Question;

/**
 * Spring Data JPA repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("select distinct question from Question question left join fetch question.exams")
    List<Question> findAllWithEagerRelationships();

    @Query("select question from Question question left join fetch question.exams where question.id =:id")
    Question findOneWithEagerRelationships(@Param("id") Long id);

	Page<Question> findAllByLesson(Pageable pageable, Lesson lesson);

	Page<Question> findAllByExams(Pageable pageable, Exam exam);

	List<Question> findAllByLesson(Lesson lesson);

	List<Question> findAllByExams(Exam exam);

}
