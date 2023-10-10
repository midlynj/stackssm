package com.example.stacks.repository;

import com.example.stacks.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRepository extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into user_friends (user_id, friend_id) VALUES (:userId, :friendId)", nativeQuery = true)
    void addFriendFromUser(@Param("userId") Long userID, @Param("friendId") Long friendId);
}
