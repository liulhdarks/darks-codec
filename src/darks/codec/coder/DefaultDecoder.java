package darks.codec.coder;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.basetype.BaseType;
import darks.codec.basetype.BaseTypeFactory;
import darks.codec.exceptions.OCException;
import darks.codec.helper.ReflectHelper;
import darks.codec.helper.StringHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.logs.Logger;
import darks.codec.type.IOCSerializable;
import darks.codec.type.OCObject;

public class DefaultDecoder extends Decoder
{

    private static Logger log = Logger.getLogger(DefaultDecoder.class);
    
    @Override
    public Object decodeObject(BytesInputStream in, Object obj,
            CodecParameter param) throws IOException
    {
        BaseType baseType = BaseTypeFactory.getCodec(obj.getClass());
        if (baseType != null)
        {
            if (log.isDebugEnabled())
            {
                log.debug(StringHelper.buffer("Decode base type:[", obj.getClass(), "] ", obj));
            }
            return baseType.decode(in, obj, param);
        }
        else if (ReflectHelper.isDefaultType(obj))
        {
            decodeDefault(in, (IOCSerializable) obj, param);
        }
        else
        {
            decodeOther(in, obj, param);
        }
        return null;
    }

    private void decodeDefault(BytesInputStream in, IOCSerializable ocs,
            CodecParameter param) throws IOException
    {
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug(StringHelper.buffer("Decode default:[", ocs.getClass(), "] ", ocs));
            }
            ocs.readObject(this, in, param);
        }
        catch (IOException e)
        {
            throw new OCException("Fail to decode default object.", e);
        }
    }

    private void decodeOther(BytesInputStream in, Object object,
            CodecParameter param) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug(StringHelper.buffer("Decode default:[", object.getClass(), "] ", object));
        }
        new OCObject(object).readObject(this, in, param);
    }
}
