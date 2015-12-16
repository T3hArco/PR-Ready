package be.ehb.swp2.ui;

import be.ehb.swp2.entity.UserRole;
import be.ehb.swp2.exception.TokenNotFoundException;
import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.exception.UserNotFoundException;

/**
 * Created by arnaudcoel on 22/11/15.
 */
public interface PermissionWindow extends Window {
    UserRole ADMINISTRATOR = UserRole.ADMINISTRATOR;
    UserRole USER = UserRole.USER;

    boolean hasPermission() throws UserNoPermissionException, UserNotFoundException, TokenNotFoundException;
}
