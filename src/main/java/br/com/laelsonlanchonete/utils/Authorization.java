package br.com.laelsonlanchonete.utils;

import io.vertx.core.http.HttpServerRequest;

public class Authorization {

	public static boolean isAuthorized(HttpServerRequest request) {
		return !(request.getCookie("LOGIN") == null || !request.getCookie("LOGIN").getValue().equalsIgnoreCase("true"));
	}
}
