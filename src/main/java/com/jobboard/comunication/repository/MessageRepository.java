package com.jobboard.comunication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jobboard.comunication.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message , Long> {
	@Query("SELECT m FROM Message m WHERE " + 
			"(m.senderId = :userA AND m.receiverId = :userB) OR " +
			"(m.senderId = :userB AND m.receiverId = :userA) " +
			"ORDER BY m.createdAt ASC")
	List<Message> findChatHistory(@Param("userA") Long userA, @Param("userB") Long userB);
	
	@Modifying
	@Query("UPDATE Message m SET m.isRead = true " +
			"WHERE m.senderId = :sId AND m.receiverId = :rId " +
			"AND m.isRead = false")
	void markChatAsRead(@Param("rId") Long senderId, @Param("sId") Long receiverId);
}
