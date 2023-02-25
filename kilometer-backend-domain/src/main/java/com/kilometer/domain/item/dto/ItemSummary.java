package com.kilometer.domain.item.dto;

public class ItemSummary {
    private String title;
    private String listImageUrl;
    private boolean archiveWritten;
    private Long archiveId;

    public ItemSummary() {
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
