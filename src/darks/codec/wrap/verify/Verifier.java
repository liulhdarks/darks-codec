package darks.codec.wrap.verify;


public abstract class Verifier
{
    
    public Object update(Object initData, byte[] data)
    {
        return update(initData, data, 0, data.length);
    }
    
    public abstract Object update(Object initData, byte[] data, int offset, int length);
    
    public abstract byte[] getVerifyCode(Object code, boolean littleEndian);
    
    public abstract int verifyLength();
}
