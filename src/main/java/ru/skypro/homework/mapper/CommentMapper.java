package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.util.DateMapper;

@Mapper(uses = DateMapper.class)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    default CommentDto commentToCommentDto(Comment comment) {
        return commentToCommentDto(comment, comment.getUser().getAvatar().getAvatarApi());
    }

    @Mapping(source = "comment.id", target = "pk")
    @Mapping(source = "comment.user.id", target = "author")
    @Mapping(source = "userAvatarApi", target = "authorImage")
    @Mapping(source = "comment.user.firstName", target = "authorFirstName")
    CommentDto commentToCommentDto(Comment comment, String userAvatarApi);

    @Mapping(source = "pk", target = "id")
    @Mapping(source = "author", target = "user.id")
    @Mapping(source = "authorFirstName", target = "user.firstName")
    Comment commentDtoToComment(CommentDto commentDto);

    Comment createCommentDtoToComment(CreateCommentDto createCommentDto);

    CreateCommentDto commentToCreateCommentDto(Comment comment);
}
