package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/activities")
public class ActivityController {


    @Autowired
    private ActivityService activityService;

    //Create Activity only for ADMIN
    @PostMapping
    public ResponseEntity<ActivityDto> create(@Validated @RequestBody ActivityDto activityDto) {
        ActivityDto savedActivity = activityService.addActivity(activityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
    }


    //Update Activity only for ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<ActivityDto> updateActivity(
            @PathVariable(name = "id", required = true)
                    String id, @RequestBody ActivityDto activityDto) {
        return ResponseEntity.ok(activityService.update(id, activityDto));
    }
}
