package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.entity.Ads;

@Mapper(uses = UserMapper.class)
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(source = "pk", target = "id")
    @Mapping(source = "author", target = "author.id")
    public Ads adsDtoToAds(AdsDto adsDto);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "author.id", target = "author")
    public AdsDto adsToAdsDto(Ads ad);

    public Ads createAdsDtoToAds(CreateAdsDto createAdsDto);
}
