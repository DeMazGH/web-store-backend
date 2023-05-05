package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;

import java.util.List;

@Mapper(uses = AdsDtoMapper.class)
public interface ResponseWrapperAdsDtoMapper {

    ResponseWrapperAdsDtoMapper INSTANCE = Mappers.getMapper(ResponseWrapperAdsDtoMapper.class);

    default ResponseWrapperAdsDto toResponseWrapperAdsDto(List<Ads> ads) {
        return toResponseWrapperAdsDto(ads.size(), ads);
    }

    @Mapping(target = "results", source = "ads")
    @Mapping(target = "count", source = "count")
    ResponseWrapperAdsDto toResponseWrapperAdsDto(Integer count, List<Ads> ads);
}
