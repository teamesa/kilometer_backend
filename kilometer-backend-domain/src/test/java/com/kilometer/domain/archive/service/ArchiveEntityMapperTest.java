package com.kilometer.domain.archive.service;

import com.kilometer.domain.archive.ArchiveService;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ArchiveEntityMapperTest {

    @Autowired
    private ArchiveEntityMapper archiveEntityMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ArchiveService archiveService;
}
