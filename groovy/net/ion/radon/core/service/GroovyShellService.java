package net.ion.radon.core.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Bruce Fancher
 * 
 */
public class GroovyShellService extends GroovyService {

    private ServerSocket serverSocket;
    private int socket;
    private List<GroovyShellThread>threads = new ArrayList<GroovyShellThread>();

    public GroovyShellService() {
        super();
    }

    public GroovyShellService(int socket) {
        super();
        this.socket = socket;
    }

    public GroovyShellService(Map bindings, int socket) {
        super(bindings);
        this.socket = socket;
    }
    
    public synchronized void launch() {
        logger.info("GroovyShellService launch()");

        try {
            serverSocket = new ServerSocket(socket);
            logger.info("GroovyShellService launch() serverSocket: " + serverSocket);

            while (! isShutdownRequested()) {
                Socket clientSocket = null;
                try {
                	//serverSocket.setSoTimeout(1000) ;
                    clientSocket = serverSocket.accept();
                    logger.info("GroovyShellService launch() clientSocket: " + clientSocket);
                }
                catch (IOException e) {
                    logger.debug("e: " + e);
                    return;
                }

                GroovyShellThread clientThread = new GroovyShellThread(clientSocket, createBinding());
                threads.add(clientThread);
                clientThread.start();
            }
        }
        catch (IOException e) {
        	e.printStackTrace() ;
            logger.debug("e: " + e);
            return;
        }
        finally {
            try {
                serverSocket.close();
            }
            catch (IOException e) {
                logger.warn("e: " + e);
                return;
            }
            logger.info("GroovyShellService launch() closed connection");
        }
    }

    private boolean isShutdown = false;
    private synchronized boolean isShutdownRequested() {
		return isShutdown;
	}

	@Override
    public void destroy() {
		isShutdown = true ;
        logger.info("closing serverSocket: " + serverSocket);
        try {
            if (serverSocket != null) serverSocket.close();
            for (GroovyShellThread nextThread : threads)  {
                logger.info("closing nextThread: " + nextThread);
                nextThread.getSocket().close();
            }
        }
        catch (IOException e) {
            logger.warn("e: " + e);
        }
    }

    public void setSocket(final int socket) {
        this.socket = socket;
    }
}
