import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyEventManager {
    // 먼저 관찰자를 설정 할 수 있습니다

    private PApplet pApplet;
    private HashMap<Integer, KeyStruct> keyStructs = new HashMap<>();

    private class KeyStruct {
        public List<OnPressListener> onPressListeners = new ArrayList<>();
        public List<OnReleaseListener> onReleaseListeners = new ArrayList<>();
        public long duration;
        public boolean isFlag;
        public boolean isPress;
    }

    public KeyEventManager(PApplet pApplet) {
        this.pApplet = pApplet;
        boolean isFirst;
    }


    public interface OnPressListener {
        void onPress(boolean isFirst, float duration);
    }

    public interface OnReleaseListener {
        void onRelease(float duration);
    }


    public void update() {
        for (int key : keyStructs.keySet()) {

            KeyStruct struct = keyStructs.get(key);

            if (struct.isPress) {
                for (OnPressListener l : struct.onPressListeners) {
                    l.onPress(!struct.isFlag,
                            System.currentTimeMillis() - struct.duration);
                }
                struct.isFlag = true;
            } else if (struct.isFlag) {

                for (OnReleaseListener l : struct.onReleaseListeners) {
                    l.onRelease(System.currentTimeMillis() - struct.duration);
                }
                struct.isFlag = false;

            }

        }


    }

    public void setKeyPress(int key) {

        if (!keyStructs.containsKey(key)) return;
        if (keyStructs.get(key).isPress) return;

        keyStructs.get(key).isPress = true;
        keyStructs.get(key).isFlag = false;
        keyStructs.get(key).duration = System.currentTimeMillis();
    }

    public void setKeyPress(char key) {

        if (!keyStructs.containsKey((int)key)) return;
        if (keyStructs.get((int)key).isPress) return;

        keyStructs.get((int)key).isPress = true;
        keyStructs.get((int)key).isFlag = false;
        keyStructs.get((int)key).duration = System.currentTimeMillis();
    }

    public void setKeyRelease(int key) {
        if (!keyStructs.containsKey(key)) return;
        keyStructs.get(key).isPress = false;
    }

    public void setKeyRelease(char key) {
        if (!keyStructs.containsKey((int)key)) return;
        keyStructs.get((int)key).isPress = false;
    }

    public void removeListener(int key, OnPressListener listener) {

    }

    public void removeListener(char key, OnPressListener listener) {

    }

    public void setOnPressListener(int key, OnPressListener listener) {

        if (!keyStructs.containsKey(key)) {
            keyStructs.put(key, new KeyStruct());

        }
        keyStructs.get(key).onPressListeners.add(listener);

    }

    public void setOnPressListener(char key, OnPressListener listener) {
        if (!keyStructs.containsKey((int)key)) {
            keyStructs.put((int)key, new KeyStruct());

        }
        keyStructs.get((int)key).onPressListeners.add(listener);
    }

    public void setOnReleaseListener(int key, OnReleaseListener listener) {

        if (!keyStructs.containsKey(key)) {
            keyStructs.put(key, new KeyStruct());
        }

        keyStructs.get(key).onReleaseListeners.add(listener);

    }

    public void setOnReleaseListener(char key, OnReleaseListener listener) {
        if (!keyStructs.containsKey((int)key)) {
            keyStructs.put((int)key, new KeyStruct());
        }

        keyStructs.get((int)key).onReleaseListeners.add(listener);
    }


}

