package cn.bugstack.infrastructure.redis;

import org.redisson.api.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * Redis 服务
 */
public interface IRedisService {

    /**
     * 设置指定 key 的值
     *
     * @param key   键
     * @param value 值
     */
    <T> void setValue(String key, T value);

    /**
     * 设置指定 key 的值
     *
     * @param key     键
     * @param value   值
     * @param expired 过期时间
     */
    <T> void setValue(String key, T value, long expired);

    /**
     * 获取指定 key 的值
     *
     * @param key 键
     * @return 值
     */
    <T> T getValue(String key);

    /** 获取队列 */
    <T> RQueue<T> getQueue(String key);

    /** 加锁队列 */
    <T> RBlockingQueue<T> getBlockingQueue(String key);

    /** 延迟队列 */
    <T> RDelayedQueue<T> getDelayedQueue(RBlockingQueue<T> rBlockingQueue);

    void setAtomicLong(String key, long value);

    Long getAtomicLong(String key);

    long incr(String key);

    long incrBy(String key, long delta);

    long decr(String key);

    long decrBy(String key, long delta);

    void remove(String key);

    boolean isExists(String key);

    void addToSet(String key, String value);

    boolean isSetMember(String key, String value);

    void addToList(String key, String value);

    String getFromList(String key, int index);

    <K, V> RMap<K, V> getMap(String key);

    void addToMap(String key, String field, String value);

    String getFromMap(String key, String field);

    <K, V> V getFromMap(String key, K field);

    void addToSortedSet(String key, String value);

    RLock getLock(String key);

    RLock getFairLock(String key);

    RReadWriteLock getReadWriteLock(String key);

    RSemaphore getSemaphore(String key);

    RPermitExpirableSemaphore getPermitExpirableSemaphore(String key);

    RCountDownLatch getCountDownLatch(String key);

    <T> RBloomFilter<T> getBloomFilter(String key);

    Boolean setNx(String key);

    Boolean setNx(String key, long expired, TimeUnit timeUnit);

    RBitSet getBitSet(String key);

    default int getIndexFromUserId(String userId) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(userId.getBytes(StandardCharsets.UTF_8));
            BigInteger bigInt = new BigInteger(1, hashBytes);
            return bigInt.mod(BigInteger.valueOf(Integer.MAX_VALUE)).intValue();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

}