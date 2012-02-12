package net.ion.radon.impl.let;

import net.ion.framework.parse.gson.JsonArray;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

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
        JsonObject json = JsonParser.fromString(response.getEntityAsText()).getAsJsonObject() ;

        // Navigate within the JSON document to display the titles
        JsonObject resultSet = json.asJsonObject("ResultSet") ;
        JsonArray results = resultSet.asJsonArray("Result");
        for (int i = 0; i < results.size(); i++) {
            System.out.println(results.asJsonObject(i).asString("Title"));
        }
    }
}
