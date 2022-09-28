package com.charles.knightonline.repository;

import com.charles.knightonline.enums.StatusEnum;
import com.charles.knightonline.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select (count(u) > 0) from UserEntity u where u.email = ?1")
    Boolean existsByEmail(String email);

    @Query("select (count(u) > 0) from UserEntity u where upper(u.name) = upper(?1)")
    Boolean existsByNameIgnoreCase(String name);

    @Query("select u from UserEntity u")
    @NonNull
    Page<UserEntity> findAll(@NonNull Pageable pageable);

    @Query("select u from UserEntity u where u.email = ?1")
    Optional<UserEntity> findByEmail(String email);

    @Query("select u from UserEntity u where u.email = ?1 and u.status <> ?2")
    Optional<UserEntity> findByEmailAndStatusNot(String email, StatusEnum status);

    @Query("FROM UserEntity u where lower(u.name) like %:searchTerm%")
    Page<UserEntity> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}
