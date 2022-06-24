package com.kilometer.domain.archive;

import com.kilometer.domain.archive.entity.Archive;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveRepositoryCustom {
    List<Archive> findAllByItemAndUser(ItemEntity item, User user);
}
