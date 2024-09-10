package com.plj.hub.user.domain.repository;

import com.plj.hub.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(Long userId);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    User save(User user);

    boolean existsByUsername(String username);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    boolean existsBySlackId(String slackId);

    Page<User> findAllByHubIdAndDeletedAtIsNull(UUID hubId, Pageable pageable);

    Page<User> findAll(Pageable pageable);
}
