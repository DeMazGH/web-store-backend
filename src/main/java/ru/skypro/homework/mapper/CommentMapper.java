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


    //вот здесь не совсем 1 в 1 маппинг для image будет: в БД хранится путь к картинке в файловой системе,
    // а отдавать надо эндпоинт, по которому можно получить будет байты картинки
    @Mapping(source = "pk", target = "id")
    @Mapping(source = "author", target = "user.id")
    @Mapping(source = "authorImage", target = "user.image")
    @Mapping(source = "authorFirstName", target = "user.firstName")
    Comment commentDtoToComment(CommentDto commentDto);

    Comment createCommentDtoToComment(CreateCommentDto createCommentDto);
    CreateCommentDto commentToCreateCommentDto(Comment comment);
}
