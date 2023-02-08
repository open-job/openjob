package io.openjob.server.repository.mapper;

import io.openjob.server.repository.dto.AdminPermissionDTO;
import io.openjob.server.repository.entity.AdminPermission;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author inhere
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AdminPermissionMapper {
    /**
     * dto to entity
     *
     * @param adminPermDTO dto
     * @return entity
     */
    AdminPermission toEntity(AdminPermissionDTO adminPermDTO);

    /**
     * entity to dto
     *
     * @param adminPerm entity
     * @return dto
     */
    AdminPermissionDTO toDto(AdminPermission adminPerm);

    /**
     * partial update entity from dto
     *
     * @param adminPermDTO dto
     * @param adminPerm    entity
     * @return entity
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AdminPermission partialUpdate(AdminPermissionDTO adminPermDTO, @MappingTarget AdminPermission adminPerm);
}