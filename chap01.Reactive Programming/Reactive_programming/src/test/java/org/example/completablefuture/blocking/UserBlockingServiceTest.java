package org.example.completablefuture.blocking;

import org.example.completablefuture.common.User;
import org.example.completablefuture.blocking.repository.ArticleRepository;
import org.example.completablefuture.blocking.repository.FollowRepository;
import org.example.completablefuture.blocking.repository.ImageRepository;
import org.example.completablefuture.blocking.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserBlockingServiceTest {
    UserRepository userRepository = new UserRepository();
    ArticleRepository articleRepository = new ArticleRepository();
    ImageRepository imageRepository = new ImageRepository();
    FollowRepository followRepository = new FollowRepository();

    UserBlockingService userBlockingService = new UserBlockingService(
            userRepository, articleRepository, imageRepository, followRepository
    );


    @Test
    void testGetUser() throws InterruptedException {
        //given
        String userId = "1234";

        //when

        Optional<User> optionalUser = userBlockingService.getUserById(userId);

        //then
        assertFalse(optionalUser.isEmpty());
        var user = optionalUser.get();
        assertEquals(user.getName(),"junseong");
        assertEquals(user.getAge(),30);

        assertFalse(user.getImage().isEmpty());
        var image = user.getImage().get();
        assertEquals(image.getName(),"profileImage");
        assertEquals(image.getUrl(),"/images/profileImage.jpg");

        assertEquals(2,user.getArticles().size());

    }


}