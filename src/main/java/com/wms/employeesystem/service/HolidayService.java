package com.wms.employeesystem.service;

import com.wms.employeesystem.entity.Holiday;
import com.wms.employeesystem.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    public Holiday saveHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    public Holiday updateHoliday(Long id, Holiday holiday) {

        Holiday existing = holidayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Holiday not found"));

        existing.setHolidayName(holiday.getHolidayName());
        existing.setHolidayDate(holiday.getHolidayDate());
        existing.setDescription(holiday.getDescription());

        return holidayRepository.save(existing);
    }
}