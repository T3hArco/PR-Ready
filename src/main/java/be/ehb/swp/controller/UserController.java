package be.ehb.swp.controller;

import be.ehb.swp.entity.User;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

/**
 * Created by arnaudcoel on 08/10/15.
 */

@Controller
public class UserController extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("userresult");
    }

    @RequestMapping(value="/user", method= RequestMethod.GET)
    public String showForm(User user) {
        return "userform";
    }

    @RequestMapping(value="/user", method=RequestMethod.POST)
    public String checkUserInfo(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "userform";
        }
        return "redirect:/results";
    }
}
