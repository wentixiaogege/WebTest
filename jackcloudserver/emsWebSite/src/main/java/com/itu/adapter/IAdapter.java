package com.itu.adapter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public interface IAdapter<T> {
	JSONObject convertToJson(T t) throws JSONException;
}
