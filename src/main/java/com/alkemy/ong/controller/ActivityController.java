package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.service.ActivityService;
<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

>>>>>>> 0cb91f87437c1d78ab4af39dfd701204d3d61d02
@RestController
@RequestMapping("/activities")
public class ActivityController {

<<<<<<< HEAD
    private final ActivityService activityService;

    @PostMapping("/create")
    public ResponseEntity<ActivityDto> addActivity(@Validated @RequestBody ActivityDto activityDto) {
        ActivityDto savedActivity = activityService.addActivity(activityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
    }

=======
    @Autowired
    private ActivityService activityService;

    //Update Activity only for ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<ActivityDto> updateActivity(
            @PathVariable(name="id", required=true)
            String id, @RequestBody ActivityDto activityDto){
        return ResponseEntity.ok(activityService.update(id,activityDto));
    }
>>>>>>> 0cb91f87437c1d78ab4af39dfd701204d3d61d02
}
