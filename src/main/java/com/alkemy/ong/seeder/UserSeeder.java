package com.alkemy.ong.seeder;

import com.alkemy.ong.entity.RoleEntity;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class UserSeeder implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private static final String PHOTO ="default.jpg";


    @Override
    public void run(String... args) throws Exception {
        loadUser();
        loadRole();
    }

    //create roles if they don't exist
    private void loadRole(){
        if(roleRepository.count() == 0){
            loadRoleSeed();
        }
    }

    private void loadRoleSeed() {
        roleRepository.save(buildRole("ADMIN",
                "All has all the privileges of the roles"));
        roleRepository.save(buildRole("USER",
                "Privileges limited to only modifying and viewing your data"));
    }
    private RoleEntity buildRole(String name, String description){
        return  new RoleEntity(name,description);
    }


    private void loadUser() {
        if(userRepository.count() == 0){
            loadUserSeed();
        }
    }

    RoleEntity roleEntityAdmin = roleRepository.findByName("ADMIN");
    RoleEntity roleEntityUser = roleRepository.findByName("USER");


    private void loadUserSeed() {
        //add Admin Users
        userRepository.save(buildUser("Sansone","Rickardes","srickardes0@facebook.com","OTLZQDL",PHOTO, roleEntityAdmin ));
        userRepository.save(buildUser("Sileas","Gadesby","sgadesby1@nsw.gov.au","LWgwvkv",PHOTO, roleEntityAdmin ));
        userRepository.save(buildUser("Raynor","Creese","rcreese2@blog.com","6r0Jfdk",PHOTO, roleEntityAdmin ));
        userRepository.save(buildUser("Madel","Charteris","mcharteris3@themeforest.net","OGfiQ5zb",PHOTO, roleEntityAdmin ));
        userRepository.save(buildUser("Cedric","Meyrick","cmeyrick4@amazon.co.jp","2myWZWtb6Am",PHOTO, roleEntityAdmin ));
        userRepository.save(buildUser("Fitzgerald","Mersh","fmersh5@pcworld.com","D1PH35K",PHOTO, roleEntityAdmin ));
        userRepository.save(buildUser("Johnath","Origan","jorigan5@storify.com","R2oxqEfElMln",PHOTO, roleEntityAdmin ));
        userRepository.save(buildUser("Donni","Extance","dextance6@wisc.edu","wTahWubX",PHOTO, roleEntityAdmin ));
        userRepository.save(buildUser("Corbie","Lapsley","clapsley7@microsoft.com","qKpql1Ounzwo",PHOTO, roleEntityAdmin ));
        userRepository.save(buildUser("Leisha","Belin","lbelin8@pcworld.com","w77p77wGS",PHOTO, roleEntityAdmin ));

        //add Users
        userRepository.save(buildUser("Misha","Fossord","mfossord9@senate.gov","3q3MgFh2P5",PHOTO, roleEntityUser ));
        userRepository.save(buildUser("Cristian","Inkpen","cinkpen0@joomla.org","4VHcXxp86B",PHOTO, roleEntityUser ));
        userRepository.save(buildUser("Cristiano","Foston","cfoston1@japanpost.jp","9wf9KkPk",PHOTO, roleEntityUser ));
        userRepository.save(buildUser("Odele","Clynmans","oclynmans2@bravesites.com","DTUMZGlDj",PHOTO, roleEntityUser ));
        userRepository.save(buildUser("Will","MacRonald","wmacronald3@jalbum.net","KW1LpoXehdo",PHOTO, roleEntityUser ));
        userRepository.save(buildUser("Skylar","Larter","slarter5@ovh.net","olpArkfRD2",PHOTO, roleEntityUser ));
        userRepository.save(buildUser("Heidi","Saffill","hsaffill6@huffingtonpost.com","DZfgtH",PHOTO, roleEntityUser ));
        userRepository.save(buildUser("Sharity","Penreth","spenreth7@linkedin.com","7QWax89",PHOTO, roleEntityUser ));
        userRepository.save(buildUser("Yasmin","Lapsley","yjoicey8@theglobeandmail.com","r79Sfnp",PHOTO, roleEntityUser ));
        userRepository.save(buildUser("Charissa","MacGillreich","cmacgillreicha@sbwire.com","7MOQwvVOHz2p",PHOTO, roleEntityUser ));
    }

    private UserEntity buildUser(String firstName, String lastName, String email, String password, String photo, RoleEntity roleid ) {

        return new UserEntity(firstName,lastName,email,password,photo,roleid);
    }


}
