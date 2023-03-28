package music;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;


public class Music2 extends Thread {

    @Override
    public void run() {
        try {
            File f = new File("src\\music\\Flag.wav");
            AudioClip audioClip= Applet.newAudioClip(f.toURI().toURL());
            // 音效 只播放一次
            audioClip.play();
            // 循环播放, 可用于背景音乐
//            audioClip.loop();
            Thread.sleep(1000); // 线程休眠 1 s

            //audioClip.stop();// 停止循环播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Music2 m = new Music2();
        m.start();
    }
}
