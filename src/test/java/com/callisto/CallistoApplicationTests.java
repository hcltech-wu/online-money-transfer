package com.callisto;

import com.callisto.model.Address;
import com.callisto.model.User;
import com.callisto.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CallistoApplicationTests {


    @Autowired
    TestRestTemplate restTemplate;

    @MockitoBean
    UserService userService;

    @Test
    void testRegisterUser() throws Exception {

        Address address = new Address();
        address.setStreet("hanuman nagar");
        address.setCity("Patna");
        address.setState("Bihar");
        address.setCountry("India");
        address.setPin("800020");

        User user = new User();
        user.setFirstName("S");
        user.setMiddleName("");
        user.setLastName("kumar");
        user.setDob(LocalDate.of(1995, 9, 24));
        user.setEmail("kumarsuraj15@gmail.com");
        user.setPhoneNumber("7296098302");
        user.setPassword("Password@123");
        user.setAddress(address);
        user.setTermsAccepted(true);


        doNothing().when(userService).saveUser(any());
        ResponseEntity<String> response = restTemplate.postForEntity("/api/user/register",user,String.class);
        System.out.println(response);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("User registered successfully");
    }

}
