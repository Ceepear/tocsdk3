package net.t1y.cloud3.sdk;
import android.content.Context;
import net.t1y.cloud3.sdk.core.T1Core;
import net.t1y.cloud3.sdk.external.T1Service;
import java.util.Objects;

public final class T1Sdk {
    private static T1Sdk sdk = null;
    private T1Core core = null;
    public static T1Sdk getSdk() {
        return Objects.requireNonNull(sdk);
    }
    public static T1Sdk initT1Environment(Context context){
        Context context1 = Objects.requireNonNull(context);
        if(Objects.isNull(sdk)){
            sdk = new T1Sdk(context);
        }
        return sdk;
    }
    public T1Sdk initService(T1Service service){
        this.core.initService(service);
        return this;
    }
    public <T extends T1Service> T getService(Class<? extends T1Service> cls){
        return this.core.getService(cls);
    }
    private T1Sdk(Context context){
        this.core = new T1Core(context);
    }
    public static void close(){
        sdk.core.close();
        sdk = null;
        System.gc();
    }
}
