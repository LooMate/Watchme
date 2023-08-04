package com.easy2remember.controller;

import com.easy2remember.service.UsersDetailsService;
import com.easy2remember.dto.TelegramChannelDetailsDto;
import com.easy2remember.dto.UserTelegramDetailsDto;
import com.easy2remember.dto.UserWebDetailsDto;
import com.easy2remember.entity.impl.details.TelegramChannelDetails;
import com.easy2remember.entity.impl.details.UserTelegramDetails;
import com.easy2remember.entity.impl.details.UserWebDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/collect")
public class UserAnalyticsController {

    private UsersDetailsService usersDetailsService;

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Autowired
    public UserAnalyticsController(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("save/user_web_info")
    public String createUserWebDetails(@RequestBody UserWebDetails userWebDet) {
        this.usersDetailsService.newUserWebDetails(userWebDet);
        return "User Web Details were saved";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create/user_telegram_info")
    public String createUserTelegramDetails(@RequestBody UserTelegramDetails userTgDet) {
        this.usersDetailsService.newUserTelegramDetails(userTgDet);
        return "User Telegram Details were saved";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create/telegram_channel_info")
    public String createTelegramChannelDetails(@RequestBody TelegramChannelDetails tgChDetails) {
        this.usersDetailsService.newTelegramChannelDetails(tgChDetails);
        return "Telegram Channel Details were saved";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("get/user_web_details")
    public UserWebDetailsDto showUserWebDetails(@RequestParam("uI") Long userId) {
        return UserWebDetails.generateDto(this.usersDetailsService.getUserWebDetailsByUserId(userId));
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("get/user_telegram_details")
    public UserTelegramDetailsDto showUserTelegramDetails(@RequestParam("uI") Long userId) {
        return UserTelegramDetails.generateDto(this.usersDetailsService.getUserTelegramDetailsByUserId(userId));
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("get/telegram_channel_details")
    public TelegramChannelDetailsDto showTelegramChannelDetails(@RequestParam("uI") Long userId) {
        return TelegramChannelDetails.generateDto(this.usersDetailsService.getTelegramChannelDetailsByUserId(userId));
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/user_web_details")
    public String updateUserWebDetails(@RequestBody() UserWebDetails updatedWebDet) {
        int status = this.usersDetailsService.updateUserWebDetails(updatedWebDet);
        return status == 1 ? "User Web Details were successfully updated" : "User Details were NOT updated";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/user_telegram_details")
    public String updateUserTelegramDetails(@RequestBody() UserTelegramDetails updatedTgDet) {
        int status = this.usersDetailsService.updateUserTelegramDetails(updatedTgDet);
        return status == 1 ? "User Telegram Details were successfully updated" : "User Telegram Details were NOT successfully updated";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/telegram_channel_details")
    public String updateTelegramChannelDetails(@RequestBody() TelegramChannelDetails updatedTgChDetails) {
        int status = this.usersDetailsService.updateTelegramChannelDetails(updatedTgChDetails);
        return status == 1 ? "Telegram channel details were successfully updated" : "Telegram channel details were NOT successfully updated";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // uI - userId
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("update/recollect")
    public String recollectDetails(@RequestParam("uI") Long userId, @RequestParam("details_type") String recollectType) {
        if (this.usersDetailsService.recollectDetails(userId, recollectType) == 1) {
            return "Recollecting data was enabled";
        }
        return "Recollecting data is unable";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    // uI - userId
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("update/delete")
    public String deleteDetails(@RequestParam("uI") Long userId, @RequestParam("delete_type") String deleteType) {
        int status = 0;
        status = this.usersDetailsService.removeDetailsByUserId(userId, deleteType);
        return status == 1 ? "Details were deleted" : "User was NOT found and details were NOT deleted" ;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}