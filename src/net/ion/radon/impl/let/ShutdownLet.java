package net.ion.radon.impl.let;

import java.util.Timer;
import java.util.TimerTask;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;

public class ShutdownLet extends AbstractServerResource {

	@Get
	public String getMyName() {
		return "Hello " + getInnerRequest().getFormParameter().get("name");
	}

	@Delete
	public String suicide(){
		long timeoutMili = Math.min(getInnerRequest().getParameterAsInteger("timeout"), 1000L) ;
		
		final Aradon self = getAradon() ;
		new Timer().schedule(new TimerTask() {
			@Override public void run() {
				self.stop() ;
				System.exit(0)  ;
			}
		}, timeoutMili) ;
		
		return timeoutMili + "(ms) shutdown.." ;
	}
}