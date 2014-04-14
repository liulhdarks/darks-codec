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

package darks.codec.logs.impl;

import darks.codec.logs.Logger;

/**
 * Log4jLogger.java
 * @version 1.0.0
 * @author Liu lihua
 */
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
