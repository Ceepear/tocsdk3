package net.t1y.cloud3.sdk.external.handler;
import java.util.concurrent.Future;

public interface T1Handler {
    void post(Runnable runnable);
    Future<?> exec(Runnable runnable);
}
