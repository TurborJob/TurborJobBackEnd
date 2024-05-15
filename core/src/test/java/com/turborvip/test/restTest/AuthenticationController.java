package com.turborvip.test.restTest;

import com.turborvip.core.domain.adapter.web.rest.UserResource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(UserResource.class)
public class AuthenticationController {
    @Autowired
    private MockMvc mvc;


}
