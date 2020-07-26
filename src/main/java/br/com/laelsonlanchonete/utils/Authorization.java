package br.com.laelsonlanchonete.utils;

import io.vertx.core.http.HttpServerRequest;

public class Authorization {

	public static boolean isAuthorized(HttpServerRequest request) {
		if (request.getCookie("LOGIN") == null || !request.getCookie("LOGIN").getValue().equals("true")) {
			return false;
		}
		return true;
	}
}
