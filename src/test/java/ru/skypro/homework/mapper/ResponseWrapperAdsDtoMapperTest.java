package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.homework.ConstantsTest.*;

class ResponseWrapperAdsDtoMapperTest {

    @Test
    void toResponseWrapperAdsDto() {
        ResponseWrapperAdsDto actual = ResponseWrapperAdsDtoMapper.INSTANCE.toResponseWrapperAdsDto(ADS_LIST);

        assertThat(actual).isNotNull();
        assertThat(actual.getCount()).isEqualTo(ADS_LIST.size());
        assertThat(actual.getResults()).isEqualTo(ADS_DTO_LIST);
    }
}