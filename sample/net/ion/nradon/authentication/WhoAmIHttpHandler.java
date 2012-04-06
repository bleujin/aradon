package net.ion.nradon.authentication;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.handler.AbstractHttpHandler;
import net.ion.nradon.handler.authentication.BasicAuthenticationHandler;

/**
 * Simple handler that shows the user who they are logged in as, using plain ol' HTTP
 */
public class WhoAmIHttpHandler extends AbstractHttpHandler {
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        response.header("Content-type", "text/html")
            .content("You are: " + request.data(BasicAuthenticationHandler.USERNAME))
            .end();
    }
}
