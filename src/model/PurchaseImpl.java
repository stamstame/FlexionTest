package model;

import com.flexionmobile.codingchallenge.integration.Purchase;

public class PurchaseImpl implements Purchase {

    private String id, itemId;
    private boolean consumed;

    public PurchaseImpl(String id, String itemId, boolean consumed){
        this.id=id;
        this.itemId=itemId;
        this.consumed=consumed;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean getConsumed() {
        return this.consumed;
    }

    @Override
    public String getItemId() {
        return this.itemId;
    }
}
