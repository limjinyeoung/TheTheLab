package model;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Packet {
    default byte[] toPacket() {
        Gson gson = new Gson();
        String json = gson.toJson(this);

        System.out.println(json);

        byte[] buf = json.getBytes();
        int len = buf.length;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(len + 2);
             DataOutputStream dos = new DataOutputStream(bos)) {

            dos.writeShort(len);
            dos.writeBytes(json);

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
