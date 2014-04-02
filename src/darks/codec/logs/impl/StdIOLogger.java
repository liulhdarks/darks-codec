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
