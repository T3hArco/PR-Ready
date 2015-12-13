package be.ehb.swp2.util;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.entity.UserRole;
import be.ehb.swp2.exception.TokenNotFoundException;
import be.ehb.swp2.exception.UserNotFoundException;
import be.ehb.swp2.manager.UserManager;
import org.hibernate.SessionFactory;

/**
 * Created by arnaudcoel on 13/12/15.
 */
public class PermissionHandler {

    public static boolean currentUserHasPermission(SessionFactory factory, UserRole role) {
        ConfigurationHandler configurationHandler = new ConfigurationHandler(); // moved configurationHandler due to reloading problems
        String token = configurationHandler.getSetting("user", "token");

        return userHasPermission(factory, role, token);
    }

    public static boolean userHasPermission(SessionFactory factory, UserRole role, String token) {
        UserManager um = new UserManager(factory);
        User user = null;

        try {
            user = um.getUserByToken(token);
        } catch (TokenNotFoundException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        return user.getUserRole().equals(role);

    }

}
