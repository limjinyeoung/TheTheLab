import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.HashMap;

public class SpriteManager {
    private SpriteManager() {}

    private static HashMap<Integer, ArrayList<PImage>> sprites = new HashMap<>();

    /**
     * HashMap에 PImage의 ArrayList를 넣음.
     * @param pApplet
     * @param key
     * @param path
     * @param totalImageStartX
     * @param totalImageStartY
     * @param column
     * @param row
     * @param sizeX
     * @param sizeY
     * @param spriteCount
     */
    public static void putImages(
            PApplet pApplet,
            int key, String path, int totalImageStartX, int totalImageStartY,
            int column, int row, int sizeX, int sizeY, int spriteCount) {

        PImage image = pApplet.loadImage(path);
        int width = image.width;
        int height = image.height;
        int startX = totalImageStartX + column * sizeX;
        int startY = totalImageStartY + row * sizeY;

        ArrayList<PImage> images = new ArrayList<>();
        for (int i = 0; i < spriteCount; i++) {
            PImage img = image.get(startX, startY, sizeX, sizeY);
            images.add(img);
            startX += sizeX;

            if (startY + sizeY > height)
                throw new IllegalArgumentException("요청한 이미지가 기존 이미지를 넘어감");

            if (startX + sizeX > width) {
                startY += sizeY;
                startX = column * sizeX;
            }
        }
        if (sprites.containsKey(key)) {
            throw new IllegalArgumentException("이미 등록된 상태임");
        }
        sprites.put(key, images);
    }


    /**
     * image sprite가 아닌 이미지 한장 가져오기
     * @param pApplet
     * @param key
     * @param path
     */
    public static void putImage(
            PApplet pApplet, int key, String path
    ) {
        ArrayList<PImage> images = new ArrayList<>();

        images.add(pApplet.loadImage(path));
        if (sprites.containsKey(key)) {
            throw new IllegalArgumentException("이미 등록된 상태임");
        }
        sprites.put(key, images);
    }


    /**
     * 배열의 순서대로 이미지가 PImage의 ArrayList에 넣어짐.
     * @param pApplet
     * @param state
     * @param path
     * @param totalImageStartX
     * @param totalImageStartY
     * @param sizeX
     * @param sizeY
     * @param spriteCount
     * @param indices
     */
    public static void putImages(PApplet pApplet,
                                 int state, String path,int totalImageStartX, int totalImageStartY,
                                 int sizeX, int sizeY, int spriteCount,
                                 int[] indices) {

        PImage image = pApplet.loadImage(path);

        int startX;
        int startY;

        ArrayList<PImage> images = new ArrayList<>();
        for (int i = 0; i < indices.length; i++) {
            startX = ((indices[i] % spriteCount) * sizeX) + totalImageStartX;
            startY = (sizeY * (indices[i] / spriteCount)) + totalImageStartY;

            PImage img = image.get(startX, startY, sizeX, sizeY);
            images.add(img);

        }

        if (sprites.containsKey(state)) {
            throw new IllegalArgumentException("이미 등록된 상태임");
        }
        sprites.put(state, images);

    }

    public static void putImages(PApplet pApplet,
                                 int state, String path, int totalImageStartX, int totalImageStartY,
                                 int column, int row, int sizeX, int sizeY,
                                 int spriteCount, boolean isLoop) {

        PImage image = pApplet.loadImage(path);
        int width = image.width;
        int height = image.height;
        int startX = column * sizeX + totalImageStartX;
        int startY = row * sizeY + totalImageStartY;

        ArrayList<PImage> images = new ArrayList<>();

        if (isLoop) {
            for (int i = 0; i < spriteCount; i++) {
                PImage img = image.get(startX, startY, sizeX, sizeY);
                images.add(img);
                startX += sizeX;

                if (startY + sizeY > height)
                    throw new IllegalArgumentException("요청한 이미지가 기존 이미지를 넘어감");
                if (startX + sizeX > width) {
                    if (i < spriteCount - 1) {
                        startY += sizeY;
                        startX = column * sizeX;
                    }
                }
            }

            startX -= sizeX * 2;

            for (int i = 0; i < spriteCount - 2; i++) {
                PImage img = image.get(startX, startY, sizeX, sizeY);
                images.add(img);
                startX -= sizeX;

                if (startX - sizeX < 0 && i < spriteCount - 3) {
                    startY -= sizeY;
                    startX = (column + spriteCount - 1) * sizeX;
                }

                if (startY < 0)
                    throw new IllegalArgumentException("요청한 이미지가 기존 이미지를 넘어감111");
            }
        } else {
            for (int i = 0; i < spriteCount; i++) {
                PImage img = image.get(startX, startY, sizeX, sizeY);
                images.add(img);
                startX += sizeX;

                if (startY + sizeY > height)
                    throw new IllegalArgumentException("요청한 이미지가 기존 이미지를 넘어감");

                if (startX + sizeX > width) {
                    startY += sizeY;
                    startX = column * sizeX;
                }
            }
        }


        if (sprites.containsKey(state)) {
            throw new IllegalArgumentException("이미 등록된 상태임");
        }
        sprites.put(state, images);

    }


    /*********************************
     여백을 둔 putImage() 메소드 구현
     ********************************/
//    public void putImage


    /**********************************
     getImage() 메소드 구현
     **********************************/
    public static ArrayList<PImage> getImages(int state) {
        if (!sprites.containsKey(state))
            throw new IllegalArgumentException("state가 없습니다.");

        return sprites.get(state);
    }

    public static PImage getImage(int state, int index) {
        if (!sprites.containsKey(state))
            throw new IllegalArgumentException("state가 없습니다.");

        ArrayList<PImage> images = sprites.get(state);
        return images.get(index % images.size());
    }

    public static PImage getImage(int state) {
        if (!sprites.containsKey(state))
            throw new IllegalArgumentException("state가 없습니다.");

        ArrayList<PImage> images = sprites.get(state);
        return images.get(0);
    }

}
