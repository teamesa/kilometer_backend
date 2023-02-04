package com.kilometer.domain.homeModules.modules.realTimeArchive;

import com.kilometer.domain.archive.Archive;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.archive.exception.ArchiveNotFoundException;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import java.util.Comparator;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class RealTimeArchiveHandler implements ModuleHandler {

    private static final int MAX_ARCHIVES = 4;

    private ArchiveRepository archiveRepository;

    @Override
    public boolean supports(final ModuleType moduleType) {
        return moduleType == ModuleType.REAL_TIME_ARCHIVE;
    }

    @Override
    public Object generator(final ModuleParamDto paramDto) throws RuntimeException {
        return archiveRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Archive::getUpdatedAt))
                .map(archive -> archiveRepository.findRealTimeArchive(archive.getId())
                        .orElseThrow(ArchiveNotFoundException::new))
                .filter(this::hasArchiveImage)
                .limit(MAX_ARCHIVES)
                .map(realTimeArchiveDto -> doesLikeArchive(realTimeArchiveDto, paramDto))
                .collect(Collectors.toList());
    }

    private boolean hasArchiveImage(RealTimeArchiveDto realTimeArchiveDto) {
        String imageUrl = realTimeArchiveDto.getImageUrl();
        return imageUrl != null && !imageUrl.isBlank();
    }

    private RealTimeArchiveDto doesLikeArchive(RealTimeArchiveDto realTimeArchiveDto, ModuleParamDto moduleParamDto) {
        if (moduleParamDto.getUserId().equals(realTimeArchiveDto.getUserId())) {
            return realTimeArchiveDto;
        }
        return RealTimeArchiveDto.builder()
                .likeCount(realTimeArchiveDto.getLikeCount())
                .starRating(realTimeArchiveDto.getStarRating())
                .updatedAt(realTimeArchiveDto.getUpdatedAt())
                .comment(realTimeArchiveDto.getComment())
                .imageUrl(realTimeArchiveDto.getImageUrl())
                .placeName(realTimeArchiveDto.getPlaceName())
                .title(realTimeArchiveDto.getTitle())
                .itemId(realTimeArchiveDto.getItemId())
                .userId(realTimeArchiveDto.getUserId())
                .userImageUrl(realTimeArchiveDto.getUserImageUrl())
                .userName(realTimeArchiveDto.getUserName())
                .isLiked(false)
                .build();
    }
}
