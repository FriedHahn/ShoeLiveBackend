package htw.webtech.myapp.rest.model;

import java.util.List;

public class PurchaseRequest {
    private List<Long> adIds;

    public List<Long> getAdIds() { return adIds; }
    public void setAdIds(List<Long> adIds) { this.adIds = adIds; }
}
