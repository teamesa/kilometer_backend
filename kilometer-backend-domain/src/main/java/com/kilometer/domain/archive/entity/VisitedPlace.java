package com.kilometer.domain.archive.entity;

import com.kilometer.domain.archive.PlaceType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "visited_place")
public class VisitedPlace {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "archiveId")
    private Archive archive;

    @Enumerated(value = EnumType.STRING)
    private PlaceType placeType;

    private String placeName;

    private String address;

    private String roadAddress;

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
