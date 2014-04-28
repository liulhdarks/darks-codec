package darks.codec.examples.complex;

import darks.codec.CodecConfig.EndianType;
import darks.codec.CodecConfig.TotalLengthType;
import darks.codec.ObjectCoder;
import darks.codec.helper.ByteHelper;
import darks.codec.type.OCBytes;
import darks.codec.type.OCInt16;
import darks.codec.type.OCMap;
import darks.codec.wrap.IdentifyWrapper;

public class ComplexCoder
{

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
        ObjectCoder coder = new ObjectCoder();
        coder.getCodecConfig().setEndianType(EndianType.LITTLE);
        coder.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
        coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xfafb)));
        
        ComplexMsg msg = new ComplexMsg();
        msg.id = 32;
        msg.version = 1;
        msg.subMsg.equipCode.setValue("2014");
        msg.subMsg.modular = 0x08;
        msg.subMsg.subModular = 0x01;
        msg.subMsg.commands.put((byte)0x01, new OrderMsg(OCBytes.valueOf("ready")));
        msg.subMsg.commands.put((byte)0x02, new OrderMsg(OCBytes.valueOf(128, true)));
        msg.subMsg.commands.put((byte)0x03, new OrderMsg(OCBytes.valueOf("parameter")));
        
        byte[] bytes = coder.encode(msg);
        System.out.println(ByteHelper.toHexString(bytes));
        
        ComplexMsg result = new ComplexMsg();
        coder.decode(bytes, result);
        OCMap<Byte, OrderMsg> cmds = result.subMsg.commands;
        System.out.println("ID:" + result.id + " VERSION:" + result.version);
        System.out.println("Equip Code:" + result.subMsg.equipCode.getValue());
        System.out.println("Modular:" + result.subMsg.modular + " Sub Modular:" + result.subMsg.subModular);
        System.out.println("Command 01:" + cmds.get((byte)0x01).order.getString());
        System.out.println("Command 02:" + cmds.get((byte)0x02).order.getInt32(true));
        System.out.println("Command 03:" + cmds.get((byte)0x03).order.getString());
        
    }

}
