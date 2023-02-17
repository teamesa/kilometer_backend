package com.kilometer.domain.item.dto;

public class ItemSummaryResponse {

    private final String title;
    private final String listImageUrl;
    private final boolean archiveWritten;
    private final Long archiveId;

    public static ItemSummaryResponse from(String title, String listImageUrl, boolean archiveWritten, Long archiveId) {
        return new ItemSummaryResponse(title, listImageUrl, archiveWritten, archiveId);
    }

    private ItemSummaryResponse(String title, String listImageUrl, boolean archiveWritten, Long archiveId) {
        this.title = title;
        this.listImageUrl = listImageUrl;
        this.archiveWritten = archiveWritten;
        this.archiveId = archiveId;
    }

    public String getTitle() {
        return title;
    }

    public String getListImageUrl() {
        return listImageUrl;
    }

    public boolean isArchiveWritten() {
        return archiveWritten;
    }
    public Long getArchiveId() {
        return archiveId;
    }
}
