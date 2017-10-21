package client;

import com.flexionmobile.codingchallenge.integration.Integration;
import com.flexionmobile.codingchallenge.integration.IntegrationTestRunner;
import com.flexionmobile.codingchallenge.integration.Purchase;
import com.google.gson.Gson;
import model.PurchaseImpl;
import model.PurchasesList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IntegrationImpl implements Integration {

    private static final String FLEXION_URL = "http://sandbox.flexionmobile.com/javachallenge/rest/developer/";
    private static final String DEVELOPERID = "StamatinaStamelaki";
    private static final String BUY = "/buy/";
    private static final String ALL = "/all";
    private static final String CONSUME = "/consume/";
    private URL url;
    private HttpURLConnection conn;

    @Override
    public Purchase buy(String s) {
        Purchase purchase;
        try {

            url = new URL(FLEXION_URL + DEVELOPERID + BUY + s);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new IllegalStateException("Resource not found");
            }

            InputStream input = conn.getInputStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            purchase = new Gson().fromJson(reader, PurchaseImpl.class);
            conn.disconnect();

        } catch (IOException e) {
            throw new IllegalStateException("Input\\Output not as expected\n");
        }
        return purchase;
    }

    @Override
    public List<Purchase> getPurchases() {

        PurchasesList purchasesList;
        try {
            url = new URL(FLEXION_URL + DEVELOPERID + ALL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new IllegalStateException("Resource not found");
            }

            InputStream input = conn.getInputStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            purchasesList = new Gson().fromJson(reader, PurchasesList.class);

            conn.disconnect();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }

        return new ArrayList<>(purchasesList.getPurchases());
    }

    @Override
    public void consume(Purchase purchase) {

        try {

            url = new URL(FLEXION_URL + DEVELOPERID + CONSUME + purchase.getId());
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new IllegalStateException("Resource not found");
            }

            conn.disconnect();

        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }


    public static void main(String args[]) {

        Integration integration = new IntegrationImpl();
        IntegrationTestRunner integrationTestRunner = new IntegrationTestRunner();
        integrationTestRunner.runTests(integration);

    }
}
