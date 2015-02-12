package com.rs2.yz85.net.protocol;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * @author Kyle James Richards
 * Created by IntelliJ IDEA.
 */
public final class CodecFactory implements ProtocolCodecFactory {
    private ProtocolEncoder encoder = new PacketEncoder();
    private ProtocolDecoder decoder = new PacketDecoder();

    public ProtocolEncoder getEncoder() throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder() throws Exception {
        return decoder;
    }
}
