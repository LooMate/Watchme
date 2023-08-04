package com.easy2remember.exceptions.handler;


import com.easy2remember.exceptions.users.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class UserExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleGeneralException(Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        return "Opss...Something went wrong";
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(UserWasNotSavedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserWasNotSavedException() {
        return "User was NOT saved";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserAlreadyExistsException() {
        return "User with this name is already exists";
    }

    @ExceptionHandler(UserDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserDoesNotExistsException() {
        return "No such user exists";
    }

    @ExceptionHandler(UserWasNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserWasNotFoundException() {
        return "user was NOT found";
    }

    @ExceptionHandler(UserWasNotDeletedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserWasNotDeletedException() {
        return "user was NOT deleted";
    }

    @ExceptionHandler(UserWasNotUpdatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserWasNotUpdatedException() {
        return "user was NOT updated";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(NoSuchReferralCodeForUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleNoSuchReferralCodeForUserException() {
        return "Referral code is not valid";
    }
    @ExceptionHandler(DetailsTypeEnumException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDetailsTypeEnumException() {
        return "No such data type";
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(SubscribersWereNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleSubscribersWereNotFoundException() {
        return "List of subscribers is not found";
    }

    @ExceptionHandler(PublisherWasNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handlePublisherWasNotFoundException() {
        return "Publisher Does Not Exists";
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(CouldNotSaveSubscriberToPublisherException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCouldNotSaveSubscriberToPublisherException() {
        return "Subscriber's id wasn't saved to the publisher";
    }

    @ExceptionHandler(CouldNotSavePublisherIdToSubscriberException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCouldNotSavePublisherIdToSubscriberException() {
        return "Publisher's id wasn't saved to the subscriber";
    }

    @ExceptionHandler(CouldNotUnsubscribeUserFromPublisherException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCouldNotUnsubscribeUserFromPublisherException() {
        return "Subscriber's id wasn't removed from publisher";
    }

    @ExceptionHandler(CouldNotRemoveSubscriptionFromUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCouldNotRemoveSubscriptionFromUserException() {
        return "Publisher's id wasn't removed from subscriber";
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(UserDoNotHaveAnyPostsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserDoNotHaveAnyPostsException() {
        return "User do NOT have any posts";
    }

    @ExceptionHandler(SpecifiedPostDoesNotBelongToUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleSpecifiedPostDoesNotBelongToUserException() {
        return "Specified post does NOT belong to the user";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    @ExceptionHandler(UserDetailsWasNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserDetailsWereNotFoundException() {
        return "User Details were NOT found";
    }

    @ExceptionHandler(NoSuchElementBelongsToUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleNoSuchElementBelongsToUserException() {
        return "User Details were NOT found";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    @ExceptionHandler(UserInvitedTypeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserInvitedTypeException(){
        return "Invited from type is NOT correct";
    }


    @ExceptionHandler(UsernameDoesNotMatchLoggedUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUsernameDoesNotMatchLoggedUserException(){
        return "Username does not match logged User";
    }

    @ExceptionHandler(UserIdDoesNotMatchLoggedUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleUserIdDoesNotMatchLoggedUserException(){
        return "User Id does not match logged User";
    }



}
