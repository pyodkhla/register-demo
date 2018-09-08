package com.register.demo.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:8080" + uri;
    }

    @Test
    public void testRegisterNoUsernameCase() throws Exception {
        System.out.println("Test method testRegisterNoUsernameCase()");

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setPassword("password");
        applicationUser.setMobileNo("0123456789");
        applicationUser.setSalary(new BigDecimal(10000));

        HttpEntity<ApplicationUser> entity = new HttpEntity<>(applicationUser, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/users/register"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"messageCode\":\"10\",\"messageDesc\":\"Username or password cannot be null or empty.\"}";

        assertEquals(expected, response.getBody());
    }

    @Test
    public void testRegisterNoUsernameAndPasswordCase() throws Exception {
        System.out.println("Test method testRegisterNoUsernameAndPasswordCase()");

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setMobileNo("0123456789");
        applicationUser.setSalary(new BigDecimal(10000));

        HttpEntity<ApplicationUser> entity = new HttpEntity<>(applicationUser, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/users/register"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"messageCode\":\"10\",\"messageDesc\":\"Username or password cannot be null or empty.\"}";

        assertEquals(expected, response.getBody());
    }

    @Test
    public void testRegisterNoMobileNoCase() throws Exception {
        System.out.println("Test method testRegisterNoMobileNoCase()");

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("admin");
        applicationUser.setPassword("password");
        applicationUser.setSalary(new BigDecimal(10000));

        HttpEntity<ApplicationUser> entity = new HttpEntity<>(applicationUser, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/users/register"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"messageCode\":\"10\",\"messageDesc\":\"Mobile number cannot be null or empty.\"}";

        assertEquals(expected, response.getBody());
    }

    @Test
    public void testRegisterRejectCase() throws Exception {
        System.out.println("Test method testRegisterRejectCase()");

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("admin");
        applicationUser.setPassword("password");
        applicationUser.setMobileNo("0123456789");
        applicationUser.setSalary(new BigDecimal(10000));

        HttpEntity<ApplicationUser> entity = new HttpEntity<>(applicationUser, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/users/register"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"messageCode\":\"10\",\"messageDesc\":\"Reject, salary is null or less than 15,000\"}";

        assertEquals(expected, response.getBody());
    }

    @Test
    public void testRegisterSuccessCase() throws Exception {
        System.out.println("Test method testRegisterSuccessCase()");

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("admin");
        applicationUser.setPassword("password");
        applicationUser.setMobileNo("0123456789");
        applicationUser.setSalary(new BigDecimal(18000));

        HttpEntity<ApplicationUser> entity = new HttpEntity<>(applicationUser, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/users/register"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLogin() throws Exception {
        System.out.println("Test method testLogin()");

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("admin");
        applicationUser.setPassword("password");
        applicationUser.setMobileNo("0123456789");
        applicationUser.setSalary(new BigDecimal(18000));

        ResponseEntity<String> response = null;

        HttpEntity<ApplicationUser> entityRegister = new HttpEntity<>(applicationUser, headers);
        response = testRestTemplate.exchange(
                createURLWithPort("/users/register"),
                HttpMethod.POST, entityRegister, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        HttpEntity<ApplicationUser> entityLogin = new HttpEntity<>(applicationUser, headers);
        response = testRestTemplate.exchange(
                createURLWithPort("/login"),
                HttpMethod.POST, entityLogin, String.class);

        System.out.println("response.getHeaders(): " + response.getHeaders());
        System.out.println("response.getHeaders().Authorization: " + response.getHeaders().get("Authorization"));

        assertNotNull(response.getHeaders().get("Authorization"));
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        System.out.println("Test method shouldNotAllowAccessToUnauthenticatedUsers()");

        mvc.perform(MockMvcRequestBuilders.get(createURLWithPort("/users"))).andExpect(status().isForbidden());
    }


}