package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;

@Mapper(uses = UserMapper.class)
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(source = "pk", target = "id")
    @Mapping(source = "author", target = "author.id")
    Ads adsDtoToAds(AdsDto adsDto);

    //здесь будет маппинг для image: в БД хранится путь к картинке в файловой системе,
    // а отдавать надо эндпоинт, по которому можно получить будет байты картинки
    @Mapping(source = "id", target = "pk")
    @Mapping(source = "author.id", target = "author")
    AdsDto adsToAdsDto(Ads ad);

    Ads createAdsDtoToAds(CreateAdsDto createAdsDto);

    //здесь будет маппинг для image: в БД хранится путь к картинке в файловой системе,
    // а отдавать надо эндпоинт, по которому можно получить будет байты картинки
    @Mapping(target = "authorFirstName", source = "ad.author.firstName")
    @Mapping(target = "authorLastName", source = "ad.author.lastName")
    @Mapping(target = "email", source = "ad.author.email")
    @Mapping(target = "phone", source = "ad.author.phone")
    FullAdsDto adToFullAdsDto(Ads ad);
}
