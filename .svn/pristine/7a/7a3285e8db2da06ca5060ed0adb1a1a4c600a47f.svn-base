package cn.guo.privacyfix;

import android.app.Service;  
import android.content.Intent;  
import android.content.IntentFilter;  
import android.os.IBinder;  
  
public final class SystemListenService extends Service {  
  
    @Override  
    public void onCreate() {  
        super.onCreate();  
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);  
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);  
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);  
        filter.addDataScheme("package");  
        filter.setPriority(Integer.MAX_VALUE);  
        registerReceiver(new UninstallReceiver(), filter);  
    }  
      
    @Override  
    public IBinder onBind(Intent paramIntent) {  
        return null;  
    }  
  
}  
