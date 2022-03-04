package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    //Update Activity only for ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<ActivityDto> updateActivity(
            @PathVariable(name="id", required=true)
            String id, @RequestBody ActivityDto activityDto){
        return ResponseEntity.ok(activityService.update(id,activityDto));
    }
}
