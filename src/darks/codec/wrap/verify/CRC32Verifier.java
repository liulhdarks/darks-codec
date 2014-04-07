package darks.codec.wrap.verify;

import java.util.zip.CRC32;

import darks.codec.helper.ByteHelper;
import darks.codec.logs.Logger;

public class CRC32Verifier extends Verifier
{

    private static Logger log = Logger.getLogger(CRC32Verifier.class);

    public CRC32Verifier()
    {
    }

    @Override
    public byte[] getVerifyCode(Object code, boolean littleEndian)
    {
        CRC32 crc32 = (CRC32) code;
        if (crc32 != null)
        {
            return ByteHelper
                    .convertInt32((int) crc32.getValue(), littleEndian);
        }
        return null;
    }

    @Override
    public Object update(Object initData, byte[] data, int offset, int length)
    {
        CRC32 crc32 = (CRC32) initData;
        if (crc32 == null)
        {
            crc32 = new CRC32();
        }
        crc32.update(data, offset, length);
        if (log.isDebugEnabled())
        {
            log.debug("CRC32 update "
                    + ByteHelper.toHexString(data, offset, length));
        }
        return crc32;
    }

    @Override
    public int verifyLength()
    {
        return 4;
    }

}
