package com.mss.proxy;

/**
 * 线程中的静态代理
 */
public class StaticProxyTest {

    public interface PlayGame {
        void playGame();
    }

    /**
     * 静态代理类
     */
    static class ProxyPlayGame implements PlayGame {
        private PlayGame target;

        public ProxyPlayGame(PlayGame target) {
            this.target = target;
        }

        @Override
        public void playGame() {
            before();
            target.playGame();
            after();
        }

        private void after() {
            System.out.println("opening game...");
        }

        private void before() {
            System.out.println("shut down game.");
        }
    }

    public static void main(String[] args) {
        //被代理对象
        PlayGame player = ()-> System.out.println("playing game1...");

        //代理对象
        ProxyPlayGame playGame = new ProxyPlayGame(player);
        playGame.playGame();

        //被代理对象
        Runnable runnable = ()-> System.out.println(Thread.currentThread().getName()+"线程start...");

        //代理对象
        Thread thread = new Thread(runnable);
        thread.start();
    }

}

