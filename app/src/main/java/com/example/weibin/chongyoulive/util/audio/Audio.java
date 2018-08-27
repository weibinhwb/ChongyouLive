package com.example.weibin.chongyoulive.util.audio;

import android.os.Environment;

import com.example.weibin.chongyoulive.base.LiveApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;


/**
 * Created by weibin on 2018/8/23
 */


public class Audio implements AudioCapturer.OnAudioCapturedListener{

    private File mSaveFile;
    private FileOutputStream mSaveFos;

    private volatile static Audio sAudio;
    private AudioCapturer mAudioCapturer;
    private AudioPlayer mAudioPlayer;

    private Audio() {
        mAudioCapturer = new AudioCapturer();
        mAudioCapturer.setOnAudioFrameCapturedListener(this);
        mAudioPlayer = new AudioPlayer();
    }

    public static Audio getInstance() {
        if (sAudio == null) {
            synchronized (Audio.class) {
                if (sAudio == null) {
                    sAudio = new Audio();
                }
            }
        }
        return sAudio;
    }

    public void startCapture() {
        try {
            String fileName = new Date().getTime() + "record.pcm";
            mSaveFile = new File(LiveApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName);
            if (mSaveFile.createNewFile()) {
                mSaveFos = new FileOutputStream(mSaveFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAudioCapturer.startCapture();
    }

    public String stopCapture() {
        mAudioCapturer.stopCapture();
        try {
            mSaveFos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mSaveFile.getAbsolutePath();
    }

    public void play(File file) {
        new Thread(() -> {
            mAudioPlayer.startPlayer();
            try {
                int readBuffsize = mAudioPlayer.getMinBufferSize();
                byte[] bytes = new byte[readBuffsize];
                FileInputStream fis = new FileInputStream(file);
                int len = 0;
                while (fis.available() > 0) {
                    len = fis.read(bytes);
                    mAudioPlayer.play(bytes, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stopPlay() {
        if (mAudioPlayer != null) {
            mAudioPlayer.stopPlayer();
            return;
        }
        throw new RuntimeException("语音没有播放");
    }

    @Override
    public void onAudioCaptured(byte[] audioData) {
        if (mSaveFos == null) {
            throw new RuntimeException("输入流为空");
        }
        try {
            mSaveFos.write(audioData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
