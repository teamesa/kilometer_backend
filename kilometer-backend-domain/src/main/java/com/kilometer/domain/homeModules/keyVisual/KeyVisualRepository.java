package com.kilometer.domain.homeModules.keyVisual;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeyVisualRepository extends JpaRepository<KeyVisual, Long> {
    @Query("select k from KeyVisual k where k.imageUrl <> '' order by k.id asc")
    List<KeyVisual> findAllOrderByIdAtAsc();
}