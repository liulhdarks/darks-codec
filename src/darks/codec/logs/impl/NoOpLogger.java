package darks.codec.logs.impl;

import darks.codec.logs.Logger;

public class NoOpLogger extends Logger
{
    
    public NoOpLogger(String clazz)
    {
    }
    
    public boolean isDebugEnabled()
    {
        return true;
    }
    
    public void error(String s, Throwable e)
    {
        
    }
    
    public void error(String s)
    {
        
    }
    
    public void debug(String s)
    {
        
    }
    
    public void debug(String s, Throwable e)
    {
        
    }
    
    public void warn(String s)
    {
        
    }

    @Override
    public void info(String paramString, Throwable e)
    {
        
    }

    @Override
    public void info(String paramString)
    {
        
    }

    @Override
    public void warn(String paramString, Throwable e)
    {
        
    }
}
