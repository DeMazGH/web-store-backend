package ru.skypro.homework;

import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class ConstantsTest {

    public static final Integer USER_ID = 1;
    public static final String EMAIL = "user@gmail.com";
    public static final String FIRST_NAME = "first name";
    public static final String LAST_NAME = "last name";
    public static final String PHONE = "+79876543211";
    public static final Avatar AVATAR = new Avatar();
    public static final String PASSWORD = "password";
    public static final Role ROLE = Role.USER;

    public static final User USER_TEST = new User(USER_ID, EMAIL, FIRST_NAME, LAST_NAME, PHONE, AVATAR, PASSWORD, ROLE);


    public static final Integer AVATAR_ID_1 = 1;
    public static final String AVATAR_FILE_PATH = EMAIL + ".jpg";

    static {
        AVATAR.setId(AVATAR_ID_1);
        AVATAR.setFilePath(AVATAR_FILE_PATH);
        AVATAR.setUser(USER_TEST);
    }


    public static final Integer ADS_ID_1 = 1;
    public static final Integer ADS_ID_2 = 2;
    public static final Integer PRICE = 99;
    public static final String DESCRIPTION = "description";
    public static final String TITLE = "title";
    public static final Image AD_IMAGE = new Image();
    public static final List<Comment> COMMENT_LIST = new ArrayList<>();

    public static final Ads ADS_TEST_1 = new Ads(ADS_ID_1, PRICE, DESCRIPTION, TITLE, USER_TEST, AD_IMAGE, COMMENT_LIST);
    public static final Ads ADS_TEST_2 = new Ads(ADS_ID_2, PRICE, DESCRIPTION, TITLE, USER_TEST, AD_IMAGE, COMMENT_LIST);


    public static final Integer IMAGE_ID_1 = 1;
    public static final String IMAGE_FILE_PATH = EMAIL + ".jpg";

    static {
        AD_IMAGE.setId(IMAGE_ID_1);
        AD_IMAGE.setFilePath(IMAGE_FILE_PATH);
        AD_IMAGE.setAd(ADS_TEST_1);
    }


    public static final Integer COMMENT_ID_1 = 1;
    public static final Integer COMMENT_ID_2 = 2;
    public static final String TEXT = "text";
    public static final LocalDateTime CREATED_AT_LOCAL_DATE_TIME
            = LocalDateTime.of(2000, 10, 10, 10, 10);
    public static final long CREATED_AT_LONG = CREATED_AT_LOCAL_DATE_TIME
            .toInstant(ZoneOffset.systemDefault().getRules().getOffset(CREATED_AT_LOCAL_DATE_TIME)).toEpochMilli();

    public static final Comment COMMENT_TEST_1 = new Comment(COMMENT_ID_1, TEXT, CREATED_AT_LOCAL_DATE_TIME, ADS_TEST_1, USER_TEST);
    public static final Comment COMMENT_TEST_2 = new Comment(COMMENT_ID_2, TEXT, CREATED_AT_LOCAL_DATE_TIME, ADS_TEST_1, USER_TEST);


    public static final UserDto USER_DTO_TEST = new UserDto();
    public static final RegisterReq REGISTER_REQ_TEST = new RegisterReq();

    public static final AdsDto ADS_DTO_TEST_1 = new AdsDto();
    public static final AdsDto ADS_DTO_TEST_2 = new AdsDto();
    public static final CreateAdsDto CREATE_ADS_DTO_TEST = new CreateAdsDto();
    public static final FullAdsDto FULL_ADS_DTO_TEST = new FullAdsDto();

    public static final CommentDto COMMENT_DTO_TEST_1 = new CommentDto();
    public static final CommentDto COMMENT_DTO_TEST_2 = new CommentDto();
    public static final CreateCommentDto CREATE_COMMENT_DTO_TEST = new CreateCommentDto();
    public static final List<CommentDto> COMMENT_DTO_LIST = new ArrayList<>();

    public static final List<Ads> ADS_LIST = new ArrayList<>();
    public static final List<AdsDto> ADS_DTO_LIST = new ArrayList<>();


    public static final String USER_AVATAR_API_1 = "/images/avatar/1";
    public static final String AD_IMAGE_API_1 = "/images/ads/1";

    static {
        USER_DTO_TEST.setId(USER_ID);
        USER_DTO_TEST.setEmail(EMAIL);
        USER_DTO_TEST.setFirstName(FIRST_NAME);
        USER_DTO_TEST.setLastName(LAST_NAME);
        USER_DTO_TEST.setPhone(PHONE);
        USER_DTO_TEST.setImage(USER_AVATAR_API_1);

        REGISTER_REQ_TEST.setUsername(EMAIL);
        REGISTER_REQ_TEST.setPassword(PASSWORD);
        REGISTER_REQ_TEST.setFirstName(FIRST_NAME);
        REGISTER_REQ_TEST.setLastName(LAST_NAME);
        REGISTER_REQ_TEST.setPhone(PHONE);
        REGISTER_REQ_TEST.setRole(ROLE);

        COMMENT_DTO_TEST_1.setAuthor(USER_ID);
        COMMENT_DTO_TEST_1.setAuthorImage(USER_AVATAR_API_1);
        COMMENT_DTO_TEST_1.setAuthorFirstName(FIRST_NAME);
        COMMENT_DTO_TEST_1.setCreatedAt(CREATED_AT_LONG);
        COMMENT_DTO_TEST_1.setPk(COMMENT_ID_1);
        COMMENT_DTO_TEST_1.setText(TEXT);

        COMMENT_DTO_TEST_2.setAuthor(USER_ID);
        COMMENT_DTO_TEST_2.setAuthorImage(USER_AVATAR_API_1);
        COMMENT_DTO_TEST_2.setAuthorFirstName(FIRST_NAME);
        COMMENT_DTO_TEST_2.setCreatedAt(CREATED_AT_LONG);
        COMMENT_DTO_TEST_2.setPk(COMMENT_ID_2);
        COMMENT_DTO_TEST_2.setText(TEXT);

        COMMENT_LIST.add(COMMENT_TEST_1);
        COMMENT_LIST.add(COMMENT_TEST_2);

        COMMENT_DTO_LIST.add(COMMENT_DTO_TEST_1);
        COMMENT_DTO_LIST.add(COMMENT_DTO_TEST_2);

        CREATE_COMMENT_DTO_TEST.setText(TEXT);

        ADS_DTO_TEST_1.setAuthor(USER_ID);
        ADS_DTO_TEST_1.setImage(AD_IMAGE_API_1);
        ADS_DTO_TEST_1.setPk(ADS_ID_1);
        ADS_DTO_TEST_1.setPrice(PRICE);
        ADS_DTO_TEST_1.setTitle(TITLE);

        ADS_DTO_TEST_2.setAuthor(USER_ID);
        ADS_DTO_TEST_2.setImage(AD_IMAGE_API_1);
        ADS_DTO_TEST_2.setPk(ADS_ID_2);
        ADS_DTO_TEST_2.setPrice(PRICE);
        ADS_DTO_TEST_2.setTitle(TITLE);

        CREATE_ADS_DTO_TEST.setDescription(DESCRIPTION);
        CREATE_ADS_DTO_TEST.setPrice(PRICE);
        CREATE_ADS_DTO_TEST.setTitle(TITLE);

        FULL_ADS_DTO_TEST.setId(ADS_ID_1);
        FULL_ADS_DTO_TEST.setAuthorFirstName(FIRST_NAME);
        FULL_ADS_DTO_TEST.setAuthorLastName(LAST_NAME);
        FULL_ADS_DTO_TEST.setDescription(DESCRIPTION);
        FULL_ADS_DTO_TEST.setEmail(EMAIL);
        FULL_ADS_DTO_TEST.setImage(AD_IMAGE_API_1);
        FULL_ADS_DTO_TEST.setPhone(PHONE);
        FULL_ADS_DTO_TEST.setPrice(PRICE);
        FULL_ADS_DTO_TEST.setTitle(TITLE);

        ADS_LIST.add(ADS_TEST_1);
        ADS_LIST.add(ADS_TEST_2);

        ADS_DTO_LIST.add(ADS_DTO_TEST_1);
        ADS_DTO_LIST.add(ADS_DTO_TEST_2);
    }
}
