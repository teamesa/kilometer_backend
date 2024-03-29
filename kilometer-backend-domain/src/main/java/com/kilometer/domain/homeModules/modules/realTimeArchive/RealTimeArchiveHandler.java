package com.kilometer.domain.homeModules.modules.realTimeArchive;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.dto.RealTimeArchiveDto;
import com.kilometer.domain.archive.like.Like;
import com.kilometer.domain.archive.like.LikeRepository;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.realTimeArchive.dto.RealTimeArchiveResponse;
import com.kilometer.domain.user.User;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RealTimeArchiveHandler implements ModuleHandler {

    private final ArchiveRepository archiveRepository;
    private final LikeRepository likeRepository;

    @Override
    public boolean supports(final ModuleType moduleType) {
        return moduleType == ModuleType.REAL_TIME_ARCHIVE;
    }

    @Override
    public Optional<RealTimeArchiveResponse> generator(final ModuleParamDto paramDto) {
        List<RealTimeArchiveDto> realTimeArchiveDtos = archiveRepository.findTopFourVisibleAtItemArchivesWithImageUrl()
                .stream()
                .map(archive -> archiveRepository.findRealTimeArchive(archive.getId()))
                .map(this::combineVisitedPlaces)
                .map(realTimeArchiveDto -> doesLikeArchive(realTimeArchiveDto, paramDto))
                .collect(Collectors.toList());

        if (realTimeArchiveDtos.isEmpty()) {
            return Optional.empty();
        }

        ModuleDto moduleDto = paramDto.getModuleDto();
        return Optional.of(RealTimeArchiveResponse.builder()
                .topTitle(moduleDto.getUpperModuleTitle())
                .bottomTitle(moduleDto.getLowerModuleTitle())
                .archives(realTimeArchiveDtos)
                .build());
    }

    private RealTimeArchiveDto combineVisitedPlaces(List<RealTimeArchiveDto> realTimeArchiveDtos) {
        String visitedPlaces = realTimeArchiveDtos.stream()
                .map(RealTimeArchiveDto::getPlaceName)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));

        RealTimeArchiveDto realTimeArchiveDto = realTimeArchiveDtos.get(0);
        return RealTimeArchiveDto.builder()
                .archiveId(realTimeArchiveDto.getArchiveId())
                .likeCount(realTimeArchiveDto.getLikeCount())
                .starRating(realTimeArchiveDto.getStarRating())
                .updatedAt(realTimeArchiveDto.getUpdatedAt())
                .comment(realTimeArchiveDto.getComment())
                .imageUrl(realTimeArchiveDto.getImageUrl())
                .placeName(visitedPlaces)
                .title(realTimeArchiveDto.getTitle())
                .itemId(realTimeArchiveDto.getItemId())
                .userImageUrl(realTimeArchiveDto.getUserImageUrl())
                .userName(realTimeArchiveDto.getUserName())
                .build();

    }

    private RealTimeArchiveDto doesLikeArchive(RealTimeArchiveDto realTimeArchiveDto, ModuleParamDto moduleParamDto) {
        boolean isLiked = true;
        Optional<Like> like = findLike(realTimeArchiveDto, moduleParamDto);
        if (moduleParamDto.getUserId() == null || like.isEmpty() || !like.get().isLiked()) {
            isLiked = false;
        }
        return RealTimeArchiveDto.builder()
                .archiveId(realTimeArchiveDto.getArchiveId())
                .likeCount(realTimeArchiveDto.getLikeCount())
                .starRating(realTimeArchiveDto.getStarRating())
                .updatedAt(realTimeArchiveDto.getUpdatedAt())
                .comment(realTimeArchiveDto.getComment())
                .imageUrl(realTimeArchiveDto.getImageUrl())
                .placeName(realTimeArchiveDto.getPlaceName())
                .title(realTimeArchiveDto.getTitle())
                .itemId(realTimeArchiveDto.getItemId())
                .userImageUrl(realTimeArchiveDto.getUserImageUrl())
                .userName(realTimeArchiveDto.getUserName())
                .isLiked(isLiked)
                .build();

    }

    private Optional<Like> findLike(RealTimeArchiveDto realTimeArchiveDto, ModuleParamDto moduleParamDto) {
        ArchiveEntity archiveEntity = ArchiveEntity.builder()
                .id(realTimeArchiveDto.getArchiveId())
                .build();
        User user = User.builder()
                .id(moduleParamDto.getUserId())
                .build();
        return likeRepository.findByLikedArchiveEntityAndLikedUser(archiveEntity, user);
    }
}
