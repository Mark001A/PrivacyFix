package cn.guo.privacyfix;

import android.os.FileObserver;
import android.util.Log;

/**   
 * SD卡中的目录创建监听器。   
 *  
 * @author mayingcai 
 */    
public class SDCardListener extends FileObserver {     
   
       public SDCardListener(String path) {   
              /*   
               * 这种构造方法是默认监听所有事件的,如果使用 super(String,int)这种构造方法，   
               * 则int参数是要监听的事件类型.   
               */    
              super(path);     
       }     
     
       @Override    
       public void onEvent(int event, String path) {            
              switch(event) {     
                     case FileObserver.ALL_EVENTS:     
                            Log.i("all", "path:"+ path);     
                            break;     
                     case FileObserver.CREATE:     
                            Log.i("Create", "path:"+ path);     
                            break;     
              }     
      }     
}  
