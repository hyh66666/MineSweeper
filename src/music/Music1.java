package music;

import javax.sound.sampled.FloatControl;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;


public class Music1 extends Thread {

    @Override
    public void run() {

        try {
            File f = new File("src\\music\\BGM.wav");
            AudioClip audioClip= Applet.newAudioClip(f.toURI().toURL());

            // 音效 只播放一次
            System.out.println("WIND");
            // 循环播放, 可用于背景音乐
            audioClip.loop();


            //Thread.sleep(1000); // 线程休眠 1 s

            //audioClip.stop();// 停止循环播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Music1 m = new Music1();
        m.start();
    }
}
