package com.shenjiahuan.eBook.controller;

import com.shenjiahuan.eBook.util.HibernateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class OrderItemControllerTest {

    @Before
    public void init() {
        HibernateUtil.initialize();
    }

    @Autowired
    @Lazy
    BookController bookController;

    @Autowired
    @Lazy
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(bookController).isNotNull();
    }

    @Test
    public void getOrderListUnauthorized() throws Exception {
        this.mockMvc.perform(get("/orders"))
                .andExpect(status().is(401));
    }

    @Test
    public void getOrderListSuccess() throws Exception {
    }

    @Test
    public void createOrder() {
    }

    @Test
    public void updateOrder() {
    }

    @Test
    public void deleteOrder() {
    }
}
