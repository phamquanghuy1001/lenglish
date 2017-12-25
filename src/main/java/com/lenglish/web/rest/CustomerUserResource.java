package com.lenglish.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.lenglish.domain.CustomerUser;
import com.lenglish.service.CustomerUserService;
import com.lenglish.service.UserService;
import com.lenglish.service.dto.CustomerUserDTO;
import com.lenglish.web.rest.errors.BadRequestAlertException;
import com.lenglish.web.rest.util.HeaderUtil;
import com.lenglish.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing CustomerUser.
 */
@RestController
@RequestMapping("/api")
public class CustomerUserResource {

	private final Logger log = LoggerFactory.getLogger(CustomerUserResource.class);

	private static final String ENTITY_NAME = "customerUser";

	private final CustomerUserService customerUserService;
	private final UserService userService;

	public CustomerUserResource(CustomerUserService customerUserService, UserService userService) {
		this.customerUserService = customerUserService;
		this.userService = userService;
	}

	/**
	 * POST /customer-users : Create a new customerUser.
	 *
	 * @param customerUserDTO
	 *            the customerUserDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         customerUserDTO, or with status 400 (Bad Request) if the customerUser
	 *         has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/customer-users")
	@Timed
	public ResponseEntity<CustomerUserDTO> createCustomerUser(@Valid @RequestBody CustomerUserDTO customerUserDTO)
			throws URISyntaxException {
		log.debug("REST request to save CustomerUser : {}", customerUserDTO);
		if (customerUserDTO.getId() != null) {
			throw new BadRequestAlertException("A new customerUser cannot already have an ID", ENTITY_NAME, "idexists");
		}
		CustomerUserDTO result = customerUserService.save(customerUserDTO);
		return ResponseEntity.created(new URI("/api/customer-users/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /customer-users : Updates an existing customerUser.
	 *
	 * @param customerUserDTO
	 *            the customerUserDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         customerUserDTO, or with status 400 (Bad Request) if the
	 *         customerUserDTO is not valid, or with status 500 (Internal Server
	 *         Error) if the customerUserDTO couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/customer-users")
	@Timed
	public ResponseEntity<CustomerUserDTO> updateCustomerUser(@Valid @RequestBody CustomerUserDTO customerUserDTO)
			throws URISyntaxException {
		log.debug("REST request to update CustomerUser : {}", customerUserDTO);
		if (customerUserDTO.getId() == null) {
			return createCustomerUser(customerUserDTO);
		}
		CustomerUserDTO result = customerUserService.save(customerUserDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerUserDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /customer-users : get all the customerUsers.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of customerUsers
	 *         in body
	 */
	@GetMapping("/customer-users")
	@Timed
	public ResponseEntity<List<CustomerUser>> getAllCustomerUsers(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of CustomerUsers");
		Page<CustomerUser> page = customerUserService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-users");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /customer-users/:id : get the "id" customerUser.
	 *
	 * @param id
	 *            the id of the customerUserDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         customerUserDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/customer-users/{id}")
	@Timed
	public ResponseEntity<CustomerUserDTO> getCustomerUser(@PathVariable Long id) {
		log.debug("REST request to get CustomerUser : {}", id);
		CustomerUserDTO customerUserDTO = customerUserService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerUserDTO));
	}

	/**
	 * DELETE /customer-users/:id : delete the "id" customerUser.
	 *
	 * @param id
	 *            the id of the customerUserDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/customer-users/{id}")
	@Timed
	public ResponseEntity<Void> deleteCustomerUser(@PathVariable Long id) {
		log.debug("REST request to delete CustomerUser : {}", id);
		customerUserService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	// new code
	@GetMapping("/current_customer-user")
	@Timed
	public ResponseEntity<CustomerUserDTO> getCustomerUser() {
		log.debug("REST request to get current CustomerUser");
		CustomerUserDTO customerUserDTO = customerUserService.findOneByUser(userService.getUserWithAuthorities());
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerUserDTO));
	}
}
