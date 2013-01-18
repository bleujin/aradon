package net.ion.nradon;

import java.util.concurrent.Executor;

public interface HttpConnection extends Executor, DataHolder{
    HttpRequest httpRequest();

    HttpConnection close();

    Executor handlerExecutor();
}
