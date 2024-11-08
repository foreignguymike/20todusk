package com.distraction.ttd2024.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioHandler {

    public enum AudioState {
        ON,
        MUSIC,
        SFX,
        OFF
    }

    private final Map<String, Music> music;
    private final Map<String, Sound> sounds;

    private MusicConfig currentlyPlaying;

    private AudioState audioState = AudioState.ON;
    private boolean musicMuted = false;
    private boolean sfxMuted = false;

    public AudioHandler() {
        music = new HashMap<>();
        addMusic("bg", "music/darksideoffate.ogg");

        sounds = new HashMap<>();
        addSound("soul", "sfx/soul.wav");
        addSound("hit", "sfx/hit.wav");
        addSound("2x", "sfx/2x.wav");
    }

    private void addMusic(String key, String fileName) {
        music.put(key, Gdx.audio.newMusic(Gdx.files.internal(fileName)));
    }

    private void addSound(String key, String fileName) {
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(fileName)));
    }

    public AudioState getAudioState() {
        return audioState;
    }

    public AudioState nextAudioState() {
        if (audioState == AudioState.ON) {
            audioState = AudioState.MUSIC;
            setMuteState(false, true);
        } else if (audioState == AudioState.MUSIC) {
            audioState = AudioState.SFX;
            setMuteState(true, false);
        } else if (audioState == AudioState.SFX) {
            audioState = AudioState.OFF;
            setMuteState(true, true);
        } else {
            audioState = AudioState.ON;
            setMuteState(false, false);
        }
        return audioState;
    }

    private void setMuteState(boolean musicMuted, boolean sfxMuted) {
        this.musicMuted = musicMuted;
        this.sfxMuted = sfxMuted;
        if (currentlyPlaying != null) {
            if (musicMuted) {
                currentlyPlaying.mute();
            } else {
                currentlyPlaying.play();
            }
        }
        if (sfxMuted) {
            for (Sound it : sounds.values()) it.stop();
        }
    }

    public boolean isMusicPlaying() {
        if (currentlyPlaying == null) return false;
        if (currentlyPlaying.getMusic() == null) return false;
        return currentlyPlaying.getMusic().isPlaying();
    }

    public void playMusic(String key, float volume, boolean looping) {
        Music newMusic = music.get(key);
        if (newMusic == null) {
            throw new IllegalArgumentException("music does not exist: " + key);
        }
        if (currentlyPlaying != null && newMusic != currentlyPlaying.getMusic()) {
            stopMusic();
        }
        currentlyPlaying = new MusicConfig(music.get(key), volume, looping);
        if (!musicMuted) currentlyPlaying.play();
    }

    public void stopMusic() {
        if (currentlyPlaying != null) {
            currentlyPlaying.stop();
            currentlyPlaying = null;
        }
    }

    public void playSound(String key) {
        playSound(key, 1, false);
    }

    public void playSoundCut(String key) {
        playSound(key, 1, true);
    }

    public void playSoundCut(String key, float volume) {
        playSound(key, volume, true);
    }

    public void playSound(String key, float volume, boolean cut) {
        if (sfxMuted) return;
        for (Map.Entry<String, Sound> entry : sounds.entrySet()) {
            if (entry.getKey().equals(key)) {
                if (cut) entry.getValue().stop();
                entry.getValue().play(volume);
            }
        }
    }

}
