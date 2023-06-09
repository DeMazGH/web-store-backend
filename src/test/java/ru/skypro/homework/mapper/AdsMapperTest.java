package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.entity.Ads;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.homework.ConstantsTest.*;

@SpringBootTest
class AdsMapperTest {

    @SpyBean
    private AdsMapper adsMapper;

    @Test
    void shouldMapAdsToAdsDto() {
        AdsDto actual = adsMapper.adsToAdsDto(ADS_TEST_1);

        assertThat(actual).isNotNull();
        assertThat(actual.getAuthor()).isEqualTo(USER_ID);
        assertThat(actual.getImage()).isEqualTo(AD_IMAGE_API_1);
        assertThat(actual.getPk()).isEqualTo(ADS_ID_1);
        assertThat(actual.getPrice()).isEqualTo(PRICE);
        assertThat(actual.getTitle()).isEqualTo(TITLE);
    }

    @Test
    void createAdsDtoToAds() {
        Ads actual = adsMapper.createAdsDtoToAds(CREATE_ADS_DTO_TEST);

        assertThat(actual).isNotNull();
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getPrice()).isEqualTo(PRICE);
        assertThat(actual.getTitle()).isEqualTo(TITLE);
    }

    @Test
    void adToFullAdsDto() {
        FullAdsDto actual = adsMapper.adToFullAdsDto(ADS_TEST_1);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(ADS_ID_1);
        assertThat(actual.getAuthorFirstName()).isEqualTo(USER_TEST.getFirstName());
        assertThat(actual.getAuthorLastName()).isEqualTo(USER_TEST.getLastName());
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getEmail()).isEqualTo(USER_TEST.getEmail());
        assertThat(actual.getImage()).isEqualTo(AD_IMAGE_API_1);
        assertThat(actual.getPhone()).isEqualTo(USER_TEST.getPhone());
        assertThat(actual.getPrice()).isEqualTo(PRICE);
        assertThat(actual.getTitle()).isEqualTo(TITLE);
    }
}