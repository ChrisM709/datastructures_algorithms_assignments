package org.backendapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.backendapi.dto.TreeRequest;
import org.backendapi.dto.TreeResponse;
import org.backendapi.model.TreeResult;
import org.backendapi.service.TreeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
@CrossOrigin(origins = {
  "http://localhost:5173", "http://127.0.0.1:5173", "http://localhost:3000"
})
public class TreeController {

  private final TreeService service;
  private final ObjectMapper mapper = new ObjectMapper();

  public TreeController(TreeService service){ this.service = service; }

  @PostMapping
  public TreeResponse create(@RequestBody TreeRequest req) throws Exception {
    TreeResult saved = service.createAndSave(req.numbers());
    JsonNode tree = mapper.readTree(saved.getTreeJson());
    return new TreeResponse(saved.getId(), saved.getInputNumbers(), tree);
  }

  @GetMapping
  public List<TreeResponse> list() {
    return service.all().stream().map(r -> {
      try { return new TreeResponse(r.getId(), r.getInputNumbers(), mapper.readTree(r.getTreeJson())); }
      catch (Exception e) { return new TreeResponse(r.getId(), r.getInputNumbers(), mapper.createObjectNode()); }
    }).toList();
  }

  @GetMapping("/{id}")
  public TreeResponse one(@PathVariable Long id) throws Exception {
    TreeResult r = service.get(id);
    if (r == null) return new TreeResponse(null, "", mapper.createObjectNode());
    return new TreeResponse(r.getId(), r.getInputNumbers(), mapper.readTree(r.getTreeJson()));
  }
}
