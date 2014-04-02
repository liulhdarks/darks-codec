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
