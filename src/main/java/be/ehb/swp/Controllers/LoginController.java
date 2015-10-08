package be.ehb.swp.Controllers;

/**
 * Created by arnaudcoel on 08/10/15.
 */

import org.springframework.stereotype.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value="username", required=true) String username, @RequestParam(value="username", required=true) String password, Model model) {
        model.addAttribute(username, "username");
        model.addAttribute(password, "password");
        return "authenticated";
    }

}