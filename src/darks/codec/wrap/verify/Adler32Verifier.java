package darks.codec.wrap.verify;

import java.util.zip.Adler32;

import darks.codec.helper.ByteHelper;
import darks.codec.logs.Logger;

public class Adler32Verifier extends Verifier
{

    private static Logger log = Logger.getLogger(Adler32Verifier.class);

    public Adler32Verifier()
    {
    }

    @Override
    public byte[] getVerifyCode(Object code, boolean littleEndian)
    {
        Adler32 adler = (Adler32) code;
        if (adler != null)
        {
            return ByteHelper
                    .convertInt32((int) adler.getValue(), littleEndian);
        }
        return null;
    }

    @Override
    public Object update(Object initData, byte[] data, int offset, int length)
    {
        Adler32 adler = (Adler32) initData;
        if (adler == null)
        {
            adler = new Adler32();
        }
        adler.update(data, offset, length);
        if (log.isDebugEnabled())
        {
            log.debug("Adler32 update " + ByteHelper.toHexString(data, offset, length));
        }
        return adler;
    }

    @Override
    public int verifyLength()
    {
        return 4;
    }

}
