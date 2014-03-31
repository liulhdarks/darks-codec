package darks.codec.logs.impl;

import darks.codec.logs.Logger;


public class Slf4jLogger extends Logger
{
    
    private org.slf4j.Logger log;
    
    public Slf4jLogger(String clazz)
    {
        this.log = org.slf4j.LoggerFactory.getLogger(clazz);
    }
    
    @Override
    public void debug(String paramString)
    {
        log.debug(paramString);
    }
    
    @Override
    public void debug(String s, Throwable e)
    {
        log.debug(s, e);
    }
    
    @Override
    public void error(String paramString, Throwable paramThrowable)
    {
        log.error(paramString, paramThrowable);
    }
    
    @Override
    public void error(String paramString)
    {
        log.error(paramString);
    }
    
    @Override
    public boolean isDebugEnabled()
    {
        return log.isDebugEnabled();
    }
    
    @Override
    public void warn(String paramString)
    {
        log.warn(paramString);
    }

    @Override
    public void warn(String paramString, Throwable e)
    {
        log.warn(paramString, e);
    }

    @Override
    public void info(String paramString, Throwable e)
    {
        log.info(paramString, e);
    }

    @Override
    public void info(String paramString)
    {
        log.info(paramString);
    }
    
}
