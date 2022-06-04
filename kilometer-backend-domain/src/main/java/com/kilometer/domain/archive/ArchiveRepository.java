package com.kilometer.domain.archive;

import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveRepositoryCustom {
    List<Archive> findAllByItemAndUser(ItemEntity item, User user);
}
