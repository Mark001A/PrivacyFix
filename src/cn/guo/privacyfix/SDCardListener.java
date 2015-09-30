package cn.guo.privacyfix;

import android.os.FileObserver;
import android.util.Log;

/**   
 * SD���е�Ŀ¼������������   
 *  
 * @author mayingcai 
 */    
public class SDCardListener extends FileObserver {     
   
       public SDCardListener(String path) {   
              /*   
               * ���ֹ��췽����Ĭ�ϼ��������¼���,���ʹ�� super(String,int)���ֹ��췽����   
               * ��int������Ҫ�������¼�����.   
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
