package keri.ninetaillib.util;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.ModMetadata;

public class ModMetadataBuilder {

    private ModMetadata metadata;

    private ModMetadataBuilder(){
        this.metadata = new ModMetadata();
    }

    public void setModId(String modid){
        this.metadata.modId = modid;
    }

    public void setName(String name){
        this.metadata.name = name;
    }

    public void setVersion(String version){
        this.metadata.version = version;
    }

    public void setURL(String url){
        this.metadata.url = url;
    }

    public void setAuthor(String... authors){
        this.metadata.authorList = Lists.newArrayList(authors);
    }

    public void setUpdateJSON(String updateJSON){
        this.metadata.updateJSON = updateJSON;
    }

    @SuppressWarnings("deprecation")
    public void setUpdateURL(String updateURL){
        this.metadata.updateUrl = updateURL;
    }

    public void setLogoFile(String logoFile){
        this.metadata.logoFile = logoFile;
    }

    public void setDescription(String description){
        this.metadata.description = description;
    }

    public ModMetadata build(){
        return this.metadata;
    }

    public static ModMetadataBuilder newBuilder(){
        return new ModMetadataBuilder();
    }

}
