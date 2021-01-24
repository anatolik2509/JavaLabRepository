package ru.itis.downloader.utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ThreadPool {
    private Queue<Runnable> taskQueue;
    private Thread[] threads;
    private boolean shutdown = false;

    public ThreadPool(int poolCount){
        taskQueue = new ConcurrentLinkedDeque<>();
        threads = new Thread[poolCount];
        for(int i = 0; i < poolCount; i++){
            threads[i] = new WorkingThread();
            threads[i].start();
        }
    }

    public void submit(Runnable r){
        synchronized (taskQueue) {
            taskQueue.add(r);
            taskQueue.notify();
        }
    }

    public void shutdownOnEmptyQueue(){
        shutdown = true;
        synchronized (taskQueue){
            taskQueue.notifyAll();
        }
    }

    private class WorkingThread extends Thread{

        private Runnable r;

        @Override
        public void run() {
            try {
                synchronized (taskQueue) {
                    taskQueue.wait();
                }
                while (true) {
                    synchronized (taskQueue) {
                        if (!taskQueue.isEmpty()) {
                            r = taskQueue.poll();
                        } else {
                            taskQueue.wait();
                        }
                    }
                    if(r != null) {
                        r.run();
                        r = null;
                    }
                    if(taskQueue.isEmpty() && shutdown){
                        break;
                    }
                }
            }
            catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
