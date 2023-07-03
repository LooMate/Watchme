package com.easy2remember.controllerModule.controller;

import com.easy2remember.controllerModule.service.UserDetailsService;
import com.easy2remember.entitymodule.dto.TelegramChannelDetailsDto;
import com.easy2remember.entitymodule.dto.UserTelegramDetailsDto;
import com.easy2remember.entitymodule.dto.UserWebDetailsDto;
import com.easy2remember.entitymodule.entity.impl.TelegramChannelDetails;
import com.easy2remember.entitymodule.entity.impl.UserTelegramDetails;
import com.easy2remember.entitymodule.entity.impl.UserWebDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/collect")
public class UserAnalyticsController {

    private UserDetailsService userDetailsService;

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @Autowired
    public UserAnalyticsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("save/user_web_info")
    public String createUserWebDetails(@RequestBody UserWebDetails userWebDet) {
        this.userDetailsService.newUserWebDetails(userWebDet);
        return "User Web Details were saved";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create/user_telegram_info")
    public String createUserTelegramDetails(@RequestBody UserTelegramDetails userTgDet) {
        this.userDetailsService.newUserTelegramDetails(userTgDet);
        return "User Telegram Details were saved";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("create/telegram_channel_info")
    public String createTelegramChannelDetails(@RequestBody TelegramChannelDetails tgChDetails) {
        this.userDetailsService.newTelegramChannelDetails(tgChDetails);
        return "Telegram Channel Details were saved";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @GetMapping("get/user_web_details")
    public ResponseEntity<UserWebDetailsDto> showUserWebDetails(@RequestParam("uI") Long userId) {
        return ResponseEntity.status(200)
                .body(UserWebDetails.generateDto(this.userDetailsService.getUserWebDetailsByUserId(userId)));
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @GetMapping("get/user_telegram_details")
    public ResponseEntity<UserTelegramDetailsDto> showUserTelegramDetails(@RequestParam("uI") Long userId) {
        return ResponseEntity.status(200)
                .body(UserTelegramDetails.generateDto(this.userDetailsService.getUserTelegramDetailsByUserId(userId)));
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @GetMapping("get/telegram_channel_details")
    public ResponseEntity<TelegramChannelDetailsDto> showTelegramChannelDetails(@RequestParam("uI") Long userId) {
        return ResponseEntity.status(200)
                .body(TelegramChannelDetails.generateDto(this.userDetailsService.getTelegramChannelDetailsByUserId(userId)));
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/user_web_details")
    public String updateUserWebDetails(@RequestBody() UserWebDetails updatedWebDet) {
        int status = this.userDetailsService.updateUserWebDetails(updatedWebDet);
        return status == 1 ? "User Web Details were successfully updated" : "User Details were NOT updated";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/user_telegram_details")
    public String updateUserTelegramDetails(@RequestBody() UserTelegramDetails updatedTgDet) {
        int status = this.userDetailsService.updateUserTelegramDetails(updatedTgDet);
        return status == 1 ? "User Telegram Details were successfully updated" : "User Telegram Details were NOT successfully updated";
    }
    //-----------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("update/telegram_channel_details")
    public String updateTelegramChannelDetails(@RequestBody() TelegramChannelDetails updatedTgChDetails) {
        int status = this.userDetailsService.updateTelegramChannelDetails(updatedTgChDetails);
        return status == 1 ? "Telegram channel details were successfully updated" : "Telegram channel details were NOT successfully updated";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // uI - userId
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("update/recollect")
    public String recollectDetails(@RequestParam("uI") Long userId, @RequestParam("details_type") String recollectType) {
        if (this.userDetailsService.recollectDetails(userId, recollectType) == 1) {
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
        status = this.userDetailsService.removeDetailsByUserId(userId, deleteType);
        return status == 1 ? "Details were deleted" : "User was NOT found and details were NOT deleted" ;
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}