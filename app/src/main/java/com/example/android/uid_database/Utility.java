package com.example.android.uid_database;


import java.util.Random;

public class Utility {

    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append(str).append(" ");
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    public static String stringToAscii(String val) {
        StringBuffer stringBuffer = new StringBuffer();
        char[] chars = val.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            stringBuffer.append((int) chars[i]);
        }

        return stringBuffer.toString();
    }

    public static int byteToInteger(byte b) {
        int value;
        value = b & 0xff;
        return value;
    }

    public static String EncryptInput(String input) {
        if (input.length() < 16) {
            input = addZeroForNum(input, 16);
        }

        input = "WZ" + input.substring(2, input.length() - 2) + "YK";
        return input;
    }

    public static int CRC16(byte[] Buf) {
        int CRC;
        int i, Temp, Len;
        CRC = 0xffff;
        Len = Buf.length;

        for (i = 0; i < Len; i++) {
            CRC = CRC ^ byteToInteger(Buf[i]);
            for (Temp = 0; Temp < 8; Temp++) {
                if ((CRC & 0x01) == 1)
                    CRC = (CRC >> 1) ^ 0xA001;
                else
                    CRC = CRC >> 1;
            }
        }
        return CRC;
    }

    public static int Enc_fun(int val){
        Random rdm = new Random(System.currentTimeMillis());
        int sec = rdm.nextInt(1000);
        val = (sec << 16) | val;
        return val;
    }
}
