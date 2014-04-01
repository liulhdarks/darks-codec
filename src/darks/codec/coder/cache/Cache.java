package darks.codec.coder.cache;

import java.lang.reflect.Field;

import darks.codec.CodecConfig;
import darks.codec.CodecConfig.CacheType;
import darks.codec.helper.EnvHelper;
import darks.codec.helper.StringHelper;
import darks.codec.logs.Logger;

public class Cache
{
    
    private static Logger log = Logger.getLogger(Cache.class);
    
    private static Cache globalCache;
    
    private CacheStrategy strategy;
    
    public static Cache getCache(CodecConfig cfg)
    {
        if (cfg.getCacheType() == CacheType.GLOBAL)
        {
            return getGlobalCache();
        }
        else if (cfg.getCacheType() == CacheType.LOCAL)
        {
            return getLocalCache();
        }
        else
        {
            return null;
        }
    }
    
    private static synchronized Cache getGlobalCache()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Get codec cache from global cache.");
        }
        if (globalCache == null)
        {
            globalCache = new Cache();
        }
        return globalCache;
    }
    
    private static Cache getLocalCache()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Get codec cache from local cache.");
        }
        return new Cache();
    }
    
    private Cache()
    {
        if (EnvHelper.isAndroidEnv())
        {
            strategy = new StrongRefStrategy();
        }
        else
        {
            strategy = new SoftRefStrategy();
        }
    }
    
    public Field[] getCacheFields(Class<?> clazz)
    {
        if (log.isDebugEnabled())
        {
            log.debug(StringHelper.buffer("Get fields from cache:", strategy, " for ", clazz));
        }
        return strategy.getCacheFields(clazz);
    }
    
    public void putCacheFields(Class<?> clazz, Field[] fields)
    {
        if (log.isDebugEnabled())
        {
            log.debug(StringHelper.buffer("Put fields to cache:", strategy, " for ", clazz));
        }
        strategy.putCacheFields(clazz, fields);
    }
    
}
