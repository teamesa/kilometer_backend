package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.paging.RequestPagingStatus;
import com.kilometer.domain.paging.ResponsePagingStatus;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchiveService {

    private final UserRepository userRepository;

    public void save(Long userId, ArchiveRequest archiveRequest) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + userId));

        Archive.builder()
            .build();


    }

    public ArchiveResponse findAllByItemId(Long itemId, RequestPagingStatus requestPagingStatus) {
        Preconditions.notNull(itemId, "id must not be null");
        Preconditions.notNull(requestPagingStatus,"page value must not be null");

        ResponsePagingStatus responsePagingStatus = ResponsePagingStatus.builder()
            .nextPage(1)
            .currentPage(0)
            .pageSize(2)
            .hasNext(true)
            .totalContentsCount(18)
            .currentContentsCount(0)
            .build();

        List<ArchiveInfo> archiveInfos = List.of();

        return ArchiveResponse.builder()
            .responsePagingStatus(responsePagingStatus)
            .avgStarRating(4.7)
            .archives(archiveInfos)
            .build();
    }

    public ArchiveResponse findAllByUserId(Long userId, RequestPagingStatus requestPagingStatus) {
        Preconditions.notNull(userId, "id must not be null");
        Preconditions.notNull(requestPagingStatus,"page value must not be null");

        return ArchiveResponse.builder().build();
    }

}
