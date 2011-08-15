package net.ion.radon.core.security;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ObjectUtil;
import net.ion.radon.core.IService;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeRequest;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Status;
import org.restlet.security.Verifier;

public class ChallengeAuthenticator extends Authenticator {

	private volatile String realm;
	private volatile boolean rechallenging;
	private final ChallengeScheme scheme;
	private volatile Verifier verifier;

	public ChallengeAuthenticator(String realm) {
		this(ChallengeScheme.HTTP_BASIC, realm, new SimpleVerifier());
	}

	public ChallengeAuthenticator(String realm, Verifier verifier) {
		this(ChallengeScheme.HTTP_BASIC, realm, verifier);
	}
	public ChallengeAuthenticator(ChallengeScheme challengeScheme, String realm, Verifier verifier) {
		this.realm = realm;
		this.rechallenging = true;
		this.scheme = challengeScheme;
		this.verifier = verifier;
	}

	@Override
	public void init(IService service){
		super.init(service) ;
		this.verifier = ObjectUtil.coalesce(this.verifier, new SimpleVerifier()) ;
	}
	
	protected boolean authenticate(Request request, Response response) {
		boolean result = false;
		if (getVerifier() == null) {
			Debug.warn("Authentication failed. No verifier provided.");
			return false;
		}

		switch (getVerifier().verify(request, response)) {
		case Verifier.RESULT_VALID: // '\004'
			result = true;
			break;

		case Verifier.RESULT_MISSING: // '\0'
			if (!isOptional())
				challenge(response, false);
			break;

		case Verifier.RESULT_INVALID:
			if (!isOptional())
				if (isRechallenging())
					challenge(response, false);
				else
					forbid(response);
			break;

		case Verifier.RESULT_STALE: // '\001'
			if (!isOptional())
				challenge(response, true);
			break;

		case Verifier.RESULT_UNKNOWN: // '\005'
			if (!isOptional())
				if (isRechallenging())
					challenge(response, false);
				else
					forbid(response);
			break;
		}
		return result;
	}

	public void challenge(Response response, boolean stale) {
		response.setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
		response.getChallengeRequests().add(createChallengeRequest(stale));
	}

	protected ChallengeRequest createChallengeRequest(boolean stale) {
		return new ChallengeRequest(getScheme(), getRealm());
	}

	public void forbid(Response response) {
		response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
	}

	public String getRealm() {
		return realm;
	}

	public ChallengeScheme getScheme() {
		return scheme;
	}

	public Verifier getVerifier() {
		return verifier;
	}

	public boolean isRechallenging() {
		return rechallenging;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public void setRechallenging(boolean rechallenging) {
		this.rechallenging = rechallenging;
	}

	public void setVerifier(Verifier verifier) {
		this.verifier = verifier;
	}

}
