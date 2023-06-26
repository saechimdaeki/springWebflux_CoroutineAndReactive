package org.example.completablefuture.blocking;

import org.example.completablefuture.common.ArticleEntity;
import org.example.completablefuture.common.ImageEntity;
import org.example.completablefuture.common.User;
import org.example.completablefuture.blocking.repository.ArticleRepository;
import org.example.completablefuture.blocking.repository.FollowRepository;
import org.example.completablefuture.blocking.repository.ImageRepository;
import org.example.completablefuture.blocking.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

public class UserBlockingService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;
    private final FollowRepository followRepository;

    public UserBlockingService(UserRepository userRepository, ArticleRepository articleRepository, ImageRepository imageRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.imageRepository = imageRepository;
        this.followRepository = followRepository;
    }

    public Optional<User> getUserById(String id) throws InterruptedException {
        return userRepository.findById(id)
                .map(user -> {
                    var image = imageRepository.findById(user.getProfileImageId())
                            .map(imageEntity -> new ImageEntity(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl()));


                    var articles = articleRepository.findAllByUserId(user.getId())
                            .stream().map(articleEntity -> new ArticleEntity(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent(), articleEntity.getUserId())).collect(Collectors.toList());


                    var followCount = followRepository.countByUserId(user.getId());

                    return new User(user.getId(), user.getName(), user.getAge(), image, articles, followCount);
                });
    }
}
