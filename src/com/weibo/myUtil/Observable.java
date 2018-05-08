package com.weibo.myUtil;


import com.weibo.event.WeiboCreatedEvent;
import com.weibo.event.WeiboReadEvent;

import java.util.Vector;

public class Observable{
    private boolean changed = false;
    private Vector<Observer> obs;
    public Observable(){
        obs = new Vector<Observer>();
    }


    public void register(Observer observer){
        if(observer == null) throw new NullPointerException();
        if(!obs.contains(observer)) obs.add(observer);
    }
    public synchronized void unregisterAllObservers(){
        obs.removeAllElements();
    }

    public synchronized  void unregister(Observer observer){
        obs.removeElement(observer);
    }

    public synchronized void notifyObservers(){
        notifyObservers((Object) null);
    }
    public synchronized void notifyObservers(Object arg){
        synchronized (this){
            for(int i = 0; i < obs.size(); i++){
                obs.get(i).update(this, arg);
            }
        }
    }
    public synchronized void notifyObservers(WeiboCreatedEvent event){
        synchronized (this){
            for(int i = 0; i < obs.size(); i++){
                obs.get(i).update(this, event);
            }
        }
    }
    public synchronized void notifyObservers(WeiboReadEvent event){
        synchronized (this){
            for(int i = 0; i < obs.size(); i++){
                obs.get(i).update(this, event);
            }
        }
    }
    public synchronized  void setChanged(){
        changed = true;
    }
    public synchronized boolean hasChanged(){
        return changed;
    }

    protected synchronized void clearChanged(){
        changed = false;
    }


}
