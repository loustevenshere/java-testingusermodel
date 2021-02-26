package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplicationTesting;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

// Because this is using the database, it will also be using the repository as well
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplicationTesting.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplWithDBTest {

    @Autowired
    private UserService userService;

    @MockBean
    HelperFunctions helperFunctions;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void B_findUserById() {
        assertEquals("admin", userService.findUserById(4).getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void BA_findUserByIdNotFound()
    {
        assertEquals("admin",
                userService.findUserById(10)
        .getUsername());
    }

    @Test
    public void C_findAll()
    {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void D_delete()
    {
        userService.delete(13);
        assertEquals(4, userService.findAll().size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void DA_notFoundDelete()
    {
        userService.delete(100);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void E_findByUsername()
    {
        assertEquals("admin", userService.findByName("admin").getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void AA_findByUsernameNotFound()
    {
        assertEquals("admin", userService.findByName("turtle")
        .getUsername());
    }

    @Test
    public void AB_findByNameContaining()
    {
        assertEquals(4, userService.findByNameContaining("a").size());
    }

    @Test
    public void F_save()
    {
        Role r2 = new Role("user");
        r2.setRoleid(2);

        User u2 = new User("tiger",
                "IHateMath!",
                "tiger@school.lambda");

        u2.getRoles()
                .add(new UserRoles(u2, r2));
        u2.getUseremails()
                .add(new Useremail(u2, "tiger@wild.local"));

        User saveU2 = userService.save(u2);

        System.out.println(saveU2);

        assertEquals("tiger@wild.local",
                saveU2.getUseremails().get(0).getUseremail());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void FA_saveputnotfound()
    {
        Role r2 = new Role("user");
        r2.setRoleid(2);

        User u2 = new User("tiger",
                "IHateMath!",
                "tiger@school.lambda");

        u2.getRoles()
                .add(new UserRoles(u2, r2));
        u2.getUseremails()
                .add(new Useremail(u2, "tiger@wild.local"));

        User saveU2 = userService.save(u2);

        System.out.println(saveU2);

        assertEquals("tiger@wild.local",
                saveU2.getUseremails().get(0).getUseremail());
    }

    @Test
    public void FA_saveputfound()
    {
        Role r2 = new Role("user");
        r2.setRoleid(2);

        User u2 = new User("tigger",
                "IHateLoveMath!",
                "tigger@school.lambda");

        u2.getRoles()
                .add(new UserRoles(u2, r2));
        u2.getUseremails()
                .add(new Useremail(u2, "tiger@wild.local"));

        User saveU2 = userService.save(u2);

        System.out.println("*** Data ***");
        System.out.println(saveU2);
        System.out.println("*** Data ***");

        assertEquals("tiger@wild.local",
                saveU2.getUseremails().get(0).getUseremail());
    }

    @Test
    public void G_update()
    {

        // mock the helper function - Security function
        Mockito.when(helperFunctions.isAuthorizedToMakeChange(anyString()))
                .thenReturn(true);

        Role r2 = new Role("user");
        r2.setRoleid(2);

        User u2 = new User("Rhino",
                "IHateMath!",
                "Rhino@school.lambda");

        u2.getRoles()
                .add(new UserRoles(u2, r2));
        u2.getUseremails()
                .add(new Useremail(u2, "tiger@wild.local"));

        User updateU2 = userService.update(u2);

        System.out.println(updateU2);

        assertEquals("tiger@wild.local",
                updateU2.getUseremails().get(0).getUseremail());

        int checking = updateU2.getUseremails()
                .size() - 1;
        assertEquals("tiger@wild.local",
                updateU2.getUseremails()
        .get(checking)
        .getUseremail());

    }

    @Test(expected = ResourceNotFoundException.class)
    public void GB_updateNotCurrentUserNorAdmin()
    {
        Role r2 = new Role("user");
        r2.setRoleid(2);

        User u2 = new User("tiger",
                "IHateMath!",
                "tiger@school.lambda");

        u2.getRoles()
                .add(new UserRoles(u2, r2));
        u2.getUseremails()
                .add(new Useremail(u2, "tiger@wild.local"));
        u2.getUseremails()
                .add(new Useremail(u2, "tiger@home.local"));
        u2.getUseremails()
                .add(new Useremail(u2, "tiger@away.local"));


        // Unauthorized user
        Mockito.when(helperFunctions.isAuthorizedToMakeChange(anyString()))
                .thenReturn(false);

        User updateU2 = userService.update(u2);

        int checking = updateU2.getUseremails()
                .size() - 1;
        assertEquals("tiger@away.local",
                updateU2.getUseremails()
        .get(checking)
        .getUseremail());

    }





}





