package com.kilometer.domain.item.dto;

public class ItemSummaryResponse {

    private final String title;
    private final String listImageUrl;
    private final boolean archiveWritten;

    public static ItemSummaryResponse from(String title, String listImageUrl, boolean archiveWritten) {
        return new ItemSummaryResponse(title, listImageUrl, archiveWritten);
    }

    private ItemSummaryResponse(String title, String listImageUrl, boolean archiveWritten) {
        this.title = title;
        this.listImageUrl = listImageUrl;
        this.archiveWritten = archiveWritten;
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
}
