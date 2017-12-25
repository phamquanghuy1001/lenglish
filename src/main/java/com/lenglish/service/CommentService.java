package com.lenglish.service;

import com.lenglish.domain.Comment;
import com.lenglish.domain.Post;
import com.lenglish.repository.CommentRepository;
import com.lenglish.service.dto.CommentDTO;
import com.lenglish.service.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Comment.
 */
@Service
@Transactional
public class CommentService {

	private final Logger log = LoggerFactory.getLogger(CommentService.class);

	private final CommentRepository commentRepository;

	private final CommentMapper commentMapper;

	public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
		this.commentRepository = commentRepository;
		this.commentMapper = commentMapper;
	}

	/**
	 * Save a comment.
	 *
	 * @param commentDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	public CommentDTO save(CommentDTO commentDTO) {
		log.debug("Request to save Comment : {}", commentDTO);
		Comment comment = commentMapper.toEntity(commentDTO);
		comment = commentRepository.save(comment);
		return commentMapper.toDto(comment);
	}

	/**
	 * Get all the comments.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<CommentDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Comments");
		return commentRepository.findAll(pageable).map(commentMapper::toDto);
	}

	/**
	 * Get one comment by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public CommentDTO findOne(Long id) {
		log.debug("Request to get Comment : {}", id);
		Comment comment = commentRepository.findOne(id);
		return commentMapper.toDto(comment);
	}

	/**
	 * Delete the comment by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Comment : {}", id);
		commentRepository.delete(id);
	}

	public Page<CommentDTO> findAllByPost(Pageable pageable, Post post) {
		log.debug("Request to get all Comments by postId : {}", post.getId());
		return commentRepository.findAllByPost(pageable, post).map(commentMapper::toDto);
	}
}
