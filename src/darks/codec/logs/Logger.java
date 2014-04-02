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

package darks.codec.logs;

public abstract class Logger
{
    
    public static Logger getLogger(Class<?> clazz)
    {
        return LoggerFactory.getLogger(clazz);
    }
    
    public static Logger getLogger(String tag)
    {
        return LoggerFactory.getLogger(tag);
    }
    
    public abstract boolean isDebugEnabled();
    
    public abstract void info(String paramString, Throwable e);
    
    public abstract void info(String paramString);
    
    public abstract void error(String paramString, Throwable e);
    
    public abstract void error(String paramString);
    
    public abstract void debug(String paramString);
    
    public abstract void debug(String s, Throwable e);
    
    public abstract void warn(String paramString);
    
    public abstract void warn(String paramString, Throwable e);
}
