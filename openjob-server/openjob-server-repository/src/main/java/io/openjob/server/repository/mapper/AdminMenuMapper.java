package io.openjob.server.repository.mapper;

import io.openjob.server.repository.dto.AdminMenuDTO;
import io.openjob.server.repository.entity.AdminMenu;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author inhere
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AdminMenuMapper {
    /**
     * dto to entity
     *
     * @param adminMenuDTO dto
     * @return entity
     */
    AdminMenu toEntity(AdminMenuDTO adminMenuDTO);

    /**
     * entity to dto
     *
     * @param adminMenu entity
     * @return dto
     */
    AdminMenuDTO toDto(AdminMenu adminMenu);

    /**
     * partial update entity from dto
     *
     * @param adminMenuDTO dto
     * @param adminMenu    entity
     * @return entity
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AdminMenu partialUpdate(AdminMenuDTO adminMenuDTO, @MappingTarget AdminMenu adminMenu);
}