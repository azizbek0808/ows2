package uz.com.mapper;

import uz.com.dto.CrudDto;
import uz.com.hibernate.base._Entity;

import java.util.List;

/**
 *
 * @param <E> - Entity
 * @param <D> - Dto
 * @param <CD> - CreateDTO
 * @param <UD> - UpdateDTO
 */

public interface BaseMapper<E extends _Entity, D, CD extends CrudDto, UD extends CrudDto> {
    D toDto(E entity);
    E fromDto(D dto);
    List<D> toDto(List<E> entityList);
    List<E> fromDto(List<D> dtoList);
    E fromCreateDto(CD createDto);
    E fromUpdateDto(UD updateDto);
}
