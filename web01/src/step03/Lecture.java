package step03;

public class Lecture {
  int lno;
  int mrno;
  int crmno;
  String title;
  String dscp;
  String startDate;
  String endDate;
  int qty;
  int price;
  int totalHours;
  
  
  
  @Override
  public String toString() {
    return "Lecture [lno=" + lno + ", mrno=" + mrno + ", crmno=" + crmno + ", title=" + title + ", dscp=" + dscp
        + ", startDate=" + startDate + ", endDate=" + endDate + ", qty=" + qty + ", price=" + price + ", totalHours="
        + totalHours + "]";
  }
  public int getLno() {
    return lno;
  }
  public void setLno(int lno) {
    this.lno = lno;
  }
  public int getMrno() {
    return mrno;
  }
  public void setMrno(int mrno) {
    this.mrno = mrno;
  }
  public int getCrmno() {
    return crmno;
  }
  public void setCrmno(int crmno) {
    this.crmno = crmno;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getDscp() {
    return dscp;
  }
  public void setDscp(String dscp) {
    this.dscp = dscp;
  }
  public String getStartDate() {
    return startDate;
  }
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }
  public String getEndDate() {
    return endDate;
  }
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
  public int getQty() {
    return qty;
  }
  public void setQty(int qty) {
    this.qty = qty;
  }
  public int getPrice() {
    return price;
  }
  public void setPrice(int price) {
    this.price = price;
  }
  public int getTotalHours() {
    return totalHours;
  }
  public void setTotalHours(int totalHours) {
    this.totalHours = totalHours;
  }
  
  
  

}
