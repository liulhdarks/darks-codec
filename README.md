Darks Codec
===========

Darks codec is a lightweight message protocol encoding and decoding framework. 
It supports encoding and decoding message object of most message protocol based on bytes. 
It helps developers build any message protocol easily and quickly.
It makes developers design attention more than the implementation of message protocol, which can help software design better.

Simple Example
-----------

### Define Message Structure
If we want to build the message protocol with LITTLE-ENDIAN like:
<pre>
  FB FA  [ID 32bits] [VERSION 8bits] [COMMAND]  FF
</pre>
We can build JAVA class like:
<pre>
  public class SimpleMsg
  {
      int id;
      byte version;
      String command;
  }
</pre>
In order to describe simple, we omit get/set method.

### Configure Object Coder
According to message protocol, we only need Head identify "FB FA" and tail identify "FF" besides message body, so we can configure object coder like:
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().setEndianType(EndianType.LITTLE);
  coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xFAFB), new OCInt8(0xFF)));
</pre>

### Encoding Message
Now we can encode message object easily.
<pre>
  SimpleMsg msg = new SimpleMsg();
  msg.id = 32;
  msg.version = 1;  
  msg.command = "running";
  byte[] bytes = coder.encode(msg);
  System.out.println(ByteHelper.toHexString(bytes));
</pre>
Code will output console information:
<pre>
  FB FA   20 00 00 00   01   72 75 6E 6E 69 6E 67   FF
</pre>
Because of LITTLE-ENDIAN, 0xFAFB coded as "FB FA", ID which is 32 coded as "20 00 00 00", VERSION which is 1 coded as "01",
command which is "running" coded as "72 75 6E 6E 69 6E 67" and 0xFF coded as "FF".

### Decoding Message
Now we try to decode message object easily.
<pre>
  SimpleMsg result = new SimpleMsg();
  coder.decode(bytes, result);
  System.out.println("ID:" + result.id);
  System.out.println("VERSION:" + result.version);
  System.out.println("COMMAND:" + result.command);
</pre>
Code will output console information:
<pre>
  ID:32
  VERSION:1
  COMMAND:running
</pre>

### Total Length
Some message protocol need replace tail identify with bytes total length. 
We can set total length type to have the bytes total length. Total length type have AUTO, BODY and HEAD_BODY types.<br/>
AUTO : Automatic object total length. It calculate object length without head or tail wrapper's length only when autoLength is true.<br/>
BODY : Body total length will calculate object length and tail wrapper's length without head wrapper's length.<br/>
HEAD_BODY : Head and body total length will calculate head wrapper, tail wrapper and object length.<br/>
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().setEndianType(EndianType.LITTLE);
  coder.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
  coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xFAFB)));
</pre>
Code will output console information:
<pre>
  FB FA   12 00 00 00   20 00 00 00   01   72 75 6E 6E 69 6E 67 
</pre>
Now, we have finished a simple example. You can find codes in /examples/darks/codec/examples/simple.

Automatic Value Length
-----------------------
If message protocol have multiply object type such as String, Object, Bytes and so on, 
we should set automatic length to ensure decoding message correctly.<br/>
Message protocol may like: 
<pre>
  FB FA  [TOTAL LENGTH] [ID 32bits] [VERSION 8bits] [CMD1 LEN 32bits] [COMMAND1] [CMD2 LEN 32bits] [COMMAND2]
</pre>
Java bean class:
<pre>
  public class MultiCmdMsg
  {
      int id;
      byte version;
      String command1;
      String command2;
  }
</pre>
Then we encode it object will automatic length:
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().setEndianType(EndianType.LITTLE);
  coder.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
  coder.getCodecConfig().setAutoLength(true);
  coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xFAFB)));
  MultiCmdMsg msg = new MultiCmdMsg();
  msg.id = 32;
  msg.version = 1;
  msg.command1 = "ready";
  msg.command2 = "running";
  byte[] bytes = coder.encode(msg);
  System.out.println(ByteHelper.toHexString(bytes));
</pre>
Code will output console information:
<pre>
  FB FA   1F 00 00 00   20 00 00 00   01   05 00 00 00   72 65 61 64 79   07 00 00 00   72 75 6E 6E 69 6E 67
</pre>
You can see detail example codes in /examples/darks/codec/examples/autolen.

Codec Type
-------------
Besides using JAVA type directly, developers can use codec type which is in package darks.codec.type more flexibly to code.<br/>
OCInt32 :   32-bits integer type which based on OCInteger.<br/>
OCInt16 :   16-bits integer type which based on OCInteger.<br/>
OCInt8  :   8-bits integer type which based on OCInteger.<br/>
OCLong  :   64-bits long type.<br/>
OCFloat :   Just like float.<br/>
OCDouble:   Just like double.<br/>
OCBytes :   Bytes array type.<br/>
OCString:   Just like String.<br/>
OCList  :   It can store collection data which based on java.util.List.<br/>
OCMap   :   It can store Key-value pair values which based on java.util.Map.<br/>
OCListMap:  It can store multiply values in same key index besides Map feature.<br/>
OCObject:   Java object can extends it to code flexibly.<br/>
Custom Type: Developers can build a class which inherit from OCBase or OCBaseType to customize type, which must add annotation @CodecType.<br/>

### Codec Type Example
We can rebuild SimpleMsg by codec type.
<pre>
  public class SimpleMsg extends OCObject
  {
      OCInt32 id = new OCInt32();
      OCInt8 version = new OCInt8();
      OCString command = new OCString();
  }
</pre>
Also we can rebuild MultiCmdMsg by mix type.
<pre>
  public class MultiCmdMsg
  {
      int id;
      byte version;
      OCInt32 cmdLen1 = new OCInt32();
      OCString command1 = new OCString(cmdLen1);
      OCInt32 cmdLen2 = new OCInt32();
      OCString command2 = new OCString(cmdLen2);
  }
</pre>
"command1 = new OCString(cmdLen1)" can set command1's length value to field "cmdLen1" automatically. 
And It will decode command1 by cmdLen1's value when decoding. Therefore we should set parameter "autoLength" false.
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().setEndianType(EndianType.LITTLE);
  coder.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
  coder.getCodecConfig().setAutoLength(false);  //default false.
  coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xFAFB)));
  MultiCmdMsg msg = new MultiCmdMsg();
  msg.id = 32;
  msg.version = 1;
  msg.command1.setValue("ready");
  msg.command2.setValue("running");
  byte[] bytes = coder.encode(msg);
  System.out.println(ByteHelper.toHexString(bytes));
</pre>
You can see detail example codes in /examples/darks/codec/examples/codectype.

Fields Cache
-----------------
To encode or decode object, darks-codec will get object's valid fields. We can use field cache to improve the speed
 of getting fields. Cache have LOCAL and GLOBAL type. Default NONE.<br/>
LOCAL : Local cache is available in current ObjectCoder.<br/>
GLOBAL: Global cache is available in all ObjectCoder as global static object.<br/>
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().setEndianType(EndianType.LITTLE);
  coder.getCodecConfig().setCacheType(CacheType.LOCAL);
    ...
</pre>

Field Sequence
-----------------
In some Android's dalvikVM, the fields get from object are sorted automatically by virtual machine. 
Therefore we cannot encode object fields according to the specified fields sequence. 
To solve the problem, we should define a field named "fieldSequence" in JAVA object or use OCObject's method "setFieldSequence". 
Java bean class like:
<pre>
  public class FieldMsg
  {
      static String[] fieldSequence = new String[]{"id", "version", "command"};
      String command;
      int id;
      byte version;
  }
</pre>
Or
<pre>
  public class FieldCodecMsg extends OCObject
  {
      public FieldCodecMsg()
      {
          setFieldSequence(new String[]{"id", "version", "command"});
      }
      OCString command = new OCString();
      int id;
      byte version;
  }
</pre>
You can see detail example codes in /examples/darks/codec/examples/fieldseq.

Wrapper
-------------
When ObjectCoder encoding or decoding message, 
It will use WrapperChain to call wrapper before and after encoding or decoding. 
When developers add wrapper, wrapper will be added to the first of chain.<br/>
When encoding message, wrappers are called like LIFO, which will call the first addition of wrapper finally. 
And when decoding message, wrappers are called like FIFO, which will call the first addition of wrapper firstly.
<br/>
Example:
<pre>
addWrap IdentifyWrapper
addWrap VerifyWrapper
addWrap ZipWrapper

Encoding call sequence:
ZipWrapper -> VerifyWrapper -> IdentifyWrapper

Decoding call sequence:
IdentifyWrapper -> VerifyWrapper -> ZipWrapper
</pre>

### TotalLengthWrapper
TotalLengthWrapper will be added automatically when total length type is BODY or HEAD_BODY, 
so developers cannot add it manually again.

### Identify Wrapper
IdentifyWrapper can help developers add head identify and tail identify. You can add only head identify or both of them.
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xFAFB), new OCInt8(0xFF)));
    ...
  coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xFAFB)));
    ...
  coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt8(0xFA)));
    ...
  coder.getCodecConfig().addWrap(new IdentifyWrapper((short)0xFAFB));
</pre>

### Verify Wrapper
VerifyWrapper can add verify bytes to the end of message bytes such as CRC16, CRC32, ADLER32 and so on.
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().addWrap(VerifyWrapper.CRC16());
    ...
  coder.getCodecConfig().addWrap(VerifyWrapper.CRC32());
    ...
  coder.getCodecConfig().addWrap(new VerifyWrapper(new CustomVerifier()));
</pre>
Example:
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().setEndianType(EndianType.LITTLE);
  coder.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
  coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xFAFB)));
  coder.getCodecConfig().addWrap(VerifyWrapper.CRC16());
  
  SimpleMsg msg = new SimpleMsg();
  msg.id = 32;
  msg.version = 1;  
  msg.command = "running";
  byte[] bytes = coder.encode(msg);
  System.out.println(ByteHelper.toHexString(bytes));
</pre>
Code will output console information:
<pre>
  FB FA   14 00 00 00   20 00 00 00   01   72 75 6E 6E 69 6E 67   D6 74 
</pre>
"D6 74" is the message bytes's CRC16 code.

### ZIP Wrapper
If message bytes are so large, we can compress bytes to reduce communication traffic. 
It supports JDK GZIP, JZLIB (Need jzlib-1.1.x.jar), Commons Compress(Need commons-compress-1.x.jar) and custom compress.
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().addWrap(ZipWrapper.JZLIB());
    ...
  coder.getCodecConfig().getWrapChain().add(ZipWrapper.COMMON_COMPRESS(CompressorStreamFactory.BZIP2));
    ...
  coder.getCodecConfig().addWrap(new ZipWrapper(new CustomZip()));
</pre>

### Cipher Wrapper
Developers can encrypt message bytes to ensure information secure.
<pre>
  ObjectCoder coder = new ObjectCoder();
  coder.getCodecConfig().addWrap(CipherWrapper.AES("AESKEYS"));
    ...
  coder.getCodecConfig().addWrap(new CipherWrapper(new CustomCipher()));
</pre>

Continue to update...
-------------------
