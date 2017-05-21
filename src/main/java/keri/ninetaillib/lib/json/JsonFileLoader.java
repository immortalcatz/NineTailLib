/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json;

import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonFileLoader {

    public <T> T loadWithParser(ResourceLocation location, IJsonParser<? extends T> parser){
        parser.parse(this.load(location));
        return parser.getData();
    }

    public JSONObject load(ResourceLocation location){
        JSONObject object = null;
        String filePath = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath() + ".json";
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;

        try{
            inputStream = this.getClass().getResourceAsStream(filePath);
            inputStreamReader = new InputStreamReader(inputStream);
            JSONParser parser = new JSONParser();
            object = (JSONObject)parser.parse(inputStreamReader);

            if(inputStreamReader != null){
                inputStreamReader.close();
            }

            if(inputStream != null){
                inputStream.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return object;
    }

}
