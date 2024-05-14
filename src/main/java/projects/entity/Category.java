/**
 * 
 */
package projects.entity;
/**
 *  represents a category of projects. Each category has a 
 *  unique identifier and and a name.
 */

public class Category {
  private Integer categoryId;
  private String categoryName;
// getter for Category ID
  public Integer getCategoryId() {
    return categoryId;
  }
// setter for Category ID
  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }
// getter for Category name
  public String getCategoryName() {
    return categoryName;
  }
// setter for Category name
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  @Override
  public String toString() {
    return "ID=" + categoryId + ", categoryName=" + categoryName;
  }
}
