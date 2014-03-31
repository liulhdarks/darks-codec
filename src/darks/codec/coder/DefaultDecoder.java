package darks.codec.coder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.basetype.BaseType;
import darks.codec.basetype.BaseTypeFactory;
import darks.codec.exceptions.OCException;
import darks.codec.helper.ReflectHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.logs.Logger;
import darks.codec.type.IOCSerializable;
import darks.codec.type.OCList;
import darks.codec.type.OCMap;

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
            log.debug("Decode base type:[" + obj.getClass() + "] " + obj);
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
            log.debug("Decode default:[" + ocs.getClass() + "] " + ocs);
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
        log.debug("Decode default:[" + object.getClass() + "] " + object);
        Field[] fields = ReflectHelper.getValidField(object);
        for (Field field : fields)
        {
            Object val = ReflectHelper.getFieldValue(object, field);
            if (val instanceof OCList<?> || val instanceof OCMap<?, ?>)
            {
                Type[] types = ReflectHelper.getGenericTypes(field);
                if (types == null)
                {
                    throw new OCException("Generic type is null");
                }
                param.setGenericType(types);
            }
            val = decodeObject(in, val, param);
            if (val != null)
            {
                ReflectHelper.setFieldValue(object, field, val);
            }
        }
    }
}
