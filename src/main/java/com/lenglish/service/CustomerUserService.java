package com.lenglish.service;

import com.lenglish.domain.CustomerUser;
import com.lenglish.domain.User;
import com.lenglish.repository.CustomerUserRepository;
import com.lenglish.repository.UserRepository;
import com.lenglish.service.dto.CustomerUserDTO;
import com.lenglish.service.mapper.CustomerUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing CustomerUser.
 */
@Service
@Transactional
public class CustomerUserService {

    private final Logger log = LoggerFactory.getLogger(CustomerUserService.class);

    private final CustomerUserRepository customerUserRepository;

    private final CustomerUserMapper customerUserMapper;

    private final UserRepository userRepository;

    public CustomerUserService(CustomerUserRepository customerUserRepository, CustomerUserMapper customerUserMapper, UserRepository userRepository) {
        this.customerUserRepository = customerUserRepository;
        this.customerUserMapper = customerUserMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a customerUser.
     *
     * @param customerUserDTO the entity to save
     * @return the persisted entity
     */
    public CustomerUserDTO save(CustomerUserDTO customerUserDTO) {
        log.debug("Request to save CustomerUser : {}", customerUserDTO);
        CustomerUser customerUser = customerUserMapper.toEntity(customerUserDTO);
        customerUser = customerUserRepository.save(customerUser);
        return customerUserMapper.toDto(customerUser);
    }

    /**
     *  Get all the customerUsers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CustomerUser> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerUsers");
        return customerUserRepository.findAll(pageable);
    }

    /**
     *  Get one customerUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CustomerUserDTO findOne(Long id) {
        log.debug("Request to get CustomerUser : {}", id);
        CustomerUser customerUser = customerUserRepository.findOne(id);
        return customerUserMapper.toDto(customerUser);
    }

    @Transactional(readOnly = true)
    public CustomerUser findFullOne(String login) {
        log.debug("Request to get CustomerUser : {}", login);
        Optional<User> user = userRepository.findOneByLogin(login);
        CustomerUser customerUser = customerUserRepository.findOneByUser(user.get());
        return customerUser;
    }

    /**
     *  Delete the  customerUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerUser : {}", id);
        customerUserRepository.delete(id);
    }

	public CustomerUserDTO findOneByUser(User user) {
		log.debug("Request to get current CustomerUser ");
		CustomerUser customerUser = customerUserRepository.findOneByUser(user);
		return customerUserMapper.toDto(customerUser);
	}

	public void resetTodayPoint() {
		customerUserRepository.resetTodayPoint();
		
	}
}
