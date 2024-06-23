package com.example.stacks.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public static <T,D> D toDto(T entity, Class<D> dto) {
        try {
            D newDto = dto.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, newDto);
            return newDto;
        } catch (Exception e) {
            return null;

        }
    }

    public static <T, D> List<D> fetchEntitiesWithMapper(JpaRepository<T, Long> repository,  Class<D> dtoClass) {
        List<T> entities = repository.findAll();
        List<D> dtos = new ArrayList<>();

        for (T entity : entities) {
            D dto = toDto(entity, dtoClass);
            dtos.add(dto);
        }

        return dtos;
    }
}
