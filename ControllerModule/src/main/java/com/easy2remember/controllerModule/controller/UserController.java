package com.easy2remember.controllerModule.controller;


import com.easy2remember.controllerModule.service.NotificationService;
import com.easy2remember.controllerModule.service.UserService;
import com.easy2remember.entitymodule.dto.UserDto;
import com.easy2remember.entitymodule.entity.impl.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/user")
@RestController
public class UserController {

    private UserService userService;
    private NotificationService notificationService;
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Autowired
    public UserController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("register")
    public String createUser(@RequestBody User user) {
        userService.newUser(user);
        return "Registration user: " + user.getUsername() + " succeeded";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @GetMapping("show/{username}")
    public ResponseEntity<UserDto> showUser(@PathVariable String username) {
        return ResponseEntity.ok(User.generateDto(this.userService.getUserByName(username)));
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{publisherName}/subscribers")
    public UserDto[] showAllPublisherSubscribers(@PathVariable("publisherName") String publisherName) {
        return User.generateDtoArray(this.userService.getAllPublisherSubscribers(publisherName));
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/")
    public String updateUser(@RequestBody User updatedUser,
                             @RequestParam("username") String username) {
        this.userService.updateUser(updatedUser, username);
        return "User " + updatedUser.getUsername() + " was successfully updated";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("delete")  //will be implemented with JWT Token in futures' releases
    public String deleteUser(@RequestParam String username) {
        this.userService.removeUser(username);
        return "User: " + username + " was successfully deleted";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("subscribe")
    public String subscribeUserToPublisher(@RequestParam("who") String username,
                                           @RequestParam("to") String publisherName) {

        this.userService.subscribeUserToPublisher(publisherName, username);
        return username + " was successfully subscribed to publisher " + publisherName;
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("unsubscribe")
    public String unsubscribeUserFromPublisher(@RequestParam("who") String username,
                                               @RequestParam("from") String publisherName) {

        this.userService.unsubscribeUserFromPublisher(publisherName, username);
        return username + " was successfully unsubscribed to publisher " + publisherName;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
