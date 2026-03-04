package com.gloriane.ecommerceappdemo.repository;

import com.gloriane.ecommerceappdemo.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByNickNameIgnoreCase(String nickName);
    List<UserProfile> findByPhoneNumberContaining(String partialPhoneNumber);
    List<UserProfile> findByBioIsNotNull();
    List<UserProfile> findByNickNameStartingWithIgnoreCase(String prefix);
}
