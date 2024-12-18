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
    @Query(value = "INSERT INTO user_friends (user_id,friend_id) VALUES(:userId, :friendId)",nativeQuery = true)
    void addU(@Param("userId") Long userId, @Param("friendId")Long friendId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_friends (user_id,friend_id) VALUES(:friendId,:userId)",nativeQuery = true)
    void addF(@Param("userId") Long userId, @Param("friendId")Long friendId);

    default void addFriends(Long userId, Long friendId) {
        System.out.println("Adding user from database");
        addU(userId, friendId);
        addF(userId, friendId);
    }

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_friends WHERE user_id = :userId AND friend_id = :friendId",nativeQuery = true)
    void removeU(@Param("userId") Long userId, @Param("friendId")Long friendId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_friends WHERE user_id = :friendId AND friend_id = :userId",nativeQuery = true)
    void removeF(@Param("userId") Long userId, @Param("friendId")Long friendId);

    default void removeFriends(Long userId, Long friendId) {
        System.out.println("Removing user from database");
        removeU(userId, friendId);
        removeF(userId, friendId);
    }

}
