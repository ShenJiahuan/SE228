package com.shenjiahuan.eBook.controller;

import com.google.gson.*;
import com.shenjiahuan.eBook.entity.Book;
import com.shenjiahuan.eBook.util.HibernateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {

    @Before
    public void init() {
        HibernateUtil.initialize();
    }

    @Autowired
    BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(bookController).isNotNull();
    }

    @Test
    public void getBookInfoSuccess() throws Exception {
        this.mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"bookId\":1}"));
    }

    @Test
    public void getBookInfoNotFound() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/books/-1"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    public void getBookInfoWithIncorrectBookId() throws Exception {
        this.mockMvc.perform(get("/books/书"))
                .andExpect(status().is(400));
    }

    @Test
    public void getBookListSuccess() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/books/")
                .param("keyword", "人"))
                .andExpect(status().isOk())
                .andReturn();

        assertNotEquals("", result.getResponse().getContentAsString());
    }

    @Test
    public void getBookListNotFound() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/books/")
                .param("keyword", "11111111"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("", result.getResponse().getContentAsString());
    }

    @Test
    public void getBookLisWithIncorrectParameter() throws Exception {
        this.mockMvc.perform(get("/books/"))
                .andExpect(status().is(400));
    }

    @Test
    public void getHotBookListSuccess() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/books/hot")
                .param("limit", "10"))
                .andExpect(status().isOk())
                .andReturn();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(result.getResponse().getContentAsString());
        assertEquals(10, jsonArray.size());
    }

    @Test
    public void getHotBookListIncorrectLimit() throws Exception {
        this.mockMvc.perform(get("/books/hot")
                .param("limit", "-10"))
                .andExpect(status().is(400));
    }

    @Test
    public void getHotBookListWithoutLimit() throws Exception {
        this.mockMvc.perform(get("/books/hot"))
                .andExpect(status().is(400));
    }

    @Test
    public void getRecommendBookList() {
    }

    @Test
    public void uploadImage() {
    }

    @Test
    public void uploadBook() {
    }
}
