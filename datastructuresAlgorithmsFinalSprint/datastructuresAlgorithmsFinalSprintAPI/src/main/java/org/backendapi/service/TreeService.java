package org.backendapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backendapi.model.TreeResult;
import org.backendapi.repository.TreeResultRepository;
import org.backendapi.util.BinarySearchTree;
import org.backendapi.util.TreeNode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TreeService {
  private final TreeResultRepository repo;
  private final ObjectMapper mapper = new ObjectMapper();

  public TreeService(TreeResultRepository repo) {
    this.repo = repo;
  }

  public TreeResult createAndSave(String numbersCsv) throws Exception {
    List<Integer> nums = parseCsv(numbersCsv);

    // build a regular BST by sequential inserts
    BinarySearchTree bst = new BinarySearchTree();
    for (int n : nums) {
      bst.insert(n);
    }
    TreeNode root = bst.getRoot();

    // inline serialization: convert to a plain Map -> JSON string
    String json = mapper.writeValueAsString(toPojo(root));

    return repo.save(new TreeResult(numbersCsv, json));
  }

  public List<TreeResult> all() { return repo.findAll(); }

  public TreeResult get(Long id) { return repo.findById(id).orElse(null); }

  private List<Integer> parseCsv(String csv) {
    if (csv == null || csv.isBlank()) {
      throw new IllegalArgumentException("numbers cannot be empty");
    }
    return Arrays.stream(csv.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .map(Integer::parseInt)
        .toList();
  }

  // Convert the tree to a simple POJO structure { value, left, right }
  // so Jackson produces clean JSON.
  private Object toPojo(TreeNode node) {
    if (node == null) return null;
    Map<String, Object> m = new LinkedHashMap<>();
    // If your TreeNode fields are private, replace with getters (e.g., node.getValue()).
    m.put("value", node.value);
    m.put("left",  toPojo(node.left));
    m.put("right", toPojo(node.right));
    return m;
  }
}
