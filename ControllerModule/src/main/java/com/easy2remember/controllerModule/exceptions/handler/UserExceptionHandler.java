package com.easy2remember.controllerModule.exceptions.handler;


import com.easy2remember.controllerModule.exceptions.users.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class UserExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(409).body("Opss...Something went wrong");
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(UserWasNotSavedException.class)
    public ResponseEntity<String> handleUserWasNotSavedException() {
        return ResponseEntity.status(409).body("User was NOT saved");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException() {
        return ResponseEntity.status(409).body("User with this name is already exists");
    }

    @ExceptionHandler(UserWasNotFoundException.class)
    public ResponseEntity<String> handleUserWasNotFoundException() {
        return ResponseEntity.status(409).body("user was NOT found");
    }

    @ExceptionHandler(UserWasNotDeletedException.class)
    public ResponseEntity<String> handleUserWasNotDeletedException() {
        return ResponseEntity.status(409).body("user was NOT deleted");
    }

    @ExceptionHandler(UserWasNotUpdatedException.class)
    public ResponseEntity<String> handleUserWasNotUpdatedException() {
        return ResponseEntity.status(409).body("user was NOT updated");
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(NoSuchReferralCodeForUserException.class)
    public ResponseEntity<String> handleNoSuchReferralCodeForUserException() {
        return ResponseEntity.status(409).body("Referral code is not valid");
    }
    @ExceptionHandler(DetailsTypeEnumException.class)
    public ResponseEntity<String> handleDetailsTypeEnumException() {
        return ResponseEntity.status(409).body("No such data type");
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(SubscribersWereNotFoundException.class)
    public ResponseEntity<String> handleSubscribersWereNotFoundException() {
        return ResponseEntity.status(409).body("List of subscribers is not found");
    }

    @ExceptionHandler(PublisherWasNotFoundException.class)
    public ResponseEntity<String> handlePublisherWasNotFoundException() {
        return ResponseEntity.status(409).body("Publisher Does Not Exists");
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(CouldNotSaveSubscriberToPublisherException.class)
    public ResponseEntity<String> handleCouldNotSaveSubscriberToPublisherException() {
        return ResponseEntity.status(409).body("Subscriber's id wasn't saved to the publisher");
    }

    @ExceptionHandler(CouldNotSavePublisherIdToSubscriberException.class)
    public ResponseEntity<String> handleCouldNotSavePublisherIdToSubscriberException() {
        return ResponseEntity.status(409).body("Publisher's id wasn't saved to the subscriber");
    }

    @ExceptionHandler(CouldNotUnsubscribeUserFromPublisherException.class)
    public ResponseEntity<String> handleCouldNotUnsubscribeUserFromPublisherException() {
        return ResponseEntity.status(409).body("Subscriber's id wasn't removed from publisher");
    }

    @ExceptionHandler(CouldNotRemoveSubscriptionFromUserException.class)
    public ResponseEntity<String> handleCouldNotRemoveSubscriptionFromUserException() {
        return ResponseEntity.status(409).body("Publisher's id wasn't removed from subscriber");
    }

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(UserDoNotHaveAnyPostsException.class)
    public ResponseEntity<String> handleUserDoNotHaveAnyPostsException() {
        return ResponseEntity.status(409).body("User do NOT have any posts");
    }

    @ExceptionHandler(SpecifiedPostDoesNotBelongToUserException.class)
    public ResponseEntity<String> handleSpecifiedPostDoesNotBelongToUserException() {
        return ResponseEntity.status(409).body("Specified post does NOT belong to the user");
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    @ExceptionHandler(UserDetailsWasNotFoundException.class)
    public ResponseEntity<String> handleUserDetailsWereNotFoundException() {
        return ResponseEntity.status(409).body("User Details were NOT found");
    }

    @ExceptionHandler(NoSuchElementBelongsToUserException.class)
    public ResponseEntity<String> handleNoSuchElementBelongsToUserException() {
        return ResponseEntity.status(409).body("User Details were NOT found");
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
