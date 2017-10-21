package model;
import java.util.List;

public class PurchasesList {
    private List<PurchaseImpl> purchases;

    public PurchasesList(List<PurchaseImpl> purchases){
        this.purchases=purchases;
    }

    public List<PurchaseImpl> getPurchases(){
        return purchases;
    }
}
