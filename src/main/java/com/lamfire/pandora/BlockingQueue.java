package com.lamfire.pandora;

import com.lamfire.logger.Logger;

/**
 * 阻塞对例
 * User: lamfire
 * Date: 14-1-8
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
public class BlockingQueue implements Queue {
    private static final Logger LOGGER = Logger.getLogger(BlockingQueue.class);
    private Queue queue;

    public BlockingQueue(Queue queue) {
        this.queue = queue;
    }

    @Override
    public synchronized void push(byte[] value) {
        this.queue.push(value);
        this.notifyAll();
    }

    @Override
    public synchronized byte[] pull() {
        byte[] result = null;
        do{
            try {
                result = this.queue.pull();
                if(result == null){
                    this.wait();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        } while(result == null);
        return result;
    }

    @Override
    public synchronized byte[] peek() {
        byte[] result = null;
        do{
            try {
                result = this.queue.peek();
                if(result == null){
                    this.wait();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        } while(result == null);
        return result;
    }

    @Override
    public int size() {
        return this.queue.size();
    }

    @Override
    public void clear() {
        this.queue.clear();
    }
}
