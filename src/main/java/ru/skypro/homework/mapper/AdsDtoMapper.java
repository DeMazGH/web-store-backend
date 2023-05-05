package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;

@Mapper(uses = UserMapper.class)
public interface AdsDtoMapper {

    @Mapping(source = "pk", target = "id")
    @Mapping(source = "author", target = "author.id")
    @Mapping(source = "image", target = "author.image")
    public Ads adsDtoToAds(AdsDto adsDto);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "author.image", target = "image")
    public AdsDto adsToAdsDto(Ads ad);
}
