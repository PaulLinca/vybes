package com.vybes.service.vybe.mapper;

import com.vybes.dto.CreateVybeRequestDTO;
import com.vybes.service.vybe.dto.VybeDTO;
import com.vybes.service.vybe.entity.Vybe;

import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {LikeMapper.class, CommentMapper.class})
public interface VybeMapper {
    Vybe transform(CreateVybeRequestDTO vybe);

    VybeDTO transform(Vybe vybe);
}
