package be.ehb.swp.controller;

import be.ehb.swp.exception.GenericWebException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Created by arnaudcoel on 22/10/15.
 */
@Controller
public class ErrorController {

    @RequestMapping(value = "/{type:.+}", method = RequestMethod.GET)
    public ModelAndView getPages(@PathVariable("type") String type)
            throws Exception {

        if ("error".equals(type)) {
            // go handleCustomException
            throw new GenericWebException("E888", "This is custom message");
        } else if ("io-error".equals(type)) {
            // go handleAllException
            throw new IOException();
        } else {
            return new ModelAndView("index").addObject("msg", type);
        }

    }


    @ExceptionHandler(GenericWebException.class)
    public ModelAndView handleCustomException(GenericWebException ex) {

        ModelAndView model = new ModelAndView("error/generic_error");
        model.addObject("errCode", ex.getErrorCode());
        model.addObject("errMsg", ex.getErrorMsg());

        return model;

    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {

        ModelAndView model = new ModelAndView("error/generic_error");
        model.addObject("errMsg", "this is Exception.class");

        return model;

    }

}