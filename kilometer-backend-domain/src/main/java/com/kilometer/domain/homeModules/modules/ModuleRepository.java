package com.kilometer.domain.homeModules.modules;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findAllByOrderByExposureOrderNumber();
}
