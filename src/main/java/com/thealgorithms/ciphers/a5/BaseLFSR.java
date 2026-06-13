package com.thealgorithms.ciphers.a5;

/* 线性反馈移位寄存器基类 */


import java.util.BitSet;

public interface BaseLFSR {
    void initialize(BitSet sessionKey, BitSet frameCounter);
    boolean clock();
    int SESSION_KEY_LENGTH = 64;
    int FRAME_COUNTER_LENGTH = 22;
}
