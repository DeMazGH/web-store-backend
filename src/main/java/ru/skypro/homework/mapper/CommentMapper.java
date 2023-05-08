package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.util.*;

@Mapper(uses = DateMapper.class)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.id", target = "author")
    @Mapping(source = "user.image", target = "authorImage")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    CommentDto commentToCommentDto(Comment comment);

    @Mapping(source = "pk", target = "id")
    @Mapping(source = "author", target = "user.id")
    @Mapping(source = "authorImage", target = "user.image")
    @Mapping(source = "authorFirstName", target = "user.firstName")
    Comment commentDtoToComment(CommentDto commentDto);

    Comment createCommentDtoToComment(CreateCommentDto createCommentDto);
    CreateCommentDto commentToCreateCommentDto(Comment comment);
}
