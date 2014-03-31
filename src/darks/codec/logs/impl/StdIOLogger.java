package darks.codec.logs.impl;

import darks.codec.logs.Logger;


public class StdIOLogger extends Logger
{
    
    public StdIOLogger(String clazz)
    {
        
    }
    
    public boolean isDebugEnabled()
    {
        return true;
    }
    
    public void error(String s, Throwable e)
    {
        System.err.println(s);
        e.printStackTrace(System.err);
    }
    
    public void error(String s)
    {
        System.err.println(s);
    }
    
    public void debug(String s)
    {
        System.out.println(s);
    }
    
    public void debug(String s, Throwable e)
    {
        System.out.println(s);
        e.printStackTrace(System.out);
    }
    
    public void warn(String s)
    {
        System.out.println(s);
    }

    @Override
    public void warn(String paramString, Throwable e)
    {
        System.out.println(paramString);
        e.printStackTrace(System.out);
    }

    @Override
    public void info(String paramString, Throwable e)
    {
        System.out.println(paramString);
        e.printStackTrace(System.out);
    }

    @Override
    public void info(String paramString)
    {
        System.out.println(paramString);
    }
}
