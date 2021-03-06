package com.shenjiahuan.eBook.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class BookControllerTest {

    @Autowired
    BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @Value("${image-dir}")
    String imageDir;

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
        this.mockMvc.perform(get("/books/-1"))
                .andExpect(status().is(404));
    }

    @Test
    public void getBookInfoWithIncorrectBookId() throws Exception {
        this.mockMvc.perform(get("/books/书"))
                .andExpect(status().is(400));
    }

    @Test
    public void getBookListSuccess() throws Exception {
        this.mockMvc.perform(get("/books/")
                .param("keyword", "人"))
                .andExpect(status().isOk());
    }

    @Test
    public void getBookListNotFound() throws Exception {
        this.mockMvc.perform(get("/books/")
                .param("keyword", "11111111"))
                .andExpect(status().is(404));
    }

    @Test
    public void getBookLisWithIncorrectParameter() throws Exception {
        this.mockMvc.perform(get("/books/"))
                .andExpect(status().is(400));
    }

    @Test
    public void getHotBookListSuccess() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/books/top/hot")
                .param("limit", "10"))
                .andExpect(status().isOk())
                .andReturn();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(result.getResponse().getContentAsString());
        assertEquals(10, jsonArray.size());
    }

    @Test
    public void getHotBookListNegativeLimit() throws Exception {
        this.mockMvc.perform(get("/books/top/hot")
                .param("limit", "-10"))
                .andExpect(status().is(400));
    }

    @Test
    public void getHotBookListWithoutLimit() throws Exception {
        this.mockMvc.perform(get("/books/top/hot"))
                .andExpect(status().is(400));
    }

    @Test
    public void getHotBookListLimitToLarge() throws Exception {
        this.mockMvc.perform(get("/books/top/hot")
                .param("limit", "100000"))
                .andExpect(status().is(400));
    }

    @Test
    public void getRecommendBookListSuccess() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/books/top/recommend")
                .param("limit", "10"))
                .andExpect(status().isOk())
                .andReturn();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(result.getResponse().getContentAsString());
        assertEquals(10, jsonArray.size());
    }

    @Test
    public void getRecommendBookListNegativeLimit() throws Exception {
        this.mockMvc.perform(get("/books/top/recommend")
                .param("limit", "-10"))
                .andExpect(status().is(400));
    }

    @Test
    public void getRecommendBookListWithoutLimit() throws Exception {
        this.mockMvc.perform(get("/books/top/recommend"))
                .andExpect(status().is(400));
    }

    @Test
    public void getRecommendBookListLimitToLarge() throws Exception {
        this.mockMvc.perform(get("/books/top/recommend")
                .param("limit", "100000"))
                .andExpect(status().is(400));
    }

    @Test
    public void uploadImageUnauthorized() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[100]);
        this.mockMvc.perform(multipart("/upload/image")
                .file(file))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser(roles = {"NORMAL"})
    public void uploadImageNoPermission() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", new byte[100]);
        this.mockMvc.perform(multipart("/upload/image")
                .file(file))
                .andExpect(status().is(403));
    }

//    @Test
//    @WithMockUser(roles = {"NORMAL", "ADMIN"})
//    public void uploadImageSuccess() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("file", new byte[100]);
//        MvcResult mvcResult = this.mockMvc.perform(multipart("/upload/image")
//                .file(file))
//                .andExpect(status().is(200))
//                .andReturn();
//        String filename = mvcResult.getResponse().getContentAsString();
//
//        File imgFile = new File(imageDir + filename);
//        imgFile.delete();
//    }

    @Test
    public void uploadBookUnauthorized() throws Exception {
        String body = "{\"title\":\"111\",\"author\":null,\"publisher\":null,\"publishDate\":null,\"pages\":null,\"price\":\"222\",\"decoration\":null,\"isbn\":\"333\",\"score\":null,\"desc\":null,\"img\":\"a1ab759e3204d0cd19969c38ed1f19cb.jpg\"}";
        this.mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().is(401));
    }

    @Test
    @WithMockUser(roles = {"NORMAL"})
    public void uploadBookNoPermission() throws Exception {
        String body = "{\"title\":\"111\",\"author\":null,\"publisher\":null,\"publishDate\":null,\"pages\":null,\"price\":\"222\",\"decoration\":null,\"isbn\":\"333\",\"score\":null,\"desc\":null,\"img\":\"a1ab759e3204d0cd19969c38ed1f19cb.jpg\"}";
        this.mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().is(403));
    }

//    @Test
//    @WithMockUser(roles = {"NORMAL", "ADMIN"})
//    public void uploadBookSuccess() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("file", new byte[100]);
//        MvcResult mvcResult = this.mockMvc.perform(multipart("/upload/image")
//                .file(file))
//                .andExpect(status().is(200))
//                .andReturn();
//        String filename = mvcResult.getResponse().getContentAsString();
//
//        String body = String.format("{\"title\":\"111\",\"author\":null,\"publisher\":null,\"publishDate\":null,\"pages\":null,\"price\":\"222\",\"decoration\":null,\"isbn\":\"333\",\"score\":null,\"desc\":null,\"imgFileName\":\"%s\"}", filename);
//        this.mockMvc.perform(post("/books")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body))
//                .andExpect(status().is(200));
//
//        File imgFile = new File(imageDir + filename);
//        imgFile.delete();
//    }
//
//    @Test
//    @WithMockUser(roles = {"NORMAL", "ADMIN"})
//    public void uploadBookNoTitle() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("file", new byte[100]);
//        MvcResult mvcResult = this.mockMvc.perform(multipart("/upload/image")
//                .file(file))
//                .andExpect(status().is(200))
//                .andReturn();
//        String filename = mvcResult.getResponse().getContentAsString();
//
//        String body = String.format("{\"title\":null,\"author\":null,\"publisher\":null,\"publishDate\":null,\"pages\":null,\"price\":\"222\",\"decoration\":null,\"isbn\":\"333\",\"score\":null,\"desc\":null,\"img\":\"%s\"}", filename);
//        this.mockMvc.perform(post("/books")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body))
//                .andExpect(status().is(400));
//
//        File imgFile = new File(imageDir + filename);
//        imgFile.delete();
//    }
//
//    @Test
//    @WithMockUser(roles = {"NORMAL", "ADMIN"})
//    public void uploadBookNoPrice() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("file", new byte[100]);
//        MvcResult mvcResult = this.mockMvc.perform(multipart("/upload/image")
//                .file(file))
//                .andExpect(status().is(200))
//                .andReturn();
//        String filename = mvcResult.getResponse().getContentAsString();
//
//        String body = String.format("{\"title\":\"111\",\"author\":null,\"publisher\":null,\"publishDate\":null,\"pages\":null,\"price\":null,\"decoration\":null,\"isbn\":\"333\",\"score\":null,\"desc\":null,\"img\":\"%s\"}", filename);
//        this.mockMvc.perform(post("/books")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body))
//                .andExpect(status().is(400));
//
//        File imgFile = new File(imageDir + filename);
//        imgFile.delete();
//    }
//
//    @Test
//    @WithMockUser(roles = {"NORMAL", "ADMIN"})
//    public void uploadBookNoISBN() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("file", new byte[100]);
//        MvcResult mvcResult = this.mockMvc.perform(multipart("/upload/image")
//                .file(file))
//                .andExpect(status().is(200))
//                .andReturn();
//        String filename = mvcResult.getResponse().getContentAsString();
//
//        String body = String.format("{\"title\":\"111\",\"author\":null,\"publisher\":null,\"publishDate\":null,\"pages\":null,\"price\":\"222\",\"decoration\":null,\"isbn\":null,\"score\":null,\"desc\":null,\"img\":\"%s\"}", filename);
//        this.mockMvc.perform(post("/books")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(body))
//                .andExpect(status().is(400));
//
//        File imgFile = new File(imageDir + filename);
//        imgFile.delete();
//    }
}
