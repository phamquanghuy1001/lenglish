package com.lenglish.repository;

import com.lenglish.domain.Answer;
import com.lenglish.domain.Question;
import com.lenglish.service.dto.AnswerDTO;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Answer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	Page<Answer> findAllByQuestion(Pageable pageable, Question question);

}
