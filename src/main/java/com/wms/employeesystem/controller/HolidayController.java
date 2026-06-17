package com.wms.employeesystem.controller;

import com.wms.employeesystem.entity.Holiday;
import com.wms.employeesystem.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @PostMapping("/save")
    public Holiday saveHoliday(@RequestBody Holiday holiday) {
        return holidayService.saveHoliday(holiday);
    }

    @GetMapping("/all")
    public List<Holiday> getAllHolidays() {
        return holidayService.getAllHolidays();
    }

    @PutMapping("/update/{id}")
    public Holiday updateHoliday(
            @PathVariable Long id,
            @RequestBody Holiday holiday) {

        return holidayService.updateHoliday(id, holiday);
    }
}