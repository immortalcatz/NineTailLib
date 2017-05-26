/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json.model;

import keri.ninetaillib.lib.json.IJsonParser;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SideOnly(Side.CLIENT)
public class JsonModelParser implements IJsonParser<ModelData> {

    private ModelData modelData = new ModelData();

    @Override
    public void parse(JSONObject object) {
        JSONArray jsonCuboids = (JSONArray)object.get("cuboids");

        for(Object cuboid : jsonCuboids.toArray()){
            JSONObject jsonCuboid = (JSONObject)cuboid;
            //TODO: do things...
        }
    }

    @Override
    public ModelData getData() {
        return this.modelData;
    }

}
