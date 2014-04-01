package darks.codec.coder;

import java.io.IOException;

import darks.codec.CodecConfig;
import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.OCCodec;
import darks.codec.coder.cache.Cache;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;
import darks.codec.type.OCMessage;
import darks.codec.type.OCObject;

public class DefaultOCCodec extends OCCodec
{

    private static final Logger log = Logger.getLogger(DefaultOCCodec.class);

    private static final int INIT_BYTES_SIZE = 128;

    private Encoder encoder;

    private Decoder decoder;
    
    private Cache cache;
    
    public DefaultOCCodec(CodecConfig codecConfig)
    {
        super(codecConfig);
        encoder = new DefaultEncoder();
        decoder = new DefaultDecoder();
        cache = Cache.getCache(codecConfig);
    }

    @Override
    public byte[] encode(OCObject msg) throws IOException
    {
        if (codecConfig.isHasHeader())
        {
            msg = new OCMessage(msg);
        }
        CodecParameter param = new CodecParameter(codecConfig, cache);
        BytesOutputStream baos = new BytesOutputStream(INIT_BYTES_SIZE, codecConfig);
        encoder.encodeObject(baos, msg, param);
        byte[] bytes = baos.toByteArray();
        if (log.isDebugEnabled())
        {
            log.debug(ByteHelper.toHexString(bytes));
        }
        return bytes;
    }

    @Override
    public OCObject decode(byte[] bytes, OCObject msg) throws IOException
    {
        if (codecConfig.isHasHeader())
        {
            msg = new OCMessage(msg);
        }
        CodecParameter param = new CodecParameter(codecConfig, cache);
        BytesInputStream bais = new BytesInputStream(bytes, codecConfig);
        decoder.decodeObject(bais, msg, param);
        return msg;
    }
}