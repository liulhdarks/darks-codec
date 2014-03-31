package darks.codec.logs.impl;

import java.util.logging.Level;

import darks.codec.logs.Logger;

public class Jdk14Logger extends Logger
{
    private java.util.logging.Logger log;
    
    public Jdk14Logger(String clazz)
    {
        this.log = java.util.logging.Logger.getLogger(clazz);
    }
    
    public boolean isDebugEnabled()
    {
        return this.log.isLoggable(Level.FINE);
    }
    
    public void error(String s, Throwable e)
    {
        this.log.log(Level.SEVERE, s, e);
    }
    
    public void error(String s)
    {
        this.log.log(Level.SEVERE, s);
    }
    
    public void debug(String s)
    {
        this.log.log(Level.FINE, s);
    }
    
    public void debug(String s, Throwable e)
    {
        this.log.log(Level.FINE, s, e);
    }
    
    public void warn(String s)
    {
        this.log.log(Level.WARNING, s);
    }

    @Override
    public void warn(String paramString, Throwable e)
    {
        this.log.log(Level.WARNING, paramString, e);
    }

    @Override
    public void info(String paramString, Throwable e)
    {
        this.log.log(Level.INFO, paramString, e);
    }

    @Override
    public void info(String paramString)
    {
        this.log.log(Level.INFO, paramString);
    }
}
