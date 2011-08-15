package net.ion.radon;

import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.ext.json.JsonRepresentation;

/**
 * Searching the web with Yahoo!'s web service using JSON.
 * 
 * @author Jerome Louvel (contact@noelios.com)
 */
public class YahooSearch {
    static final String BASE_URI = "http://api.search.yahoo.com/WebSearchService/V1/webSearch";

    public static void main(String[] args) throws Exception {
        // Fetch a resource: a JSON document full of search results
        String term = "database";
        String uri = BASE_URI + "?appid=restbook&output=json&query=" + term;
        Response response = new Client(Protocol.HTTP).handle(new Request(Method.GET, uri));
        JSONObject json = new JsonRepresentation(response.getEntity()).getJsonObject() ;

        // Navigate within the JSON document to display the titles
        JSONObject resultSet = json.getJSONObject("ResultSet");
        JSONArray results = resultSet.getJSONArray("Result");
        for (int i = 0; i < results.length(); i++) {
            System.out.println(results.getJSONObject(i).getString("Title"));
        }
    }
}
