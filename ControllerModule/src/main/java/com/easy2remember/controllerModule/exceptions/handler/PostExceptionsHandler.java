package com.easy2remember.controllerModule.exceptions.handler;


import com.easy2remember.controllerModule.exceptions.posts.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class PostExceptionsHandler {

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostAnalyticsInfoWasNotFoundException.class)
    public ResponseEntity<String> handlerPostAnalyticsInfoWasNotFoundException() {
        return ResponseEntity.status(409).body("Post analytics info was Not Found");
    }

    @ExceptionHandler(PostWasNotFoundException.class)
    public ResponseEntity<String> handlerPostWasNotFoundException() {
        return ResponseEntity.status(409).body("Post was NOT found");
    }

    @ExceptionHandler(PostDetailsWasNotFoundException.class)
    public ResponseEntity<String> handlerPostDetailsWasNotFoundException() {
        return ResponseEntity.status(409).body("Post details was NOT found");
    }

    @ExceptionHandler(PostsWereNotFoundException.class)
    public ResponseEntity<String> handlerPostsWereNotFoundException() {
        return ResponseEntity.status(409).body("Posts were NOT found");
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostAnalyticsSnapWasNotSavedException.class)
    public ResponseEntity<String> handlerPostAnalyticsSnapWasNotSavedException() {
        return ResponseEntity.status(409).body("Post Analytics Snap was NOT saved");
    }

    @ExceptionHandler(PostAnalyticsInfoWasNotSavedException.class)
    public ResponseEntity<String> handlerPostAnalyticsInfoWasNotSavedException() {
        return ResponseEntity.status(409).body("Post analytics Ingo was NOT saved");
    }

    @ExceptionHandler(PostWasNotSavedException.class)
    public ResponseEntity<String> handlerPostWasNotSavedException() {
        return ResponseEntity.status(409).body("Post was NOT saved");
    }

    @ExceptionHandler(PostDetailsWasNotSavedException.class)
    public ResponseEntity<String> handlerPostDetailsWasNotSavedException() {
        return ResponseEntity.status(409).body("Post's details were NOT saved");
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostNotificationTypeException.class)
    public ResponseEntity<String> handlePostNotificationTypeException() {
        return ResponseEntity.status(409).body("Bad post's notification type");
    }

    @ExceptionHandler(PostNotificationCouldNotBeSentException.class)
    public ResponseEntity<String> handlerPostNotificationCouldNotBeSentException() {
        return ResponseEntity.status(409).body("Post notification was NOT sent");
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(NoAccessToPostDetailsException.class)
    public ResponseEntity<String> handlerNoAccessToPostDetailsException() {
        return ResponseEntity.status(409).body("User does NOT have access to the post");
    }

   @ExceptionHandler(NoCuchPostANalyticsInfoForSpecifiedPostException.class)
    public ResponseEntity<String> handlerNoSuchPostAnalyticsInfoForSpecifiedPostException() {
        return ResponseEntity.status(409).body("No such post analytics info exists for specified Post");
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostAnalyticsStateWasNotSpecifiedException.class)
    public ResponseEntity<String> handlerPostAnalyticsStateWasNotSpecifiedException() {
        return ResponseEntity.status(409).body("Post Analytics State was NOT provided");
    }

    @ExceptionHandler(PostAnalyticsUnresolvableStateException.class)
    public ResponseEntity<String> handlerPostAnalyticsUnresolvableStateException() {
        return ResponseEntity.status(409).body("Post Analytics State Is WRONG");
    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostWasNotUpdatedException.class)
    public ResponseEntity<String> handlerPostWasNotUpdatedException() {
        return ResponseEntity.status(409).body("Post was Not updated");
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
