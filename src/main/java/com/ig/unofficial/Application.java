package com.ig.unofficial;

import com.ig.unofficial.resources.ApplicationStatus;
import com.ig.unofficial.resources.Instagram;
import com.ig.unofficial.resources.Insta;
import instagram.unofficial.login.IGClient;
import instagram.unofficial.login.model.Reel;
import instagram.unofficial.login.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("api/v1")
public class Application{
    private static IGClient client;

    public static void main(String[] args) {
        client = Account.igClient;
        SpringApplication.run(Application.class,args);
    }

    @RequestMapping()
    public ApplicationStatus status() {
        return new ApplicationStatus("ok","working");
    }

    @GetMapping("/search")
    ResponseEntity<String> search(@RequestParam(value = "name", defaultValue = "instagram") String name) {
        return new ResponseEntity<>(Insta.searchUser(name), HttpStatus.OK);
    }

    @GetMapping("/profile")
    ResponseEntity<User> photo(@RequestParam(value = "id" ,defaultValue = "25025320")String id) {
        User user = client.getActions().users().info(Long.parseLong(String.valueOf(Account.pk(id)))).join();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/video")
    String video(@RequestParam(value = "name" ,defaultValue = "")String link) {

        return Instagram.videos3(link);
    }

    @GetMapping("/reel")
    String reel (@RequestParam (value = "name", defaultValue = "") String link) {
        return Instagram.videos3(link);
    }

    @GetMapping("/story")
    ResponseEntity<Reel> story(@RequestParam (value = "name", defaultValue = "") String name) {

        Reel reel  = client.getActions().story().userStory(String.valueOf(Account.pk(name))).join().getReel();
        return new ResponseEntity<>(reel,HttpStatus.OK);
    }
}