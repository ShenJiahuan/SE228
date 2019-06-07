package com.shenjiahuan.eBook.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class CartControllerTest {

    @Autowired
    CartController cartController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(cartController).isNotNull();
    }

    @Test
    public void getCartListUnauthorized() throws Exception {
        this.mockMvc.perform(get("/cart"))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"NORMAL"})
    public void getCartListSuccess() throws Exception {
        this.mockMvc.perform(get("/cart"))
                .andExpect(status().is(200));
    }

    @Test
    public void addCart() {
    }

    @Test
    public void updateCart() {
    }

    @Test
    public void deleteCart() {
    }
}
