package com.easy2remember.exceptions.handler;


import com.easy2remember.exceptions.posts.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class PostExceptionsHandler {

//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostAnalyticsInfoWasNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostAnalyticsInfoWasNotFoundException() {
        return "Post analytics info was Not Found";
    }

    @ExceptionHandler(PostWasNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostWasNotFoundException() {
        return "Post was NOT found";
    }

    @ExceptionHandler(PostDetailsWasNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostDetailsWasNotFoundException() {
        return "Post details was NOT found";
    }

    @ExceptionHandler(PostsWereNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostsWereNotFoundException() {
        return "Posts were NOT found";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostAnalyticsSnapWasNotSavedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostAnalyticsSnapWasNotSavedException() {
        return "Post Analytics Snap was NOT saved";
    }

    @ExceptionHandler(PostAnalyticsInfoWasNotSavedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostAnalyticsInfoWasNotSavedException() {
        return "Post analytics Ingo was NOT saved";
    }

    @ExceptionHandler(PostWasNotSavedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostWasNotSavedException() {
        return "Post was NOT saved";
    }

    @ExceptionHandler(PostDetailsWasNotSavedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostDetailsWasNotSavedException() {
        return "Post's details were NOT saved";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostNotificationTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePostNotificationTypeException() {
        return "Bad post's notification type";
    }

    @ExceptionHandler(PostNotificationCouldNotBeSentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostNotificationCouldNotBeSentException() {
        return "Post notification was NOT sent";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(NoAccessToPostDetailsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerNoAccessToPostDetailsException() {
        return "User does NOT have access to the post";
    }

   @ExceptionHandler(NoCuchPostANalyticsInfoForSpecifiedPostException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerNoSuchPostAnalyticsInfoForSpecifiedPostException() {
        return "No such post analytics info exists for specified Post";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostAnalyticsStateWasNotSpecifiedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostAnalyticsStateWasNotSpecifiedException() {
        return "Post Analytics State was NOT provided";
    }

    @ExceptionHandler(PostAnalyticsUnresolvableStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostAnalyticsUnresolvableStateException() {
        return "Post Analytics State Is WRONG";
    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    @ExceptionHandler(PostWasNotUpdatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerPostWasNotUpdatedException() {
        return "Post was Not updated";
    }
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
