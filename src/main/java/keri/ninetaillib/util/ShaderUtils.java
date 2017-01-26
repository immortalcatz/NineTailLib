package keri.ninetaillib.util;

import codechicken.lib.render.shader.ShaderProgram;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ShaderUtils {

    public static final int VERT = ARBVertexShader.GL_VERTEX_SHADER_ARB;
    public static final int FRAG = ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;

    public static int createShader(ResourceLocation vert, ResourceLocation frag){
        String vertPath = null;
        String fragPath = null;

        if(vert != null){
            vertPath = "/assets/" + vert.getResourceDomain() + "/" + vert.getResourcePath();
        }
        if(frag != null){
            fragPath = "/assets/" + frag.getResourceDomain() + "/" + frag.getResourcePath();
        }

        return createProgram(vertPath, fragPath);
    }

    public static void useShader(int shader, IShaderCallback callback) {
        ARBShaderObjects.glUseProgramObjectARB(shader);

        if(shader != 0){
            int time = ARBShaderObjects.glGetUniformLocationARB(shader, "time");
            ARBShaderObjects.glUniform1iARB(time, (int)codechicken.lib.util.ClientUtils.getRenderTime());
            if(callback != null){ callback.call(shader); }
        }
    }

    public static void useShader(int shader) {
        useShader(shader, null);
    }

    public static void releaseShader() {
        useShader(0);
    }

    private static int createProgram(String vert, String frag) {
        int vertId = 0;
        int fragId = 0;
        int program;
        if(vert != null){ vertId = createShader(vert, VERT); }
        if(frag != null){ fragId = createShader(frag, FRAG); }
        program = ARBShaderObjects.glCreateProgramObjectARB();
        if(program == 0){ return 0; }
        if(vert != null){ ARBShaderObjects.glAttachObjectARB(program, vertId); }
        if(frag != null){ ARBShaderObjects.glAttachObjectARB(program, fragId); }
        ARBShaderObjects.glLinkProgramARB(program);

        if(ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE){
            return 0;
        }

        ARBShaderObjects.glValidateProgramARB(program);
        if(ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE){
            return 0;
        }

        return program;
    }

    private static int createShader(String filename, int shaderType){
        int shader = 0;
        try{
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
            if(shader == 0){ return 0; }
            ARBShaderObjects.glShaderSourceARB(shader, ShaderProgram.asString(ShaderUtils.class.getResourceAsStream(filename)));
            ARBShaderObjects.glCompileShaderARB(shader);

            if(ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE){
                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
            }

            return shader;
        }
        catch(Exception e) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            e.printStackTrace();
            return -1;
        }
    }

    private static String getLogInfo(int obj){
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    public static interface IShaderCallback {

        void call(int shader);

    }

}
