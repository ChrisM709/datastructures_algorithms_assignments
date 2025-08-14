package org.backendapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TreeControllerTest {
  @Autowired MockMvc mvc;

  @Test
  void postCreatesTree() throws Exception {
    mvc.perform(post("/api/trees")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"numbers\":\"4,2,8,3\"}"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.tree.value").value(4));
  }
}
