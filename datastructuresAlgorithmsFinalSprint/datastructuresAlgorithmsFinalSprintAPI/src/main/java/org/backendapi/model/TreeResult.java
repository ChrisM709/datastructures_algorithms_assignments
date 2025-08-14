package org.backendapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TreeResult {
  @Id @GeneratedValue
  private Long id;

  private String inputNumbers;  

  @Lob
  private String treeJson;  

  private LocalDateTime createdAt = LocalDateTime.now();

  public TreeResult() {}
  public TreeResult(String inputNumbers, String treeJson) {
    this.inputNumbers = inputNumbers;
    this.treeJson = treeJson;
  }

  public Long getId() { return id; }
  public String getInputNumbers() { return inputNumbers; }
  public String getTreeJson() { return treeJson; }
  public LocalDateTime getCreatedAt() { return createdAt; }
}
