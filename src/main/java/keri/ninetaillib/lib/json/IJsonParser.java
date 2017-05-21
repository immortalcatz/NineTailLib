/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json;

import org.json.simple.JSONObject;

public interface IJsonParser<T extends Object> {

    void parse(JSONObject object);

    T getData();

}
