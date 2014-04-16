Darks Codec
===========

Darks codec is a lightweight message protocol encoding and decoding framework. 
It supports encoding and decoding message object of most message protocol based on bytes. 
It will quickly help developers build any message protocol conveniently.
It will make developers design attention more than the implementation of message protocol, which can help software design better.

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
Now we can encode message object conveniently.
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
Now we try to decode message object conveniently.
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
Now, we have finished a simple example. You can find it in /examples/darks/codec/examples/simple.

