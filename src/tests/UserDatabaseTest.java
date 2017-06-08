package tests;

import controller.UserDatabase;
import model.User;
import org.junit.*;

import java.io.File;

/**
 * Created by Prashant on 31/05/2017.
 */
public class UserDatabaseTest {
    private UserDatabase userDatabase;

    @Before
    public void setUp() {
        userDatabase = new UserDatabase("test");
    }

    @After
    public void tearDown() {
        File file = new File("test" + File.separator + "users.kpsdb");
        file.delete();
    }

    @Test
    public void checkFileExists() {
        File file = new File("test" + File.separator + "users.kpsdb");
        Assert.assertTrue(file.exists());
    }

    @Test
    public void addUserTest_Success() {
        userDatabase.addUser("user", "password123", true);
        Assert.assertNotNull(userDatabase.getUser("user"));
    }

    @Test
    public void addUserTest_Failure_DuplicateUsername() {
        userDatabase.addUser("user", "password123", true);
        try {
            userDatabase.addUser("user", "password123", true);
            Assert.fail("Should not be allowed to add two users with the same username.");
        } catch (UserDatabase.UserDatabaseException e) {
        }
    }

    @Test
    public void getUsers_Success() {
        userDatabase.addUser("user", "password123", true);
        userDatabase.addUser("user2", "password1234", false);
        userDatabase.addUser("user3", "password12345", false);
        userDatabase.addUser("user4", "password123456", false);

        Assert.assertEquals(userDatabase.getUsers().size(),4);
    }

    @Test
    public void deleteUserTest_Success() {
        userDatabase.addUser("user", "password123", true);
        userDatabase.deleteUser("user");
        Assert.assertNull(userDatabase.getUser("user"));
    }

    @Test
    public void deleteUserTest_Failure_DeleteNonExistantUser() {
        userDatabase.addUser("user", "password123", true);
        userDatabase.deleteUser("user");
        try {
            userDatabase.deleteUser("user");
            Assert.fail("Cannot delete user that does not exists.");
        } catch (UserDatabase.UserDatabaseException e) {
        }
    }

    @Test
    public void changeUserUsernameTest_Success() {
        userDatabase.addUser("user", "password123", true);
        userDatabase.changeUserUsername("user", "newUser");

        Assert.assertNull(userDatabase.getUser("user"));
        Assert.assertNotNull(userDatabase.getUser("newUser"));
    }

    @Test
    public void changeUserUsernameTest_Failure_UserDoesNotExist() {
        try {
            userDatabase.changeUserUsername("user", "newUser");
            Assert.fail("You cannot change the username to one that already exists in the database.");
        } catch (UserDatabase.UserDatabaseException e) {
        }
    }

    @Test
    public void changeUserUsernameTest_Failure_NewUsernameAlreadyExists() {
        userDatabase.addUser("user", "password123", true);
        userDatabase.addUser("newUser", "password123", true);

        try {
            userDatabase.changeUserUsername("user", "newUser");
            Assert.fail("You cannot change the username to one that already exists in the database.");
        } catch (UserDatabase.UserDatabaseException e) {
        }
    }


    @Test
    public void changeUserPasswordTest_Success() {
        userDatabase.addUser("user", "password123", true);
        userDatabase.changeUserPassword("user", "password456");

        Assert.assertEquals(userDatabase.getUser("user").getPassword(), "password456");
    }

    @Test
    public void changeUserPasswordTest_Failure_UserDoesNotExist() {
        try {
            userDatabase.changeUserPassword("user", "password123");
            Assert.fail("You cannot update password for a user that does not exist.");
        } catch (UserDatabase.UserDatabaseException e) {
        }
    }

    @Test
    public void changeUserPermissionTest_Success() {
        userDatabase.addUser("user", "password123", true);
        userDatabase.changeUserPermission("user", false);

        Assert.assertEquals(userDatabase.getUser("user").isManager(), false);
    }

    @Test
    public void changeUserPermissionTest_Failure_UserDoesNotExist() {
        try {
            userDatabase.changeUserPermission("user", false);
            Assert.fail("You cannot update password for a user that does not exist.");
        } catch (UserDatabase.UserDatabaseException e) {
        }
    }
}
