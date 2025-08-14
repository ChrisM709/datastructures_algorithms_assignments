package org.backendapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.backendapi.model.TreeResult;
import org.backendapi.repository.TreeResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TreeServiceTest {

  @Autowired TreeService service;
  @Autowired TreeResultRepository repo;

  @BeforeEach
  void clean() {
    repo.deleteAll();
  }

  @Test
  void createAndSavePersists() throws Exception {
    long before = repo.count();

    TreeResult r = service.createAndSave("4,2,8,3");

    assertNotNull(r.getId());
    assertNotNull(r.getTreeJson());
    assertEquals(before + 1, repo.count());
  }

  @Test
  void createsCorrectBasicShape() throws Exception {
    TreeResult r = service.createAndSave("5,1,7");
    JsonNode tree = new ObjectMapper().readTree(r.getTreeJson());

    assertEquals(5, tree.get("value").asInt());
    assertEquals(1, tree.get("left").get("value").asInt());
    assertEquals(7, tree.get("right").get("value").asInt());
  }

  @Test
  void rejectsEmptyInput() {
    assertThrows(IllegalArgumentException.class, () -> service.createAndSave(" , , "));
  }
}
