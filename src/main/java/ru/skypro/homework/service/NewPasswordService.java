package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

@Service
public class NewPasswordService {
    private final UserRepository userRepository;

    public NewPasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean setPassword(NewPasswordDto newPasswordDto) {
        //Скорее всего нужно возвращать числовое значение (напр. int) соответствующее коду нужного http ответа
        //и в контроллере сопоставлять код ответа и возвращать соответствующий
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User currentUser = userRepository.findByEmail(username);

        if (currentUser == null) {
            return false;
        } else if (!currentUser.getPassword().equals(newPasswordDto.getCurrentPassword())){
            return false;
        } else {
            currentUser.setPassword(newPasswordDto.getNewPassword());
            userRepository.save(currentUser);
            return true;
        }
    }
}
