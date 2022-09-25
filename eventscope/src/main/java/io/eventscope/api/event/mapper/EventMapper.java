package io.eventscope.api.event.mapper;

import io.eventscope.api.event.model.Event;
import io.eventscope.api.event.model.EventEntity;
import io.eventscope.common.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "cdi",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EventMapper extends BaseMapper<Event, EventEntity> {
}
