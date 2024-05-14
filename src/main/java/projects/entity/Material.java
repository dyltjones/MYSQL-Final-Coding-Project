/**
 * 
 */
package projects.entity;

import java.math.BigDecimal;

/**
 * Represents material used in projects.
 *
 */
public class Material {
  private Integer materialId;
  private Integer projectId;
  private String materialName;
  private Integer numRequired;
  private BigDecimal cost;
// getter for material ID
  public Integer getMaterialId() {
    return materialId;
  }
// setter for material ID
  public void setMaterialId(Integer materialId) {
    this.materialId = materialId;
  }
// getter for project ID
  public Integer getProjectId() {
    return projectId;
  }
// setter for project ID
  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }
// getter for material name
  public String getMaterialName() {
    return materialName;
  }
//setter for material name
  public void setMaterialName(String materialName) {
    this.materialName = materialName;
  }
// getter for amount
  public Integer getNumRequired() {
    return numRequired;
  }
// setter for amount 
  public void setNumRequired(Integer numRequired) {
    this.numRequired = numRequired;
  }
// getter for cost
  public BigDecimal getCost() {
    return cost;
  }
// setter for cost
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "ID=" + materialId + ", materialName=" + materialName + ", numRequired=" + numRequired
        + ", cost=" + cost;
  }
}
