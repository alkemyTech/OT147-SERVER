package com.alkemy.ong.seeder;


import com.alkemy.ong.entity.ActivityEntity;
import com.alkemy.ong.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ActivitiesSeeder implements CommandLineRunner {

    @Autowired
    private ActivityRepository activityRepository;

    private static final String image = "https://foo.jpg";

    @Override
    public void run(String... args) throws Exception {
        loadActivities();
    }
    //if activities equals 0
    private void loadActivities() {
        if (activityRepository.count() == 0) {
            loadActivitiesSeed();
        }
    }

    private void loadActivitiesSeed() {
        activityRepository.save(buildActivity("School and family support", "Family support", image));
        activityRepository.save(buildActivity("Elementary school support", "Elementary school", image));
        activityRepository.save(buildActivity("Middle school support", "Middle school", image));
        activityRepository.save(buildActivity("Hight school support", "Hight school", image));
        activityRepository.save(buildActivity("Tutorials", "Tutorials", image));
    }

    private ActivityEntity buildActivity(String name, String content, String image) {
        return new ActivityEntity (name, content, image);
    }
}
