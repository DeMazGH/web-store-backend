package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("test@example.com", null));
        SecurityContextHolder.setContext(securityContext);
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

    @Test
    public void testUpdateAd_Success(){
        User author = new User();
        author.setId(1);
        author.setEmail("john.doe@example.com");
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setPhone("1234567890");
        author.setImage("path/to/user/image.jpg");
        author.setPassword("password123");
        author.setRole(Role.USER);

        Ads oldAdData = new Ads();
        oldAdData.setId(1);
        oldAdData.setPrice(1000);
        oldAdData.setDescription("This is a great product.");
        oldAdData.setTitle("Awesome Product");
        oldAdData.setAuthor(author);
        oldAdData.setImage("path/to/ad/image.jpg");

        CreateAdsDto newAdData = new CreateAdsDto();
        newAdData.setDescription("New description");
        newAdData.setPrice(500);
        newAdData.setTitle("New Title");

        Ads updatedAd = oldAdData;
        updatedAd.setDescription(newAdData.getDescription());
        updatedAd.setPrice(newAdData.getPrice());
        updatedAd.setTitle(newAdData.getTitle());

        AdsDto adsDtoExpected = new AdsDto();
        adsDtoExpected.setAuthor(1);
        adsDtoExpected.setImage("path/to/ad/image.jpg");
        adsDtoExpected.setPk(1);
        adsDtoExpected.setPrice(500);
        adsDtoExpected.setTitle("New Title");

        when(adsRepository.save(oldAdData)).thenReturn(updatedAd);
        when(adsRepository.findById(1)).thenReturn(oldAdData);

        AdsDto adsDto = adsService.updateAd(1,newAdData);

        assertNotNull(adsDto);
        assertEquals(adsDtoExpected,adsDto);
    }

    @Test
    public void testUpdateAd_Null(){
        CreateAdsDto newAdData = new CreateAdsDto();
        newAdData.setDescription("New description");
        newAdData.setPrice(500);
        newAdData.setTitle("New Title");

        when(adsRepository.findById(1)).thenReturn(null);

        AdsDto adsDto = adsService.updateAd(1,newAdData);

        assertNull(adsDto);
    }

    @Test
    public void testGetMyAds(){
        List<Ads> adsList = new ArrayList<>();

        when(adsRepository.findAllByAuthor(any())).thenReturn(adsList);

        ResponseWrapperAdsDto responseWrapperAdsDto = adsService.getMyAds();

        assertNotNull(responseWrapperAdsDto);
    }
}

