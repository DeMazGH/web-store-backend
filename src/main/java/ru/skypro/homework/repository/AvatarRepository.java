package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.User;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {

    Avatar findByUserId(int userId);

    Optional<Avatar> findByUser(User user);
}
