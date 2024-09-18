package com.plj.hub.user.infrastructure.repository;

import com.plj.hub.user.domain.model.User;
import com.plj.hub.user.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaRepository extends org.springframework.data.jpa.repository.JpaRepository<User, Long>, UserRepository {
    boolean existsByUsername(String username);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    Optional<User> findByIdAndDeletedAtIsNullAndIsActivatedIsTrue(Long id);

    Page<User> findAllByHubIdAndDeletedAtIsNullAndIsActivatedIsTrue(UUID hubId, Pageable pageable);

    Page<User> findAll(Pageable pageable);
    Optional<User> findByIdAndDeletedAtIsNull(Long userId);
}
