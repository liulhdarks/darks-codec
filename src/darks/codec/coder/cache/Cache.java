/**
 * 
 *Copyright 2014 The Darks Codec Project (Liu lihua)
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */

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
