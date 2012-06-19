package net.ion.radon.core.security;

import java.sql.SQLException;

import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.procedure.IParameterQueryable;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.security.SecretVerifier;
import org.restlet.security.User;
import org.restlet.security.Verifier;

public class JDBCVerifier extends SecretVerifier {

	private IDBController dc;
	private String query;

	JDBCVerifier(IDBController dc, String query) {
		this.dc = dc;
		this.query = query;
	}

	public int verify(Request request, Response response) {
		int result = Verifier.RESULT_VALID;

		if (request.getChallengeResponse() == null) {
			result = 0;
		} else {
			String identifier = getIdentifier(request, response);
			char secret[] = getSecret(request, response);
			try {
				if (verify(identifier, secret) == 4)
					request.getClientInfo().setUser(new User(identifier));
				else
					result = -1;
			} catch (IllegalArgumentException iae) {
				result = Verifier.RESULT_UNKNOWN;
			}
		}
		return result;
	}

	public int verify(String identifier, char secret[]) {
		return compare(secret, getLocalSecret(identifier)) ? 4 : -1;
	}

	public char[] getLocalSecret(String identifier){
		 try {
			 IParameterQueryable query = dc.createParameterQuery(this.query) ;
			 query.addParam("userid", identifier) ;
			 
			final Rows rows = query.execQuery();
			
			if (rows.first()){
				return rows.getString("password").toCharArray() ;
			} else {
				throw new IllegalArgumentException("not found user") ;
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e); 
		}
	 }
}
