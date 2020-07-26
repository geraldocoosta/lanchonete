package br.com.laelsonlanchonete.utils;

import javax.ws.rs.core.NewCookie;

public class CookieUtil {

	public static NewCookie createCookieLogin() {
		
        int tempoExpiracao = 12 * 60;
		
		return new NewCookie("LOGIN",    			// nome do cookie
						"true",						// valor do cookie
						"/", 						// path do cookie
						null,						// dominio do cookie
						NewCookie.DEFAULT_VERSION, 	// versão do cookie
						null, 						// comentario do cookie ( ? )
						tempoExpiracao,				// tempo de duração do cookie 
						null, 						// tempo de expiração do cookie
						false, 						// cookie trafega em https
						false);						// cookie é http only
		
	}

}
