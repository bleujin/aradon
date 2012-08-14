package net.ion.radon.util;

import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ObjectId;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.ServerResource;

public class AradonTester {

	private Aradon aradon;

	private AradonTester(Aradon aradon) {
		this.aradon = aradon;
	}
	public final static AradonTester load(Aradon aradon) throws Exception {
		return new AradonTester(aradon) ;
	}

	public final static AradonTester create() throws Exception {
		Aradon aradon = Aradon.create(ConfigurationBuilder.newBuilder().build()) ;
		aradon.start();
		return new AradonTester(aradon);
	}

	public AradonTester register(String sectionName, String urls, Class<? extends ServerResource> handlerClz) throws Exception {
		register(sectionName, urls, IMatchMode.EQUALS, "", handlerClz);
		return this;
	}

	public AradonTester register(String sectionName, String urls, IMatchMode matchMode, Class<? extends ServerResource> handlerClz) throws Exception {
		register(sectionName, urls, matchMode, "", handlerClz);
		return this;
	}

	public AradonTester register(String sectionName, String urls, String name, IMatchMode matchMode, Class<? extends ServerResource> handlerClz) throws Exception {
		SectionService ss = aradon.containsSection(sectionName) ? aradon.getChildService(sectionName) : aradon.attach(SectionConfiguration.createBlank(sectionName));
		ss.attach(PathConfiguration.create(name, urls, "", matchMode, handlerClz));
		return this;
	}

	
	@Deprecated
	public AradonTester register(String sectionName, String urls, IMatchMode matchMode, String description, Class<? extends ServerResource> handlerClz) throws Exception {
		SectionService ss = aradon.containsSection(sectionName) ? aradon.getChildService(sectionName) : aradon.attach(SectionConfiguration.createBlank(sectionName));
		ss.attach(PathConfiguration.create(new ObjectId().toString(), urls, description, matchMode, handlerClz));
		return this;
	}


	public Response get(String path) {
		return aradon.handle(new Request(Method.GET, "riap://component" + path));
	}

	public Response put(String path) {
		return aradon.handle(new Request(Method.PUT, "riap://component" + path));
	}

	public Response delete(String path) {
		return aradon.handle(new Request(Method.DELETE, "riap://component" + path));
	}

	public Response post(String path) {
		return aradon.handle(new Request(Method.POST, "riap://component" + path));
	}

	public Response handle(Request request) {
		return aradon.handle(request);
	}

	public Aradon getAradon() {
		return aradon;
	}

	public void startServerNJoin(int portNum) throws Exception {
		getAradon().startServer(portNum);
		new InfinityThread().startNJoin();
	}

	public void startServer(int portNum) throws Exception {
		getAradon().startServer(portNum);
	}

	public FakeSection mergeSection(String sectionName) {
		return FakeSection.load(sectionName, this) ;
	}

}
