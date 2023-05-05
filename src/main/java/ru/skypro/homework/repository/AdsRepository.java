package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer> {

    Ads findById(int adId);

    List<Ads> findAllByAuthor(User author);
}
