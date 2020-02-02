package com.example.prototipotfg;

import android.media.AudioRecord;
import android.media.MediaRecorder;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.android.AndroidAudioInputStream;

/**
 * The Factory creates {@link AudioDispatcher} objects from the
 * configured default microphone of an Android device.
 * It depends on the android runtime and does not work on the standard Java runtime.
 *
 * @author Joren Six
 * @see AudioDispatcher
 */
public class AudioDispatcherFactory1 {

    private static AudioRecord audioInputStream;

    /**
     * Create a new AudioDispatcher connected to the default microphone.
     *
     * @param sampleRate
     *            The requested sample rate.
     * @param audioBufferSize
     *            The size of the audio buffer (in samples).
     *
     * @param bufferOverlap
     *            The size of the overlap (in samples).
     * @return A new AudioDispatcher
     */
    public static AudioDispatcher fromDefaultMicrophone(final int sampleRate,
                                                        final int audioBufferSize, final int bufferOverlap) {
        int minAudioBufferSize = AudioRecord.getMinBufferSize(sampleRate,
                android.media.AudioFormat.CHANNEL_IN_MONO,
                android.media.AudioFormat.ENCODING_PCM_16BIT);
        int minAudioBufferSizeInSamples =  minAudioBufferSize/2;
        if(minAudioBufferSizeInSamples <= audioBufferSize ){
            audioInputStream = new AudioRecord(
                    MediaRecorder.AudioSource.MIC, sampleRate,
                    android.media.AudioFormat.CHANNEL_IN_MONO,
                    android.media.AudioFormat.ENCODING_PCM_16BIT,
                    audioBufferSize * 2);

            TarsosDSPAudioFormat format = new TarsosDSPAudioFormat(sampleRate, 16,1, true, false);

            TarsosDSPAudioInputStream audioStream = new AndroidAudioInputStream(audioInputStream, format);
            //start recording ! Opens the stream.
            audioInputStream.startRecording();
            return new AudioDispatcher(audioStream,audioBufferSize,bufferOverlap);
        }else{
            throw new IllegalArgumentException("Buffer size too small should be at least " + (minAudioBufferSize *2));
        }
    }

    public AudioRecord getAudioRecord(){
        return this.audioInputStream;
    }

    /**
     * Create a stream from a piped sub process and use that to create a new
     * {@link AudioDispatcher} The sub-process writes a WAV-header and
     * PCM-samples to standard out. The header is ignored and the PCM samples
     * are are captured and interpreted. Examples of executables that can
     * convert audio in any format and write to stdout are ffmpeg and avconv.
     *
     * @param source
     *            The file or stream to capture.
     * @param targetSampleRate
     *            The target sample rate.
     * @param audioBufferSize
     *            The number of samples used in the buffer.
     * @param bufferOverlap
     * 			  The number of samples to overlap the current and previous buffer.
     * @return A new audioprocessor.
     */

}
