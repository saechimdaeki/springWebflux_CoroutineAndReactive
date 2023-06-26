package org.example.completablefuture.future;

import org.example.completablefuture.common.ArticleEntity;
import org.example.completablefuture.common.ImageEntity;
import org.example.completablefuture.common.User;
import org.example.completablefuture.common.UserEntity;
import org.example.completablefuture.future.repository.ArticleFutureRepository;
import org.example.completablefuture.future.repository.FollowFutureRepository;
import org.example.completablefuture.future.repository.ImageFutureRepository;
import org.example.completablefuture.future.repository.UserFutureRepository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class UserFutureService {
    private final UserFutureRepository userRepository;
    private final ArticleFutureRepository articleRepository;
    private final ImageFutureRepository imageRepository;
    private final FollowFutureRepository followRepository;

    public UserFutureService(UserFutureRepository userRepository, ArticleFutureRepository articleRepository, ImageFutureRepository imageRepository, FollowFutureRepository followRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.imageRepository = imageRepository;
        this.followRepository = followRepository;
    }

    public CompletableFuture<Optional<User>> getUserById(String id) {
        return userRepository.findById(id)
                .thenComposeAsync(this::getUser);
    }

    private CompletableFuture<Optional<User>> getUser(Optional<UserEntity> optionalUser) {
        try {

            if (optionalUser.isEmpty()) {
                return CompletableFuture.completedFuture(Optional.empty());
            }

            var userEntity = optionalUser.get();

            var imageFuture = imageRepository.findById(userEntity.getProfileImageId())
                    .thenApplyAsync(imageEntityOptional -> imageEntityOptional.map(imageEntity -> new ImageEntity(imageEntity.getId(),
                            imageEntity.getName(), imageEntity.getUrl())));


            var articles = articleRepository.findAllByUserId(userEntity.getId())
                    .thenApplyAsync(articleEntities -> articleEntities.stream().map(articleEntity -> new ArticleEntity(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent(), articleEntity.getUserId())).collect(Collectors.toList()));


            var followCount = followRepository.countByUserId(userEntity.getId());

            return CompletableFuture.allOf(imageFuture, articles, followCount)
                    .thenApply(v -> {
                        try {
                            var image = imageFuture.get();
                            var article = articles.get();
                            var follow = followCount.get();

                            return Optional.of(new User(userEntity.getId(), userEntity.getName(), userEntity.getAge(),
                                            image, article, follow));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
