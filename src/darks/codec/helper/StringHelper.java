package darks.codec.helper;

public final class StringHelper
{
    public static String buffer(Object... objs)
    {
        StringBuilder buf = new StringBuilder(64);
        for (Object obj : objs)
        {
            buf.append(obj);
        }
        return buf.toString();
    }
}
