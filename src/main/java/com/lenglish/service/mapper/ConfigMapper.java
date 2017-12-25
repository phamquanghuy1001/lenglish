package com.lenglish.service.mapper;

import com.lenglish.domain.*;
import com.lenglish.service.dto.ConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Config and its DTO ConfigDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfigMapper extends EntityMapper<ConfigDTO, Config> {

    

    

    default Config fromId(Long id) {
        if (id == null) {
            return null;
        }
        Config config = new Config();
        config.setId(id);
        return config;
    }
}
