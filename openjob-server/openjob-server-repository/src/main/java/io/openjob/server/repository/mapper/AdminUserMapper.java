package io.openjob.server.repository.mapper;

import io.openjob.server.repository.dto.AdminUserDTO;
import io.openjob.server.repository.entity.AdminUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author inhere
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AdminUserMapper {
    /**
     * dto to entity
     *
     * @param adminUserDTO dto
     * @return entity
     */
    AdminUser toEntity(AdminUserDTO adminUserDTO);

    /**
     * entity to dto
     *
     * @param adminUser entity
     * @return dto
     */
    AdminUserDTO toDto(AdminUser adminUser);

    /**
     * partial update entity from dto
     *
     * @param adminUserDTO dto
     * @param adminUser entity
     * @return entity
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AdminUser partialUpdate(AdminUserDTO adminUserDTO, @MappingTarget AdminUser adminUser);
}