package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.util.DateMapper;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface CommentMapper {

    default CommentDto commentToCommentDto(Comment comment) {
        return commentToCommentDto(comment, comment.getUser().getAvatar().getAvatarApi());
    }

    @Mapping(source = "comment.id", target = "pk")
    @Mapping(source = "comment.user.id", target = "author")
    @Mapping(source = "userAvatarApi", target = "authorImage")
    @Mapping(source = "comment.user.firstName", target = "authorFirstName")
    CommentDto commentToCommentDto(Comment comment, String userAvatarApi);

    Comment createCommentDtoToComment(CreateCommentDto createCommentDto);
}
