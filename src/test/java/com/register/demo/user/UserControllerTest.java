package com.register.demo.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:8080" + uri;
    }

    /*@Before
    public void initialDataForTest() {
        applicationUserRepository.save(new ApplicationUser("user01", "password", "0123456789", new BigDecimal(18000)));
        applicationUserRepository.save(new ApplicationUser("user02", "password", "9876543210", new BigDecimal(35000)));
    }*/

    @Test
    public void testSignUpRejectCase() throws Exception {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("admin");
        applicationUser.setPassword("password");
        applicationUser.setMobileNo("0123456789");
        applicationUser.setSalary(new BigDecimal(10000));

        HttpEntity<ApplicationUser> entity = new HttpEntity<>(applicationUser, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/users/sign-up"),
                HttpMethod.POST, entity, String.class);

        String expected = "{\"messageCode\":\"10\",\"messageDesc\":\"Reject, salary is null or less than 15,000\"}";

        assertEquals(expected, response.getBody());
    }

    @Test
    public void testSignUpSuccessCase() throws Exception {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("admin");
        applicationUser.setPassword("password");
        applicationUser.setMobileNo("0123456789");
        applicationUser.setSalary(new BigDecimal(18000));

        HttpEntity<ApplicationUser> entity = new HttpEntity<>(applicationUser, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/users/sign-up"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLogin() throws Exception {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("admin");
        applicationUser.setPassword("password");

        HttpEntity<ApplicationUser> entity = new HttpEntity<>(applicationUser, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/login"),
                HttpMethod.POST, entity, String.class);

        System.out.println("response.getHeaders(): " + response.getHeaders());
        System.out.println("response.getBody(): " + response.getBody());
    }

    /*@Test
    public void testSignMemberTypeSilverCase() throws Exception {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername("admin");
        applicationUser.setPassword("password");
        applicationUser.setMobileNo("0123456789");
        applicationUser.setSalary(new BigDecimal(20000));

        HttpEntity<ApplicationUser> entity = new HttpEntity<>(applicationUser, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/users/sign-up"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        System.out.println("response.getBody(): " + response.getBody());

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            response = testRestTemplate.exchange(
                    createURLWithPort("/users"),
                    HttpMethod.GET, entity, String.class);

            System.out.println("response.getBody(): " + response.getBody());

            Gson gson = new Gson();
            ApplicationUser e = gson.fromJson(response.getBody(), ApplicationUser.class);
            assertEquals("Silver", e.getMemberType());
        }
    }*/

    /*@Test
    public void getUsers() throws Exception {
        List<ApplicationUser> applicationUsers = testRestTemplate.getForObject("/users/sign-up", List.class);
        assertEquals(0, applicationUsers.size());
    }*/
}