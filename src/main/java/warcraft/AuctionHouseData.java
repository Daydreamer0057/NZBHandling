package warcraft;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;

public class AuctionHouseData {
    private int realmId = 542;
    private static final String WOWHEAD_API_URL = "https://www.wowhead.com/item=";
    String token = getAccessToken();

    public AuctionHouseData(){
String auctionUrl = "https://eu.api.blizzard.com/data/wow/connected-realm/1390/auctions?namespace=dynamic-eu&locale=en_US&access_token="+token;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(auctionUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("Response Code: " + response.getCode());

                httpClient.close();

                String result = EntityUtils.toString(response.getEntity());

                HashMap<Integer, ArrayList<AuctionItem>> mapItems = getMapItems(result);

                mapItems = sortItems(mapItems);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String token  = getAccessToken();
//        String API_URL = "https://eu.api.blizzard.com/";
//        String ConnectRealms_URL = "https://eu.api.blizzard.com/data/wow/search/connected-realm?namespace=dynamic-eu&realms.name.en_US=hyjal&access_token="+token;
    }




    public String getAccessToken(){
        String TOKEN_URL = "https://eu.battle.net/oauth/token"; // Replace with your token URL
        String CLIENT_ID = "652651cc7f04402ea349c51415a6787f";
        String CLIENT_SECRET = "RBgCazanJADsZcLOlOXHe7UrtqE1wH85";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(TOKEN_URL);

            // Base64 encode the client_id and client_secret
            String auth = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            post.setHeader("Authorization", "Basic " + encodedAuth);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            StringEntity entity = new StringEntity("grant_type=client_credentials");
            post.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                String result = EntityUtils.toString(response.getEntity());

                httpClient.close();

                JSONObject jsonObject = new JSONObject(result);

                return jsonObject.getString("access_token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public HashMap<Integer, ArrayList<AuctionItem>> getMapItems(String result){
        HashMap<Integer, ArrayList<AuctionItem>> mapAuctions = new HashMap<>();
        try {
            ObjectMapper om = new ObjectMapper();
            Root root = om.readValue(result, Root.class);

            for(Auction auctionTemp : root.auctions){
                if(mapAuctions.get(auctionTemp.item.id)!=null) {
                    ArrayList<AuctionItem> listAuctionItem = mapAuctions.get(auctionTemp.item.id);
                    if(listAuctionItem==null){
                        listAuctionItem = new ArrayList<>();
                        mapAuctions.put(auctionTemp.item.id, listAuctionItem);
                    } else {
                        AuctionItem auctionItem = new AuctionItem(auctionTemp.quantity, auctionTemp.buyout);
                        listAuctionItem.add(auctionItem);
                        mapAuctions.put(auctionTemp.item.id, listAuctionItem);
                    }
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return mapAuctions;
    }

    public HashMap<Integer, ArrayList<AuctionItem>> verifyClassItems(HashMap<Integer, ArrayList<AuctionItem>> map) {
        for (Integer id : map.keySet()) {
            String auctionUrl = "https://us.api.blizzard.com/data/wow/item/" + id + "?namespace=static-eu&locale=en_US&access_token=" + token;
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(auctionUrl);
                try (CloseableHttpResponse response = httpClient.execute(request)) {
                    System.out.println("Response Code: " + response.getCode());

                    httpClient.close();

                    String jsonResponse = EntityUtils.toString(response.getEntity());

                    // Parse JSON response
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(jsonResponse);

                    // Extract item class and subclass
                    String itemClass = rootNode.get("item_class").get("name").asText();
                    String itemSubclass = rootNode.get("item_subclass").get("name").asText();

                    if(!(itemSubclass.toLowerCase().contains("potions")||
                            itemSubclass.toLowerCase().contains("elixirs")||
                            itemSubclass.toLowerCase().contains("flasks")||
                            itemSubclass.toLowerCase().contains("reagent")||
                            (itemSubclass.toLowerCase().contains("cloth")&&itemClass.toLowerCase().contains("trade"))||
                            (itemSubclass.toLowerCase().contains("leather")&&itemClass.toLowerCase().contains("trade"))||
                            itemSubclass.toLowerCase().contains("metal")||
                            itemSubclass.toLowerCase().contains("cooking")||
                            itemSubclass.toLowerCase().contains("herb")||
                            itemSubclass.toLowerCase().contains("elemental"))){
                        map.remove(id);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public HashMap<Integer, ArrayList<AuctionItem>> sortItems(HashMap<Integer, ArrayList<AuctionItem>> map){
        for(Integer id : map.keySet()) {
            ArrayList<AuctionItem> listItems = map.get(id);
            listItems.sort(new Comparator<AuctionItem>() {
                @Override
                public int compare(AuctionItem o1, AuctionItem o2) {
                    int retour = 0;
                    if (o1.price < o2.price) retour = 1;
                    if (o1.price == o2.price) retour = 0;
                    if (o1.price > o2.price) retour = -1;
                    return retour;
                }
            });
            map.put(id,listItems);
        }

        return map;
    }

    public void getInterval(HashMap<Integer, ArrayList<Integer>> map){

    }

    public static String getItemName(int itemId) throws Exception {
        String url = WOWHEAD_API_URL + itemId;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        String jsonResponse = EntityUtils.toString(((CloseableHttpResponse) response).getEntity());
        client.close();

        // Parse JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonResponse);

        // Assuming the item name is in a specific field
        String itemName = rootNode.get("name").asText();
        return itemName;
    }

    public static void main(String[] args) {
        AuctionHouseData a = new AuctionHouseData();
    }



    public class Auction{
        public int id;
        public Item item;
        public int buyout;
        public int quantity;
        public String time_left;
        public int bid;
    }

    public class Commodities{
        public String href;
    }

    public class ConnectedRealm{
        public String href;
    }

    public class Item{
        public int id;
        public int context;
        public ArrayList<Integer> bonus_lists;
        public ArrayList<Modifier> modifiers;
    }

    public class Links{
        public Self self;
    }

    public class Modifier{
        public int type;
        public int value;
    }

    public class Root{
        public Links _links;
        public ConnectedRealm connected_realm;
        public ArrayList<Auction> auctions;
        public Commodities commodities;
    }

    public class Self{
        public String href;
    }

    public class AuctionItem{
        public int quantity;
        public int price;

        public AuctionItem(int quantity, int price){
            this.quantity = quantity;
            this.price = price;
        }
    }
}
