package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;

import java.util.List;

@Mapper(componentModel = "spring", uses = AdsMapper.class)
public interface ResponseWrapperAdsDtoMapper {

    default ResponseWrapperAdsDto toResponseWrapperAdsDto(List<Ads> ads) {
        return toResponseWrapperAdsDto(ads.size(), ads);
    }

    @Mapping(target = "results", source = "ads")
    @Mapping(target = "count", source = "count")
    ResponseWrapperAdsDto toResponseWrapperAdsDto(Integer count, List<Ads> ads);
}
