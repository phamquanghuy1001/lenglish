package com.lenglish.repository;

import com.lenglish.domain.ExamLog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ExamLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamLogRepository extends JpaRepository<ExamLog, Long> {

    @Query("select exam_log from ExamLog exam_log where exam_log.user.login = ?#{principal.username}")
    List<ExamLog> findByUserIsCurrentUser();

}
