package com.easy2remember.controller;


import com.easy2remember.dto.UserDto;
import com.easy2remember.dto.auth.UserRegistrationRequest;
import com.easy2remember.entity.impl.main.User;
import com.easy2remember.service.NotificationService;
import com.easy2remember.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/register")
    public String createUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        userService.newUser(userRegistrationRequest);
        return "Registration user: " + userRegistrationRequest.username() + " succeeded";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//    @ResponseStatus(HttpStatus.OK)
//    @PostMapping("login")
//    public String loginUser(@RequestBody UserLoginRequest userLogRequest) {
//        userService.loginUser(userLogRequest);
//        return "Registration user: " + userLogRequest.username() + " was logged in";
//    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("show/{username}")
    public UserDto showUser(@PathVariable String username) {
        return User.generateDto(this.userService.getUserByName(username));
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
    @DeleteMapping("delete/")  //will be implemented with JWT Token in futures' releases
    public String deleteUser(@RequestParam String username) {
        this.userService.deleteUser(username);
        return "User: " + username + " was successfully deleted";
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("subscribe/")
    public String subscribeUserToPublisher(@RequestParam("who") String username,
                                           @RequestParam("to") String publisherName) {
        this.userService.subscribeUserToPublisher(publisherName, username);
        return username + " was successfully subscribed to publisher " + publisherName;
    }

    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("unsubscribe/")
    public String unsubscribeUserFromPublisher(@RequestParam("who") String username,
                                               @RequestParam("from") String publisherName) {
        this.userService.unsubscribeUserFromPublisher(publisherName, username);
        return username + " was successfully unsubscribed to publisher " + publisherName;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
