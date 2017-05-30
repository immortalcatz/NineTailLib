/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.json.model;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.math.MathHelper;
import codechicken.lib.vec.*;
import codechicken.lib.vec.uv.UVRotation;
import codechicken.lib.vec.uv.UVScale;
import codechicken.lib.vec.uv.UVTranslation;
import keri.ninetaillib.lib.json.IJsonParser;
import keri.ninetaillib.lib.render.VertexPosition;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SideOnly(Side.CLIENT)
public class JsonModelParser implements IJsonParser<JsonModel> {

    private JsonModel jsonModel = new JsonModel();

    @Override
    public void parse(JSONObject object) {
        JSONArray jsonCuboids = (JSONArray)object.get("cuboids");

        for(Object cuboid : jsonCuboids.toArray()){
            JSONObject jsonCuboid = (JSONObject)cuboid;
            ModelPartData partData = new ModelPartData();

            for(ModelDataElement elementType : ModelDataElement.VALUES){
                Object cuboidElement = jsonCuboid.get(elementType.getKey());

                if(cuboidElement != null){
                    switch(elementType){
                        case NAME:
                            String cuboidName = (String)cuboidElement;
                            partData.setName(cuboidName);
                            break;
                        case BOUNDS:
                            JSONArray jsonBoundsArray = (JSONArray)cuboidElement;
                            double minX = (double)jsonBoundsArray.get(0);
                            double minY = (double)jsonBoundsArray.get(1);
                            double minZ = (double)jsonBoundsArray.get(2);
                            double maxX = (double)jsonBoundsArray.get(3);
                            double maxY = (double)jsonBoundsArray.get(4);
                            double maxZ = (double)jsonBoundsArray.get(5);
                            partData.setBounds(new Cuboid6(minX, minY, minZ, maxX, maxY, maxZ));
                            break;
                        case TEXTURE:
                            if(cuboidElement instanceof JSONObject){
                                JSONObject jsonTexture = (JSONObject)cuboidElement;

                                for(EnumFacing side : EnumFacing.VALUES){
                                    ResourceLocation texture = new ResourceLocation((String)jsonTexture.get(side.getName()));
                                    partData.setTexture(texture, side);
                                }
                            }
                            else{
                                ResourceLocation texture = new ResourceLocation((String)cuboidElement);
                                partData.setTexture(texture);
                            }

                            break;
                        case TRANSLATION:
                            JSONArray jsonTranslationArray = (JSONArray)cuboidElement;
                            double translationX = (double)jsonTranslationArray.get(0);
                            double translationY = (double)jsonTranslationArray.get(1);
                            double translationZ = (double)jsonTranslationArray.get(2);
                            partData.addTransformation(new Translation(new Vector3(translationX, translationY, translationZ)));
                            break;
                        case ROTATION:
                            JSONArray jsonRotationArray = (JSONArray)cuboidElement;
                            double rotationAngle = (double)jsonRotationArray.get(0) * MathHelper.torad;
                            double rotationAxisX = (double)jsonRotationArray.get(1);
                            double rotationAxisY = (double)jsonRotationArray.get(2);
                            double rotationAxisZ = (double)jsonRotationArray.get(3);
                            double rotationOriginX = (double)jsonRotationArray.get(4) / 16D;
                            double rotationOriginY = (double)jsonRotationArray.get(5) / 16D;
                            double rotationOriginZ = (double)jsonRotationArray.get(6) / 16D;
                            Vector3 rotationAxis = new Vector3(rotationAxisX, rotationAxisY, rotationAxisZ);
                            Vector3 rotationOrigin = new Vector3(rotationOriginX, rotationOriginY, rotationOriginZ);
                            partData.addTransformation(new Rotation(rotationAngle, rotationAxis).at(rotationOrigin));
                            break;
                        case SCALE:
                            JSONArray jsonScaleArray = (JSONArray)cuboidElement;
                            double scaleX = (double)jsonScaleArray.get(0);
                            double scaleY = (double)jsonScaleArray.get(1);
                            double scaleZ = (double)jsonScaleArray.get(2);
                            partData.addTransformation(new Scale(new Vector3(scaleX, scaleY, scaleZ)));
                            break;
                        case COLOR:
                            if(cuboidElement instanceof JSONObject){
                                JSONObject jsonColor = (JSONObject)cuboidElement;

                                for(EnumFacing side : EnumFacing.VALUES){
                                    Object sideColor = jsonColor.get(side.getName());

                                    if(sideColor instanceof JSONObject){
                                        JSONObject jsonSideColor = (JSONObject)sideColor;

                                        for(VertexPosition vertexPosition : VertexPosition.VALUES){
                                            JSONArray jsonVertexColorArray = (JSONArray)jsonSideColor.get(vertexPosition.getName());
                                            int r = (int)(long)jsonVertexColorArray.get(0);
                                            int g = (int)(long)jsonVertexColorArray.get(1);
                                            int b = (int)(long)jsonVertexColorArray.get(2);
                                            int a = (int)(long)jsonVertexColorArray.get(3);
                                            partData.setColor(new ColourRGBA(r, g, b, a), side, vertexPosition);
                                        }
                                    }
                                    else{
                                        JSONArray jsonSideColorArray = (JSONArray)sideColor;
                                        int r = (int)(long)jsonSideColorArray.get(0);
                                        int g = (int)(long)jsonSideColorArray.get(1);
                                        int b = (int)(long)jsonSideColorArray.get(2);
                                        int a = (int)(long)jsonSideColorArray.get(3);
                                        partData.setColor(new ColourRGBA(r, g, b, a), side);
                                    }
                                }
                            }
                            else{
                                JSONArray jsonColorArray = (JSONArray)cuboidElement;
                                int r = (int)(long)jsonColorArray.get(0);
                                int g = (int)(long)jsonColorArray.get(1);
                                int b = (int)(long)jsonColorArray.get(2);
                                int a = (int)(long)jsonColorArray.get(3);
                                partData.setColor(new ColourRGBA(r, g, b, a));
                            }

                            break;
                        case BRIGHTNESS:
                            int brightness = (int)(long)cuboidElement;
                            partData.setBrightnessOverride(brightness);
                            break;
                        case UV_TRANSLATION:
                            JSONArray jsonUvTranslationArray = (JSONArray)cuboidElement;
                            double translationU = (double)jsonUvTranslationArray.get(0);
                            double translationV = (double)jsonUvTranslationArray.get(1);
                            partData.addUVTransformation(new UVTranslation(translationU, translationV));
                            break;
                        case UV_ROTATION:
                            double uvRotation = (double)cuboidElement;
                            partData.addUVTransformation(new UVRotation(uvRotation));
                            break;
                        case UV_SCALE:
                            JSONArray jsonUvScaleArray = (JSONArray)cuboidElement;
                            double scaleU = (double)jsonUvScaleArray.get(0);
                            double scaleV = (double)jsonUvScaleArray.get(1);
                            partData.addUVTransformation(new UVScale(scaleU, scaleV));
                            break;
                    }
                }
            }

            this.jsonModel.addModelPartData(partData);
        }
    }

    @Override
    public JsonModel getData() {
        return this.jsonModel;
    }

}
