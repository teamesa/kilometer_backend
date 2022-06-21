package com.kilometer.domain.archive.repository;

import com.kilometer.domain.archive.entity.Archive;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveRepositoryCustom {

    List<Archive> findAllByItemAndUser(ItemEntity item, User user);

    Optional<Archive> findByItemIdAndUserId(Long itemId, Long userId);
}