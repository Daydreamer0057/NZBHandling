package warcraft;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class AuctionHouseData {
    private int realmId = 542;
    private static final String WOWHEAD_API_URL = "https://www.wowhead.com/item=";
    String token = getAccessToken();

    public AuctionHouseData(){
        HashSet<Long> listItems  = new HashSet<>();
        try {
            FileReader fr = new FileReader(new File("d://games/world of warcraft/items.txt"));
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            String totalItems = "";
            while(line!=null){
                line = br.readLine();
                if(line!=null){
                    totalItems += line;
                }
            }

            br.close();
            fr.close();

            String[] listItemsString = totalItems.split("i:");
            for(String lineTemp : listItemsString){
                if(!lineTemp.isEmpty()) {
//                    Long id = getItemId(Long.parseLong(lineTemp.trim()));
                    listItems.add(Long.parseLong(lineTemp.trim()));
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }

//        String auctionUrl = "https://eu.api.blizzard.com/data/wow/connected-realm/1390/auctions?namespace=dynamic-eu&locale=en_US&access_token="+token;
        String auctionUrl = "https://eu.api.blizzard.com/data/wow/auctions/commodities?namespace=dynamic-eu&locale=en_US&access_token="+token;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(auctionUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("Response Code: " + response.getCode());

                String result = EntityUtils.toString(response.getEntity());

                HashMap<Long, ArrayList<AuctionItem>> mapItems = getMapItems(result,listItems);

                mapItems = sortItems(mapItems);

                getInterval(mapItems);

                httpClient.close();
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

    public HashMap<Long, ArrayList<AuctionItem>> getMapItems(String result, HashSet<Long> set){
        HashMap<Long, ArrayList<AuctionItem>> mapAuctions = new HashMap<>();
        try {
            // Initialize ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
////            Root root = om.readValue(result, Root.class);
////            List<Auction> listItems = objectMapper.readValue(result, new TypeReference<List<Auction>>(){});
////            List<Auction> auctions = objectMapper.readValue(result, new TypeReference<List<Auction>>() {});
//
//            AuctionHouseDataItems auctionHouseDataItems = objectMapper.readValue(result, AuctionHouseDataItems.class);
//            List<Auction> auctions = auctionHouseDataItems.getAuctions();
//
//            for(Auction auctionTemp : auctions) {
//                if (set.contains(auctionTemp.item.id)) {
//                    ArrayList<AuctionItem> listAuctionItem = new ArrayList<>();
//                    if (mapAuctions.get(auctionTemp.item.id) != null) {
//                        listAuctionItem = mapAuctions.get(auctionTemp.item.id);
//                        listAuctionItem.add(new AuctionItem(auctionTemp.quantity, auctionTemp.unit_price));
//                        mapAuctions.put(auctionTemp.item.id, listAuctionItem);
//                    } else {
//                        listAuctionItem.add(new AuctionItem(auctionTemp.quantity, auctionTemp.unit_price));
//                        mapAuctions.put(auctionTemp.item.id, listAuctionItem);
//                    }
//                }
//            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return mapAuctions;
    }

    public HashMap<Long, ArrayList<AuctionItem>> sortItems(HashMap<Long, ArrayList<AuctionItem>> map){
        HashMap<Long, ArrayList<AuctionItem>> mapTemp = new HashMap<>();
        for(Long id : map.keySet()) {
            ArrayList<AuctionItem> listItems = map.get(id);
            listItems.sort(new Comparator<AuctionItem>() {
                @Override
                public int compare(AuctionItem o1, AuctionItem o2) {
                    int retour = 0;
                    if (o1.price < o2.price) retour = -1;
                    if (o1.price == o2.price) retour = 0;
                    if (o1.price > o2.price) retour = 1;
                    return retour;
                }
            });
            mapTemp.put(id,listItems);
        }

        return mapTemp;
    }

    public void getInterval(HashMap<Long, ArrayList<AuctionItem>> map){
        try {
            PrintWriter pw = new PrintWriter(new File("d://games/world of warcraft/items_to_buy.txt"));

            for(Long auctionTemp : map.keySet()){
                ArrayList<AuctionItem> listAuction = map.get(auctionTemp);

                if(!listAuction.isEmpty()){
                    long cout = 0;
                    ItemToBuy itemToBuy = new ItemToBuy(0,0);
                    double percent = 0;
                    for(int i=0;i<listAuction.size()-1;i++){
                        AuctionItem itemTemp = listAuction.get(i);
                        cout += (itemTemp.price * itemTemp.quantity);
                        if(cout < 100000000L){
                            AuctionItem itemTempNext = listAuction.get(i+1);
                            long priceNext = itemTempNext.price;
                            long priceCurrent = itemTemp.price;
                            double percentage = ((double) priceNext / priceCurrent * 100) - 100;
                            if(percentage > 20){
                                percent = percentage;
                                itemToBuy = new ItemToBuy(auctionTemp, priceCurrent);
//                                break;
                            }
                        }
                    }
                    pw.println("i:"+itemToBuy.id+";"+itemToBuy.price);
                    pw.flush();
                }
            }

            pw.flush();
            pw.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static Long getItemId(long itemId) throws Exception {
        String url = WOWHEAD_API_URL + itemId;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
//        HttpResponse response = client.execute(request);
//
//        JSONObject jsonObject = new JSONObject(response);

        CloseableHttpResponse response = httpClient.execute(request);
        System.out.println("Response Code: " + response.getCode());
        String jsonResponse = EntityUtils.toString(response.getEntity());

        ObjectMapper mapper = new ObjectMapper();

//        JsonNode rootNode = mapper.readTree(jsonResponse);
//        JsonNode queueNode = rootNode.path("queue");
//        JsonNode itemsNode = queueNode.path("slots");

//        Long id = jsonObject.getLong("id");

        httpClient.close();
        return 0L;
    }

    public static void main(String[] args) {
        AuctionHouseData a = new AuctionHouseData();
    }



    public static class Auction{
        public long id;
        public Item item;
        public long unit_price;
        public long quantity;
        public String time_left;
        public long bid;
    }

    public static class Commodities{
        public String href;
    }

    public static class ConnectedRealm{
        public String href;
    }

    public static class Item{
        public long id;
        public long context;
        public ArrayList<Long> bonus_lists;
        public ArrayList<Modifier> modifiers;
    }

    public static class Links{
        public Self self;
    }

    public static class Modifier{
        public long type;
        public long value;
    }

    public static class Root{
        public Links _links;
        public ConnectedRealm connected_realm;
        public ArrayList<Auction> auctions;
        public Commodities commodities;
    }

    public static class Self{
        public String href;
    }

    public static class AuctionItem{
        public long quantity;
        public long price;

        public AuctionItem(long quantity, long price){
            this.quantity = quantity;
            this.price = price;
        }
    }

    public static class AuctionHouseDataItems {
        private List<Auction> auctions;

        public List<Auction> getAuctions() {
            return auctions;
        }

        public void setAuctions(List<Auction> auctions) {
            this.auctions = auctions;
        }
    }

    public class ItemToBuy {
        public long id;
        public long price;

        public ItemToBuy(long id, long price) {
            this.id = id;
            this.price = price;
        }
    }
}
