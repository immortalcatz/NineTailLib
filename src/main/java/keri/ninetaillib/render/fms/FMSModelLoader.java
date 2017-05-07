package keri.ninetaillib.render.fms;

import codechicken.lib.colour.Colour;
import codechicken.lib.math.MathHelper;
import codechicken.lib.vec.*;
import codechicken.lib.vec.uv.UVTranslation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class FMSModelLoader implements IResourceManagerReloadListener {

    private static final String[] allowedCommentFormat = new String[]{
            "//",
            "#"
    };
    private static final String[] allowedOperations = new String[]{
            "cuboid",
            "translate",
            "scale",
            "rotate",
            "textureAll",
            "texture",
            "textureSpecialAll",
            "textureSpecial",
            "uv",
            "color3",
            "color4",
            "brightness"
    };
    private static Map<String, ResourceLocation> fileLocations = Maps.newHashMap();
    private static Map<String, List<ModelPartData>> models = Maps.newHashMap();
    private static Map<String, TextureAtlasSprite> specialTextures = Maps.newHashMap();

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        models.clear();

        for(Map.Entry<String, ResourceLocation> file : this.fileLocations.entrySet()){
            String modelName = file.getKey();
            ResourceLocation modelLocation = file.getValue();
            List<String> fileContent = this.readFile(modelLocation);
            List<List<Pair<Operation, Object[]>>> operations = Lists.newArrayList();

            for(String line : fileContent){
                List<Pair<Operation, Object[]>> operationLine = Lists.newArrayList();

                for(int possibleOperation = 0; possibleOperation < allowedOperations.length; possibleOperation++){
                    if(line.contains(allowedOperations[possibleOperation])){
                        int operationStart = line.indexOf(allowedOperations[possibleOperation]);
                        int operationEnd = line.indexOf(')', operationStart) + 1;
                        String lineOperation = line.substring(operationStart, operationEnd);
                        Pair<Operation, Object[]> operation = this.parseOperation(lineOperation);
                        operationLine.add(operation);
                    }
                }

                operations.add(operationLine);
            }

            this.models.put(modelName, this.parseModel(operations, specialTextures));
        }
    }

    public void loadModels(FMLPreInitializationEvent event){
        ProgressManager.ProgressBar loadingBar = ProgressManager.push(String.format("%s: loading models", event.getModMetadata().name), fileLocations.size());

        for(Map.Entry<String, ResourceLocation> file : this.fileLocations.entrySet()){
            String modelName = file.getKey();
            ResourceLocation modelLocation = file.getValue();
            List<String> fileContent = this.readFile(modelLocation);
            List<List<Pair<Operation, Object[]>>> operations = Lists.newArrayList();

            for(String line : fileContent){
                List<Pair<Operation, Object[]>> operationLine = Lists.newArrayList();

                for(int possibleOperation = 0; possibleOperation < allowedOperations.length; possibleOperation++){
                    if(line.contains(allowedOperations[possibleOperation])){
                        int operationStart = line.indexOf(allowedOperations[possibleOperation]);
                        int operationEnd = line.indexOf(')', operationStart) + 1;
                        String lineOperation = line.substring(operationStart, operationEnd);
                        Pair<Operation, Object[]> operation = this.parseOperation(lineOperation);
                        operationLine.add(operation);
                    }
                }

                operations.add(operationLine);
            }

            loadingBar.step(String.format("FMS Model: [%s]", modelLocation.toString()));
            this.models.put(modelName, this.parseModel(operations, specialTextures));
        }

        ProgressManager.pop(loadingBar);
    }

    private Pair<Operation, Object[]> parseOperation(String s){
        Pair<Operation, Object[]> operationPair = null;

        if(s.startsWith("cuboid")){
            operationPair = Pair.of(Operation.CUBOID, this.parseDoubleArray(s, 6));
        }
        else if(s.startsWith("translate")){
            operationPair = Pair.of(Operation.TRANSLATION, this.parseDoubleArray(s, 3));
        }
        else if(s.startsWith("scale")){
            operationPair = Pair.of(Operation.SCALE, this.parseDoubleArray(s, 3));
        }
        else if(s.startsWith("rotate")){
            operationPair = Pair.of(Operation.ROTATION, this.parseDoubleArray(s, 7));
        }
        else if(s.startsWith("textureAll")){
            operationPair = Pair.of(Operation.TEXTURE_ALL, this.parseString(s));
        }
        else if(s.startsWith("textureSpecialAll")){
            operationPair = Pair.of(Operation.TEXTURE_SPECIAL_ALL, this.parseString(s));
        }
        else if(s.startsWith("texture")){
            operationPair = Pair.of(Operation.TEXTURE, this.parseStringArray(s, 6));
        }
        else if(s.startsWith("textureSpecial")){
            operationPair = Pair.of(Operation.TEXTURE_SPECIAL, this.parseStringArray(s, 6));
        }
        else if(s.startsWith("uv")){
            operationPair = Pair.of(Operation.UV_TRANSLATION, this.parseDoubleArray(s, 2));
        }
        else if(s.startsWith("color3")){
            operationPair = Pair.of(Operation.COLOR_3, this.parseIntArray(s, 3));
        }
        else if(s.startsWith("color4")){
            operationPair = Pair.of(Operation.COLOR_4, this.parseIntArray(s, 4));
        }
        else if(s.startsWith("brightness")){
            operationPair = Pair.of(Operation.BRIGHTNESS, this.parseInt(s));
        }

        return operationPair;
    }

    private List<ModelPartData> parseModel(List<List<Pair<Operation, Object[]>>> operations, Map<String, TextureAtlasSprite> specialTextures){
        List<ModelPartData> partData = Lists.newArrayList();

        for(List<Pair<Operation, Object[]>> line : operations){
            ModelPartData data = new ModelPartData();

            for(Pair<Operation, Object[]> operation : line){
                Object[] operationArgs = operation.getValue();

                switch(operation.getKey()){
                    case CUBOID:
                        double minX = (double)operationArgs[0] / 16D;
                        double minY = (double)operationArgs[1] / 16D;
                        double minZ = (double)operationArgs[2] / 16D;
                        double maxX = (double)operationArgs[3] / 16D;
                        double maxY = (double)operationArgs[4] / 16D;
                        double maxZ = (double)operationArgs[5] / 16D;
                        data.setBounds(new Cuboid6(minX, minY, minZ, maxX, maxY, maxZ));
                        break;
                    case TRANSLATION:
                        double x = (double)operationArgs[0] / 16D;
                        double y = (double)operationArgs[1] / 16D;
                        double z = (double)operationArgs[2] / 16D;
                        data.addTransformation(new Translation(new Vector3(x, y, z)));
                        break;
                    case SCALE:
                        double scaleX = (double)operationArgs[0] / 16D;
                        double scaleY = (double)operationArgs[1] / 16D;
                        double scaleZ = (double)operationArgs[2] / 16D;
                        data.addTransformation(new Scale(new Vector3(scaleX, scaleY, scaleZ)));
                        break;
                    case ROTATION:
                        double rotation = (double)operationArgs[0] * MathHelper.torad;
                        double axisX = (double)operationArgs[1];
                        double axisY = (double)operationArgs[2];
                        double axisZ = (double)operationArgs[3];
                        double originX = (double)operationArgs[4] / 16D;
                        double originY = (double)operationArgs[5] / 16D;
                        double originZ = (double)operationArgs[6] / 16D;
                        data.addTransformation(new Rotation(rotation, new Vector3(axisX, axisY, axisZ)).at(new Vector3(originX, originY, originZ)));
                        break;
                    case TEXTURE_ALL:
                        ResourceLocation texture = new ResourceLocation((String)operationArgs[0]);
                        data.setTexture(new ResourceLocation[]{texture, texture, texture, texture, texture, texture});
                        break;
                    case TEXTURE:
                        ResourceLocation textureD = new ResourceLocation((String)operationArgs[0]);
                        ResourceLocation textureU = new ResourceLocation((String)operationArgs[1]);
                        ResourceLocation textureN = new ResourceLocation((String)operationArgs[2]);
                        ResourceLocation textureE = new ResourceLocation((String)operationArgs[3]);
                        ResourceLocation textureS = new ResourceLocation((String)operationArgs[4]);
                        ResourceLocation textureW = new ResourceLocation((String)operationArgs[5]);
                        data.setTexture(new ResourceLocation[]{textureD, textureU, textureN, textureE, textureS, textureW});
                        break;
                    case TEXTURE_SPECIAL_ALL:
                        TextureAtlasSprite textureSpecial = specialTextures.get((String)operationArgs[0]);
                        data.setHasSpecialTexture(true);
                        data.setTextureSpecial(new TextureAtlasSprite[]{textureSpecial, textureSpecial, textureSpecial, textureSpecial, textureSpecial, textureSpecial});
                        break;
                    case TEXTURE_SPECIAL:
                        TextureAtlasSprite textureSpecialD = specialTextures.get((String)operationArgs[0]);
                        TextureAtlasSprite textureSpecialU = specialTextures.get((String)operationArgs[1]);
                        TextureAtlasSprite textureSpecialN = specialTextures.get((String)operationArgs[2]);
                        TextureAtlasSprite textureSpecialE = specialTextures.get((String)operationArgs[3]);
                        TextureAtlasSprite textureSpecialS = specialTextures.get((String)operationArgs[4]);
                        TextureAtlasSprite textureSpecialW = specialTextures.get((String)operationArgs[5]);
                        data.setHasSpecialTexture(true);
                        data.setTextureSpecial(new TextureAtlasSprite[]{textureSpecialD, textureSpecialU, textureSpecialN, textureSpecialE, textureSpecialS, textureSpecialW});
                        break;
                    case UV_TRANSLATION:
                        double u = (double)operationArgs[0] / 16D;
                        double v = (double)operationArgs[1] / 16D;
                        data.addUVTransformation(new UVTranslation(u, v));
                        break;
                    case COLOR_3:
                        int r3 = (int)operationArgs[0];
                        int g3 = (int)operationArgs[1];
                        int b3 = (int)operationArgs[2];
                        data.setColor(Colour.packRGBA(r3, g3, b3, 255));
                        break;
                    case COLOR_4:
                        int r4 = (int)operationArgs[0];
                        int g4 = (int)operationArgs[1];
                        int b4 = (int)operationArgs[2];
                        int a4 = (int)operationArgs[3];
                        data.setColor(Colour.packRGBA(r4, g4, b4, a4));
                        break;
                    case BRIGHTNESS:
                        int brightness = (int)operationArgs[0];
                        data.setHasBrightnessOverride(true);
                        data.setBrightness(brightness);
                        break;
                }
            }

            partData.add(data);
        }

        return partData;
    }

    private Object[] parseString(String s){
        int argsStart = s.indexOf('(') + 2;
        int argsEnd = s.indexOf(')') - 1;
        String argsString = s.substring(argsStart, argsEnd);
        return new Object[]{argsString};
    }

    private Object[] parseStringArray(String s, int size){
        int argsStart = s.indexOf('(') + 2;
        int argsEnd = s.indexOf(')') - 1;
        String argsString = s.substring(argsStart, argsEnd);
        String[] args = s.split(", ");
        Object[] data = new Object[size];

        for(int i = 0; i < data.length; i++){
            int textureStart = args[i].indexOf('"') + 1;
            int textureEnd = args[i].indexOf('"', textureStart);
            data[i] = args[i].substring(textureStart, textureEnd);
        }

        return data;
    }

    private Object[] parseInt(String s){
        int argsStart = s.indexOf('(') + 1;
        int argsEnd = s.indexOf(')');
        String argsString = s.substring(argsStart, argsEnd);
        Object[] data = new Object[1];

        if(StringUtils.isNumeric(argsString)){
            data[0] = Integer.parseInt(argsString);
        }
        else{
            throw new FMSModelFormatException("Your model is malformed!");
        }

        return data;
    }

    private Object[] parseIntArray(String s, int size){
        int argsStart = s.indexOf('(') + 1;
        int argsEnd = s.indexOf(')');
        String argsString = s.substring(argsStart, argsEnd);
        String[] args = argsString.split(", ");
        Object[] data = new Object[size];

        for(int i = 0; i < data.length; i++){
            if(StringUtils.isNumeric(args[i])){
                data[i] = Integer.parseInt(args[i]);
            }
            else{
                throw new FMSModelFormatException("Your model is malformed!");
            }
        }

        return data;
    }

    private Object[] parseDoubleArray(String s, int size){
        int argsStart = s.indexOf('(') + 1;
        int argsEnd = s.indexOf(')');
        String argsString = s.substring(argsStart, argsEnd);
        String[] args = argsString.split(", ");
        Object[] data = new Object[size];

        for(int i = 0; i < data.length; i++){
            data[i] = Double.parseDouble(args[i]);
        }

        return data;
    }

    private List<String> readFile(ResourceLocation location){
        String filePath = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath() + ".fms";
        List<String> fileContent = Lists.newArrayList();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try{
            inputStream = this.getClass().getResourceAsStream(filePath);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String currentLine = null;

            while((currentLine = bufferedReader.readLine()) != null){
                if(!this.isCommentLine(currentLine)){
                    fileContent.add(currentLine);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }

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
        }

        return fileContent;
    }

    private boolean isCommentLine(String line){
        for(int i = 0; i < allowedCommentFormat.length; i++){
            if(line.startsWith(allowedCommentFormat[i])){
                return true;
            }
        }

        return false;
    }

    public void registerModel(String name, ResourceLocation location){
        if(name != null && name != ""){
            if(location != null){
                fileLocations.put(name, location);
            }
            else{
                throw new FMSModelLoaderException("Multiblock location can't be null!");
            }
        }
        else{
            throw new FMSModelLoaderException("Name can't be empty or null!");
        }
    }

    public void registerSpecialTexture(String name, TextureAtlasSprite texture){
        if(name != null && name != "") {
            if(texture != null) {
                specialTextures.put(name, texture);
            }
            else{
                throw new FMSModelLoaderException("Multiblock location can't be null!");
            }
        }
        else{
            throw new FMSModelLoaderException("Name can't be empty or null!");
        }
    }

    public FMSModel getModel(String name){
        if(name != null && name != "") {
            return new FMSModel(models.get(name));
        }
        else{
            throw new FMSModelLoaderException("Name can't be empty or null!");
        }
    }

    private class FMSModelLoaderException extends IllegalArgumentException {

        public FMSModelLoaderException(String message) {
            super(message);
        }

    }

    private class FMSModelFormatException extends IllegalArgumentException {

        public FMSModelFormatException(String message) {
            super(message);
        }

    }

}
