package com.project.crowdfunding.Repository;

import com.project.crowdfunding.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserUserId(Long userId);

    List<Notification> findByUserUserIdAndReadFalse(Long userId);
}
