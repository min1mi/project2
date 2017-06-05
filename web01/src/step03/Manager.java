package step03;

public class Manager {
  int no;
  String name;
  String position;
  String fax;
  String path;
  
  @Override
  public String toString() {
    return "Manager [no=" + no + ", position=" + position + ", fax=" + fax + ", path=" + path + "]";
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
  

  
}
