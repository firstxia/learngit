package com.xialm.googleplayme.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor; // java中并发库中的类
import java.util.concurrent.TimeUnit;

import static android.R.attr.max;

/**
 * 线程池管理类:单例
 * Created by Xialm on 2016/10/29.
 * alt+insert/Code--Generate   生成构造/getter/setter方法等等
 */

public class ThreadPoolManager {
    private static ThreadPoolManager instance = new ThreadPoolManager();
    private static ThreadPoolProxy poolProxy;
    private ThreadPoolProxy longThreadPoolProxy;

    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    /**
     * 请求服务器用的线程池
     * 效率最高线程池: CPU核心数*2+1:初始化时效率最高的线程
     *
     * @return 用到线程池的地方:
     * 1.读写文件
     * 2.请求服务器数据
     */
    public ThreadPoolProxy createThreadPool() {
        if (poolProxy == null) {
            // 创建一个初始线程数量为3,额外最多创建3个线程,额外的线程在空闲时最长存活时间为5s的线程池
            poolProxy = new ThreadPoolProxy(3, 3, 5000, TimeUnit.MILLISECONDS);

        }
        return poolProxy;
    }

    /**
     * 读写文件用的线程池
     * @return
     */
    public ThreadPoolProxy createLongThreadPool() {
        if (longThreadPoolProxy == null) {
            longThreadPoolProxy = new ThreadPoolProxy(5, 5, 5000, TimeUnit.MILLISECONDS);
        }
        return longThreadPoolProxy;
    }

    /**
     * 线程池配置类
     */
    public static class ThreadPoolProxy {
        ThreadPoolExecutor threadPool;
        private int corePoolSize; // 初始化时的线程数量
        private int maximumPoolSize; //除了初始化时的线程数量,额外创建的线程数量
        private long keepAliveTime; // 最长存活时间,单位为下一个参数
        private TimeUnit unit; // 存活时间的单位:有TimeUnit.MILLISECONDS/DAY/HOURS/MINATUE等

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
            this.unit = unit;
        }

        // 执行线程任务
        public void execute(Runnable runnable) {
            // 如果线程池为null,创建
            if (threadPool == null) {
                // 参数10:最多有10个线程可以进入等待队列
                threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(10));
            }
            // 执行
            threadPool.execute(runnable);
        }

        // 取消线程任务
        public void cancel(Runnable runnable) {
            // 线程池不为null,不崩溃,不终止
            if (threadPool != null && !threadPool.isShutdown() && !threadPool.isTerminated()) {
                threadPool.remove(runnable);
            }
        }
    }
}
