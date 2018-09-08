package com.register.demo.user;

import com.register.demo.error.ErrorResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public List<ApplicationUserResponse> getUsers() throws Exception {
        Iterable<ApplicationUser> applicationUsers = applicationUserRepository.findAll();
        List<ApplicationUserResponse> users = new ArrayList<>();
        for (ApplicationUser user: applicationUsers) {
            users.add(new ApplicationUserResponse(
                    user.getId(),
                    user.getReferenceCode(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getMobileNo(),
                    user.getMemberType(),
                    user.getSalary()));
        }
        return users;
    }

    @PostMapping("/register")
    public ErrorResponse signUp(@RequestBody ApplicationUser user) throws Exception {
        if (user.getUsername() == null || user.getUsername().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()) {
            return new ErrorResponse("10", "Username or password cannot be null or empty.");
        }
        if (user.getMobileNo() == null || user.getMobileNo().isEmpty()) {
            return new ErrorResponse("10", "Mobile number cannot be null or empty.");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        user.setReferenceCode(now.format(formatter) + user.getMobileNo().substring(user.getMobileNo().length() - 4));

        if (user.getSalary().compareTo(BigDecimal.valueOf(15000)) < 0) {
            return new ErrorResponse("10", "Reject, salary is null or less than 15,000");
        } else if (user.getSalary().compareTo(BigDecimal.valueOf(30000)) < 0) {
            user.setMemberType("Silver");
        } else if (user.getSalary().compareTo(BigDecimal.valueOf(50000)) <= 0) {
            user.setMemberType("Gold");
        } else {
            user.setMemberType("Platinum");
        }
        applicationUserRepository.save(user);
        return new ErrorResponse("00", "Success");
    }
}
