package darks.codec.coder;

import java.io.IOException;
import java.lang.reflect.Field;

import darks.codec.CodecParameter;
import darks.codec.Encoder;
import darks.codec.basetype.BaseType;
import darks.codec.basetype.BaseTypeFactory;
import darks.codec.exceptions.OCException;
import darks.codec.helper.ReflectHelper;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;
import darks.codec.type.IOCSerializable;

public class DefaultEncoder extends Encoder
{
    
    private static Logger log = Logger.getLogger(DefaultEncoder.class);
    
    @Override
    public void encodeObject(BytesOutputStream out, Object obj,
            CodecParameter param)
    {
        BaseType baseType = BaseTypeFactory.getCodec(obj.getClass());
        if (baseType != null)
        {
            log.debug("Encode base type:[" + obj.getClass() + "] " + obj);
            baseType.encode(out, obj, param);
        }
        else if (ReflectHelper.isDefaultType(obj))
        {
            encodeDefault(out, (IOCSerializable) obj, param);
        }
        else
        {
            encodeOther(out, obj, param);
        }
    }

    private void encodeDefault(BytesOutputStream out, IOCSerializable ocs,
            CodecParameter param)
    {
        try
        {
            log.debug("Encode default object:[" + ocs.getClass() + "] " + ocs);
            ocs.writeObject(this, out, param);
        }
        catch (IOException e)
        {
            throw new OCException("Fail to encode default object.", e);
        }
    }

    private void encodeOther(BytesOutputStream out, Object object,
            CodecParameter param)
    {
        log.debug("Encode other object:[" + object.getClass() + "] " + object);
        Field[] fields = ReflectHelper.getValidField(object);
        for (Field field : fields)
        {
            Object val = ReflectHelper.getFieldValue(object, field);
            encodeObject(out, val, param);
        }
    }

}
