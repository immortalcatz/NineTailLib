package keri.ninetaillib.multiblock;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class SimpleMultiblockLoader {

    private static final String[] allowedCommentFormat = new String[]{"//", "#"};
    private static final char[] allowedOffsets = new char[]{'n', 'e', 's', 'w', 'u', 'd'};
    private Map<String, ResourceLocation> fileLocations = Maps.newHashMap();
    private Map<String, MultiblockPattern> mutliblocks = Maps.newHashMap();

    public void loadMultiblocks(){
        for(Map.Entry<String, ResourceLocation> fileLocation : this.fileLocations.entrySet()){
            String multiblockName = fileLocation.getKey();
            ResourceLocation multiblockLocation = fileLocation.getValue();
            List<String> fileContent = this.readFile(multiblockLocation);
            List<IBlockState> componentBlockStates = Lists.newArrayList();
            List<List<EnumFacing>> componentOffsets = Lists.newArrayList();

            for(int i = 0; i < fileContent.size(); i++){
                String line = fileContent.get(i);
                this.checkSyntax(line, i);
                int offsetStart = line.indexOf('{', 0) + 1;
                int offsetEnd = line.indexOf('}', offsetStart);
                String offset = line.substring(offsetStart, offsetEnd);
                int componentStart = line.indexOf('{', offsetEnd) + 1;
                int componentEnd = line.indexOf('}', componentStart);
                String component = line.substring(componentStart, componentEnd);
                componentBlockStates.add(this.getBlockStateFromLine(component, i));
                componentOffsets.add(this.getOffsetListFromLine(offset, i));
            }

            MultiblockPattern pattern = new MultiblockPattern(componentOffsets, componentBlockStates);
            this.mutliblocks.put(multiblockName, pattern);
        }
    }

    @SuppressWarnings("deprecation")
    private IBlockState getBlockStateFromLine(String line, int lineNumber){
        int blockStart = 0;
        int blockEnd = line.indexOf(',');
        String blockName = line.substring(blockStart, blockEnd);
        int metaStart = blockEnd + 1;
        int metaEnd = line.length();
        String metaData = line.substring(metaStart, metaEnd);

        if(!StringUtils.isNumeric(metaData)){
            String message = String.format("Line %d is malformed!", lineNumber);
            throw new MultiblockFormatException(message);
        }

        return Block.getBlockFromName(blockName).getStateFromMeta(Integer.parseInt(metaData));
    }

    private List<EnumFacing> getOffsetListFromLine(String line, int lineNumber){
        List<EnumFacing> offsetList = Lists.newArrayList();

        if(!line.toLowerCase().equals("null")){
            for(int i = 0; i < line.length(); i++){
                char currentCharacter = line.charAt(i);

                if(currentCharacter != ','){
                    if(!ArrayUtils.contains(allowedOffsets, currentCharacter)){
                        String message = String.format("Line %d is malformed!", lineNumber);
                        throw new MultiblockFormatException(message);
                    }

                    switch(currentCharacter){
                        case 'n':
                            offsetList.add(EnumFacing.NORTH);
                            break;
                        case 'e':
                            offsetList.add(EnumFacing.EAST);
                            break;
                        case 's':
                            offsetList.add(EnumFacing.SOUTH);
                            break;
                        case 'w':
                            offsetList.add(EnumFacing.WEST);
                            break;
                        case 'u':
                            offsetList.add(EnumFacing.UP);
                            break;
                        case 'd':
                            offsetList.add(EnumFacing.DOWN);
                            break;
                    }
                }
            }
        }
        else{
            offsetList.add(null);
        }

        return offsetList;
    }

    private List<String> readFile(ResourceLocation location){
        String filePath = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath() + ".mbs";
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

    private void checkSyntax(String line, int lineNumber){
        if(!line.contains("offset")){
            String message = String.format("Line %d is malformed!", line);
            throw new MultiblockFormatException(message);
        }

        if(!line.contains("component")){
            String message = String.format("Line %d is malformed!", line);
            throw new MultiblockFormatException(message);
        }
    }

    public void loadMultiblock(String name, ResourceLocation location){
        if(name != null && name != ""){
            if(location != null){
                this.fileLocations.put(name, location);
            }
            else{
                throw new MultiblockLoaderException("Multiblock location can't be null!");
            }
        }
        else{
            throw new MultiblockLoaderException("Name can't be empty or null!");
        }
    }

    public MultiblockPattern getMutliblock(String name){
        if(name != null && name != ""){
            if(this.mutliblocks.containsKey(name)){
                return this.mutliblocks.get(name);
            }
            else{
                throw new MultiblockLoaderException("Multiblock pattern not registered or loaded!");
            }
        }
        else{
            throw new MultiblockLoaderException("Name can't be empty or null!");
        }
    }

    private class MultiblockLoaderException extends IllegalArgumentException {

        public MultiblockLoaderException(String message) {
            super(message);
        }

    }

    private class MultiblockFormatException extends IllegalArgumentException {

        public MultiblockFormatException(String message) {
            super(message);
        }

    }

}
