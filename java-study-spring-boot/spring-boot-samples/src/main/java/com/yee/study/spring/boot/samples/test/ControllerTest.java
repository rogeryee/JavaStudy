package com.yee.study.spring.boot.samples.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Roger.Yi
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void testMvc() throws Exception {
        String name = "Phoebe";
        int expectId = 2;
        UserInfo expectUserInfo = new UserInfo("Phoebe4Test", 20);
        given(this.userService.getUserId(name)).willReturn(expectId);
        given(this.userService.getUserByName(name)).willReturn(expectUserInfo);

        mvc.perform(get("/test/getId/{name}", name)).andExpect(content().string(String.valueOf(expectId)));
        mvc.perform(get("/test/get/{name}", name)).andExpect(content().string(String.valueOf(expectUserInfo.getName())));
    }
}
