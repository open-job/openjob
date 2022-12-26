package io.openjob.server.handler;

import io.openjob.common.constant.StatusEnum;
import io.openjob.common.response.Result;
import io.openjob.server.common.exception.CodeException;
import io.openjob.server.common.exception.HttpStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author stelin <swoft@qq.com>
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
public class ServerExceptionHandler {

    /**
     * NoHandlerFoundException
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public Result<Object> defaultErrorHandler(HttpServletRequest ignoredRequest, HttpServletResponse response, NoHandlerFoundException exception) {
        // Http status.
        this.doResponseStatus(response, StatusEnum.NOT_FOUND.getStatus());

        // Empty exception.
        if (exception == null) {
            log.error("NoHandlerFoundException is empty!");
            return Result.success("request failed!");
        }

        log.error(exception.getMessage(), exception);
        return Result.success(new Object(), 0, exception.getMessage());
    }

    /**
     * MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<Object> defaultErrorHandler(HttpServletRequest ignoredRequest, HttpServletResponse response, MethodArgumentNotValidException argException) {
        // Http status.
        this.doResponseStatus(response, StatusEnum.INVALID_ARGUMENT.getStatus());

        // Empty exception.
        if (argException == null) {
            log.error("MethodArgumentNotValidException is empty!");
            return Result.success("request failed!");
        }

        log.error(argException.getMessage(), argException);

        // Parse argument error message.
        BindingResult bindingResult = argException.getBindingResult();
        StringBuilder msgBuilder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String fieldStr = String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage());
            msgBuilder.append(fieldStr).append(", ");
        }

        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();
        if (!CollectionUtils.isEmpty(globalErrors)) {
            for (ObjectError objError : globalErrors) {
                msgBuilder.append(String.format("%s %s", objError.getObjectName(), objError.getDefaultMessage())).append(", ");
            }
        }

        String message = msgBuilder.toString();
        log.error(message);
        return Result.success(new Object(), 0, message);

    }


    /**
     * Throwable
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public Result<Object> defaultErrorHandler(HttpServletRequest ignoredRequest, HttpServletResponse response, Throwable throwable) {
        // Http status.
        this.doResponseStatus(response, StatusEnum.FAIL.getStatus());

        // Empty exception.
        if (throwable == null) {
            log.error("Throwable is empty!");
            return Result.success("request failed!");
        }

        log.error(throwable.getMessage(), throwable);
        return Result.success(throwable.getMessage());
    }

    /**
     * HttpStatusException
     */
    @ExceptionHandler(value = HttpStatusException.class)
    @ResponseBody
    public Result<Object> HttpStatusErrorHandler(HttpServletRequest ignoredRequest, HttpServletResponse response, HttpStatusException exception) {
        // Empty exception.
        if (exception == null) {
            // Http status.
            this.doResponseStatus(response, StatusEnum.FAIL.getStatus());

            log.error("HttpStatusException is empty!");
            return Result.success("request failed!");
        }

        // Http status.
        this.doResponseStatus(response, exception.getBaseEnum().getValue());

        log.info(exception.getMessage(), exception);
        return Result.success(exception.getMessage());
    }


    /**
     * Throwable
     */
    @ExceptionHandler(value = CodeException.class)
    @ResponseBody
    public Result<Object> codeErrorHandler(HttpServletRequest ignoredRequest, HttpServletResponse response, CodeException exception) {
        // Http status.
        this.doResponseStatus(response, StatusEnum.SUCCESS.getStatus());

        // Empty exception.
        if (exception == null) {
            log.error("CodeException is empty!");
            return Result.success("request failed!");
        }

        log.info(exception.getMessage(), exception);
        return Result.success(new Object(), exception.getBaseEnum().getValue(), exception.getMessage());
    }

    /**
     * Http status.
     *
     * @param response response
     */
    private void doResponseStatus(HttpServletResponse response, Integer status) {
        response.setStatus(status);
    }
}
