package com.example.demo.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

public class UserControllerTest {

  private UserController userController;

  private UserRepository userRepo = mock(UserRepository.class);

  private CartRepository cartRepo = mock(CartRepository.class);

  private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);


  @Before
  public void setUp(){
    userController = new UserController();
    TestUtils.injectObject(userController, "userRepository", userRepo);
    TestUtils.injectObject(userController, "cartRepository", cartRepo);
    TestUtils.injectObject(userController, "bCryptPasswordEncoder", encoder);

  }

  @Test
  public void create_user_happy_path() {

    when(encoder.encode("myPass")).thenReturn("myHash");
    CreateUserRequest createUserRequest = new CreateUserRequest();
    createUserRequest.setUsername("test");
    createUserRequest.setPassword("1234567890");
    createUserRequest.setConfirmPassword("1234567890");

    final ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);


    assertNotNull(responseEntity);
    assertEquals(200, responseEntity.getStatusCodeValue());

    User user = responseEntity.getBody();
    assertNotNull(user);
    assertEquals(0, user.getId());
  }



}
