/**
 * 类名:Log4jLogger.java
 * 作者:刘力华
 * 创建时间:2012-5-3
 * 版本:1.0.0 alpha 
 * 版权:CopyRight(c)2012 刘力华  该项目工程所有权归刘力华所有  
 * 描述:Log4jLogger
 */

package darks.codec.logs.impl;

import darks.codec.logs.Logger;

public class Log4jLogger extends Logger
{
    private org.apache.log4j.Logger log;
    
    public Log4jLogger(String clazz)
    {
        this.log = org.apache.log4j.Logger.getLogger(clazz);
    }
    
    public boolean isDebugEnabled()
    {
        return this.log.isDebugEnabled();
    }
    
    public void error(String s, Throwable e)
    {
        this.log.error(s, e);
    }
    
    public void error(String s)
    {
        this.log.error(s);
    }
    
    public void debug(String s)
    {
        this.log.debug(s);
    }
    
    public void debug(String s, Throwable e)
    {
        this.log.debug(s, e);
    }
    
    public void warn(String s)
    {
        this.log.warn(s);
    }

    @Override
    public void warn(String paramString, Throwable e)
    {
        log.warn(paramString, e);
    }

    @Override
    public void info(String paramString, Throwable e)
    {
        this.log.info(paramString, e);
    }

    @Override
    public void info(String paramString)
    {
        this.log.info(paramString);
    }
}
