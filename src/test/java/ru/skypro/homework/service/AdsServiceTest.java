package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class AdsServiceTest {

    @Mock
    private AdsRepository adsRepository;

    @Mock
    private UserRepository userRepository;

    private AdsService adsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        adsService = new AdsService(adsRepository, userRepository);
    }

    @Test
    public void testGetAllAds(){
        Ads ads1 = new Ads();
        Ads ads2 = new Ads();
        List<Ads> adsList = new ArrayList<>();
        adsList.add(ads1);
        adsList.add(ads2);

        when(adsRepository.findAll()).thenReturn(adsList);

        ResponseWrapperAdsDto responseWrapperAdsDto = adsService.getAllAds();

        assertNotNull(responseWrapperAdsDto);
    }

    @Test
    public void testGetInfoAboutAd_Null(){
        when(adsRepository.findById(anyInt())).thenReturn(null);

        Ads ad = adsRepository.findById(0);

        assertNull(ad);
    }

    @Test
    public void testGetInfoAboutAd_Success(){
        User author = new User();
        author.setId(1);
        author.setEmail("john.doe@example.com");
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setPhone("1234567890");
        author.setImage("path/to/user/image.jpg");
        author.setPassword("password123");
        author.setRole(Role.USER);

        Ads ad = new Ads();
        ad.setId(1);
        ad.setPrice(1000);
        ad.setDescription("This is a great product.");
        ad.setTitle("Awesome Product");
        ad.setAuthor(author);
        ad.setImage("path/to/ad/image.jpg");

        FullAdsDto fullAdsDtoExpected = new FullAdsDto();
        fullAdsDtoExpected.setImage("path/to/ad/image.jpg");
        fullAdsDtoExpected.setId(1);
        fullAdsDtoExpected.setAuthorFirstName("John");
        fullAdsDtoExpected.setAuthorLastName("Doe");
        fullAdsDtoExpected.setEmail("john.doe@example.com");
        fullAdsDtoExpected.setPhone("1234567890");
        fullAdsDtoExpected.setPrice(1000);
        fullAdsDtoExpected.setTitle("Awesome Product");
        fullAdsDtoExpected.setDescription("This is a great product.");

        when(adsRepository.findById(1)).thenReturn(ad);

        FullAdsDto fullAdsDto = adsService.getInfoAboutAd(1);

        assertNotNull(ad);
        assertEquals(fullAdsDtoExpected,fullAdsDto);
    }


}

