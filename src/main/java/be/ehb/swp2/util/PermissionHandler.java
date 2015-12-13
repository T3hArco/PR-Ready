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
    public static boolean currentUserHasPermission(SessionFactory factory, UserRole requiredRole) {
        ConfigurationHandler configurationHandler = new ConfigurationHandler(); // moved configurationHandler due to reloading problems
        String token = configurationHandler.getSetting("user", "token");

        return hasPermission(factory, requiredRole, token);
    }

    public static boolean hasPermission(SessionFactory factory, UserRole requiredRole, String token) {
        UserManager userManager = new UserManager(factory);
        User user = null;

        try {
            user = userManager.getUserByToken(token);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (TokenNotFoundException e) {
            e.printStackTrace();
        }

        return user.getUserRole().equals(requiredRole);

    }
}
