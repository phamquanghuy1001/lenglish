package com.lenglish.repository;

import com.lenglish.domain.CustomerUser;
import com.lenglish.domain.User;
import com.lenglish.service.dto.CustomerUserDTO;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CustomerUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long> {

	CustomerUser findOneByUser(User user);

	@Modifying(clearAutomatically = true)
	@Query("update CustomerUser c SET c.todayPoint = 0")
	void resetTodayPoint();

}
