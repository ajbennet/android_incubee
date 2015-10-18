package incubee.android;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sanat.
 */
public class App {
    private static Scheduler sIoThread;
    private static Scheduler sMainThread;

    public static Scheduler getIoThread() {
        if(sIoThread == null) {
            sIoThread = Schedulers.io();
        }
        return sIoThread;
    }

    public static Scheduler getMainThread() {
        if(sMainThread == null) {
            sMainThread = AndroidSchedulers.mainThread();
        }
        return sMainThread;
    }
}
