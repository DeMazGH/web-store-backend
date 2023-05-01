package ru.skypro.homework;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

public class ConstantsTest {

    public static final Integer USER_ID = 1;
    public static final String EMAIL = "user@gmail.com";
    public static final String FIRST_NAME = "first name";
    public static final String LAST_NAME = "last name";
    public static final String PHONE = "+79876543211";
    public static final String IMAGE = "image path";
    public static final String PASSWORD = "password";
    public static final Role ROLE = Role.USER;

    public static final Integer COMMENT_ID = 1;
    public static final String TEXT = "text";
    public static final long CREATED_AT = 999L;

    public static final Integer ADS_ID = 1;
    public static final Integer PRICE = 99;
    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";

    public static final User USER_TEST = new User(USER_ID, EMAIL, FIRST_NAME, LAST_NAME, PHONE, IMAGE, PASSWORD, ROLE);
    public static final UserDto USER_DTO_TEST = new UserDto();
    public static final RegisterReq REGISTER_REQ_TEST = new RegisterReq();
    public static final Ads ADS_TEST = new Ads(ADS_ID, PRICE, DESCRIPTION, TITLE, USER_TEST);
    public static final Comment COMMENT_TEST = new Comment(COMMENT_ID, TEXT, CREATED_AT, ADS_TEST, USER_TEST);
    public static final CommentDto COMMENT_DTO_TEST = new CommentDto();

    static {
        USER_DTO_TEST.setId(USER_ID);
        USER_DTO_TEST.setEmail(EMAIL);
        USER_DTO_TEST.setFirstName(FIRST_NAME);
        USER_DTO_TEST.setLastName(LAST_NAME);
        USER_DTO_TEST.setPhone(PHONE);
        USER_DTO_TEST.setImage(IMAGE);

        REGISTER_REQ_TEST.setUsername(EMAIL);
        REGISTER_REQ_TEST.setPassword(PASSWORD);
        REGISTER_REQ_TEST.setFirstName(FIRST_NAME);
        REGISTER_REQ_TEST.setLastName(LAST_NAME);
        REGISTER_REQ_TEST.setPhone(PHONE);
        REGISTER_REQ_TEST.setRole(ROLE);

        COMMENT_DTO_TEST.setAuthor(USER_ID);
        COMMENT_DTO_TEST.setCreatedAt(CREATED_AT);
        COMMENT_DTO_TEST.setPk(COMMENT_ID);
        COMMENT_DTO_TEST.setText(TEXT);
    }
}
