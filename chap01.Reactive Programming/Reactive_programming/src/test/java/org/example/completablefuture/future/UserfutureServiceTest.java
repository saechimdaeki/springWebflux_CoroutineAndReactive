package org.example.completablefuture.future;

import org.example.completablefuture.common.User;
import org.example.completablefuture.future.repository.ArticleFutureRepository;
import org.example.completablefuture.future.repository.FollowFutureRepository;
import org.example.completablefuture.future.repository.ImageFutureRepository;
import org.example.completablefuture.future.repository.UserFutureRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserfutureServiceTest {
    UserFutureRepository userRepository = new UserFutureRepository();
    ArticleFutureRepository articleRepository = new ArticleFutureRepository();
    ImageFutureRepository imageRepository = new ImageFutureRepository();
    FollowFutureRepository followRepository = new FollowFutureRepository();

    UserFutureService userFutureService = new UserFutureService(
            userRepository, articleRepository, imageRepository, followRepository
    );


    @Test
    void testGetUser() throws InterruptedException, ExecutionException {
        //given
        String userId = "1234";

        //when

        Optional<User> optionalUser = userFutureService.getUserById(userId).get();

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