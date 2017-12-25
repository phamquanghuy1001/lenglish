package com.lenglish.service.mapper;

import com.lenglish.domain.*;
import com.lenglish.service.dto.CustomerUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustomerUser and its DTO CustomerUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RoomMapper.class})
public interface CustomerUserMapper extends EntityMapper<CustomerUserDTO, CustomerUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "room.id", target = "roomId")
    CustomerUserDTO toDto(CustomerUser customerUser); 

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "roomId", target = "room")
    CustomerUser toEntity(CustomerUserDTO customerUserDTO);

    default CustomerUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerUser customerUser = new CustomerUser();
        customerUser.setId(id);
        return customerUser;
    }
}
