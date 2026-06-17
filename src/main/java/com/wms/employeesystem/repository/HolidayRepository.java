package com.wms.employeesystem.repository;

import com.wms.employeesystem.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}
