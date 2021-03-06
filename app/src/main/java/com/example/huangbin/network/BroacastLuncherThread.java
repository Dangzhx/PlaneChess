package com.example.huangbin.network;

/**
 * Created by Huangbin on 2016/4/19.
 */
public class BroacastLuncherThread extends Thread {
    private  volatile boolean stopThread=true;
    private  BroascastGroupHelper mBroacastGrouperHelper;
    //private   Context mCoontext;
    private   String data;
    private Object mLock=new Object();
    private int waitTime;
    /**
     * @param helper BroascastGroupHelper第一个发起者
     * @param ip     ip一定不能为空
     */
    public BroacastLuncherThread(BroascastGroupHelper helper,String data) {
       this.mBroacastGrouperHelper=helper;
        this.data=data;
        waitTime = 100;
    }

    public BroacastLuncherThread(BroascastGroupHelper helper,String data,int waitTime) {
        this.mBroacastGrouperHelper=helper;
        this.data=data;
        this.waitTime = waitTime;
    }

    public  void stopThread(){
        synchronized (mLock) {
            this.stopThread = false;
            mBroacastGrouperHelper.destory();
        }
    }
    @Override
    public void run() {
        synchronized (mLock) {
            while (stopThread) {
                mBroacastGrouperHelper.sendMsg(data);
                try {
                    mLock.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
