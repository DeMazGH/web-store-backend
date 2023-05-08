package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Comment;

import java.util.List;

@Mapper(uses = CommentMapper.class)
public interface ResponseWrapperCommentDtoMapper {

    ResponseWrapperCommentDtoMapper INSTANCE = Mappers.getMapper(ResponseWrapperCommentDtoMapper.class);

    default ResponseWrapperCommentDto toResponseWrapperCommentDto(List<Comment> comments) {
        return toResponseWrapperCommentDto(comments.size(), comments);
    }

    @Mapping(target = "results", source = "comments")
    @Mapping(target = "count", source = "count")
    ResponseWrapperCommentDto toResponseWrapperCommentDto(Integer count, List<Comment> comments);
}
