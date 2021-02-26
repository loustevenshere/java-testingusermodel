package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplicationTesting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplicationTesting.class,
properties = {
        "command.line.runner.enabled=false"
})
// Have to add users in an ArrayList to test
public class UserServiceImplNoDBTest {

//  @Test
//    public void findUserById()
//  {
//      Mockito.when(userrepos.findById(10L))
//              .thenReturn(Optional.of(userList.get(0)));
//
//      assertEquals("admin", userService.findUserById(10L)
//      .getUsername());
//  }
}

