package com.lenglish.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lenglish.service.ConfigService;
import com.lenglish.web.rest.errors.BadRequestAlertException;
import com.lenglish.web.rest.util.HeaderUtil;
import com.lenglish.web.rest.util.PaginationUtil;
import com.lenglish.service.dto.ConfigDTO;
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
 * REST controller for managing Config.
 */
@RestController
@RequestMapping("/api")
public class ConfigResource {

    private final Logger log = LoggerFactory.getLogger(ConfigResource.class);

    private static final String ENTITY_NAME = "config";

    private final ConfigService configService;

    public ConfigResource(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * POST  /configs : Create a new config.
     *
     * @param configDTO the configDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new configDTO, or with status 400 (Bad Request) if the config has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/configs")
    @Timed
    public ResponseEntity<ConfigDTO> createConfig(@Valid @RequestBody ConfigDTO configDTO) throws URISyntaxException {
        log.debug("REST request to save Config : {}", configDTO);
        if (configDTO.getId() != null) {
            throw new BadRequestAlertException("A new config cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigDTO result = configService.save(configDTO);
        return ResponseEntity.created(new URI("/api/configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /configs : Updates an existing config.
     *
     * @param configDTO the configDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated configDTO,
     * or with status 400 (Bad Request) if the configDTO is not valid,
     * or with status 500 (Internal Server Error) if the configDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/configs")
    @Timed
    public ResponseEntity<ConfigDTO> updateConfig(@Valid @RequestBody ConfigDTO configDTO) throws URISyntaxException {
        log.debug("REST request to update Config : {}", configDTO);
        if (configDTO.getId() == null) {
            return createConfig(configDTO);
        }
        ConfigDTO result = configService.save(configDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /configs : get all the configs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of configs in body
     */
    @GetMapping("/configs")
    @Timed
    public ResponseEntity<List<ConfigDTO>> getAllConfigs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Configs");
        Page<ConfigDTO> page = configService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/configs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /configs/:id : get the "id" config.
     *
     * @param id the id of the configDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the configDTO, or with status 404 (Not Found)
     */
    @GetMapping("/configs/{id}")
    @Timed
    public ResponseEntity<ConfigDTO> getConfig(@PathVariable Long id) {
        log.debug("REST request to get Config : {}", id);
        ConfigDTO configDTO = configService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(configDTO));
    }

    /**
     * DELETE  /configs/:id : delete the "id" config.
     *
     * @param id the id of the configDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteConfig(@PathVariable Long id) {
        log.debug("REST request to delete Config : {}", id);
        configService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
