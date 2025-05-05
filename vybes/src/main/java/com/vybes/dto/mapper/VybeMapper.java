package com.vybes.dto.mapper;

import com.vybes.dto.VybeDTO;
import com.vybes.dto.request.CreateVybeRequestDTO;
import com.vybes.model.Vybe;

import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {LikeMapper.class, CommentMapper.class})
public interface VybeMapper {
    Vybe transform(CreateVybeRequestDTO vybe);

    VybeDTO transform(Vybe vybe);
}
