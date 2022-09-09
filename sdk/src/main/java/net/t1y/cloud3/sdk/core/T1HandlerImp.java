package net.t1y.cloud3.sdk.core;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import net.t1y.cloud3.sdk.external.handler.T1Handler;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T1HandlerImp implements T1Handler {
    private Handler handler;
    private ExecutorService executorService = Executors.newFixedThreadPool(16);
    T1HandlerImp(){
        this.handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x0) {
                    ((Runnable) msg.obj).run();
                }
            }
        };
    }
    public void post(Runnable runnable){
        Message message = new Message();
        message.what = 0x0;
        message.obj = Objects.requireNonNull(runnable);;
        this.handler.sendMessage(message);
    }

    public void load(Runnable runnable){
        this.executorService.submit(new Runnable(){
            @Override
            public void run() {
                System.out.println("load被执行！");
                runnable.run();
            }
        });
    }

    public void close() {
        this.executorService.shutdown();
        this.executorService = null;
        this.handler.removeMessages(0x0);
        this.handler = null;
    }
}
