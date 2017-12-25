package com.lenglish.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lenglish.domain.Post;
import com.lenglish.service.CommentService;
import com.lenglish.service.UserService;
import com.lenglish.web.rest.errors.BadRequestAlertException;
import com.lenglish.web.rest.util.HeaderUtil;
import com.lenglish.web.rest.util.PaginationUtil;
import com.lenglish.service.dto.CommentDTO;
import com.lenglish.service.util.DateTimeUtil;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Comment.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

	private final Logger log = LoggerFactory.getLogger(CommentResource.class);

	private static final String ENTITY_NAME = "comment";

	private final CommentService commentService;

	private final UserService userService;

	public CommentResource(CommentService commentService, UserService userService) {
		this.commentService = commentService;
		this.userService = userService;
	}

	/**
	 * POST /comments : Create a new comment.
	 *
	 * @param commentDTO
	 *            the commentDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         commentDTO, or with status 400 (Bad Request) if the comment has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/comments")
	@Timed
	public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO)
			throws URISyntaxException {
		log.debug("REST request to save Comment : {}", commentDTO);
		if (commentDTO.getId() != null) {
			throw new BadRequestAlertException("A new comment cannot already have an ID", ENTITY_NAME, "idexists");
		}
		commentDTO.setCreateDate(DateTimeUtil.now());
		commentDTO.setUserId(userService.getUserWithAuthorities().getId());
		CommentDTO result = commentService.save(commentDTO);
		return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /comments : Updates an existing comment.
	 *
	 * @param commentDTO
	 *            the commentDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         commentDTO, or with status 400 (Bad Request) if the commentDTO is not
	 *         valid, or with status 500 (Internal Server Error) if the commentDTO
	 *         couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/comments")
	@Timed
	public ResponseEntity<CommentDTO> updateComment(@Valid @RequestBody CommentDTO commentDTO)
			throws URISyntaxException {
		log.debug("REST request to update Comment : {}", commentDTO);
		if (commentDTO.getId() == null) {
			return createComment(commentDTO);
		}
		CommentDTO result = commentService.save(commentDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentDTO.getId().toString())).body(result);
	}

	/**
	 * GET /comments : get all the comments.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of comments in
	 *         body
	 */
	@GetMapping("/comments")
	@Timed
	public ResponseEntity<List<CommentDTO>> getAllComments(@ApiParam Pageable pageable) {
		log.debug("REST request to get a page of Comments");
		Page<CommentDTO> page = commentService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comments");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /comments/:id : get the "id" comment.
	 *
	 * @param id
	 *            the id of the commentDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the commentDTO,
	 *         or with status 404 (Not Found)
	 */
	@GetMapping("/comments/{id}")
	@Timed
	public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
		log.debug("REST request to get Comment : {}", id);
		CommentDTO commentDTO = commentService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentDTO));
	}

	/**
	 * DELETE /comments/:id : delete the "id" comment.
	 *
	 * @param id
	 *            the id of the commentDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/comments/{id}")
	@Timed
	public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
		log.debug("REST request to delete Comment : {}", id);
		commentService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}

	/**
	 * GET /comments : get all the comments.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of comments in
	 *         body
	 */
	@GetMapping("/comments_by_post/{id}")
	@Timed
	public ResponseEntity<List<CommentDTO>> getAllComments(@ApiParam Pageable pageable, @PathVariable Long id) {
		log.debug("REST request to get a page of Comments");
		Post post = new Post();
		post.setId(id);
		Page<CommentDTO> page = commentService.findAllByPost(pageable, post);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comments_by_post/" + id);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}
}
