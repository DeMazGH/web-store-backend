package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.homework.ConstantsTest.*;

@SpringBootTest
class ResponseWrapperAdsDtoMapperTest {

    @SpyBean
    private ResponseWrapperAdsDtoMapper responseWrapperAdsDtoMapper;

    @Test
    void toResponseWrapperAdsDto() {
        ResponseWrapperAdsDto actual = responseWrapperAdsDtoMapper.toResponseWrapperAdsDto(ADS_LIST);

        assertThat(actual).isNotNull();
        assertThat(actual.getCount()).isEqualTo(ADS_LIST.size());
        assertThat(actual.getResults()).isEqualTo(ADS_DTO_LIST);
    }
}