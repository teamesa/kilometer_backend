package com.kilometer.domain.homeModules.modules.realTimeArchive;

import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.like.dto.ArchiveLike;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RealTimeArchiveHandler implements ModuleHandler {

    private ArchiveRepository archiveRepository;

    @Override
    public boolean supports(final ModuleType moduleType) {
        return moduleType == ModuleType.REAL_TIME_ARCHIVE;
    }

    @Override
    public Object generator(final ModuleParamDto paramDto) throws RuntimeException {
        return null;
    }
}
